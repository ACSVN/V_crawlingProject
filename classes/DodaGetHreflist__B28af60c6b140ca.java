import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class DodaGetHreflist__B28af60c6b140ca extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.FileWriter;
       //     import java.io.File;
       //     import java.io.BufferedWriter;
       //     import java.io.PrintWriter;
       //     import java.io.IOException;
           
       //     import org.jsoup.Jsoup;
       //     import org.jsoup.helper.Validate;
       //     import org.jsoup.nodes.*;
       //     import org.jsoup.select.Elements;
           
           String dir_path = getContext().getVariableAsString("_PROJECT_DIR");
       
           
           int kcnt=1,cnt=1; //finally避けの代入。本来の値はfor文の初期条件式
           boolean first = true;    
           boolean endFlag = false;
           try {
       
           //リストページ1つを確認後、hreflist.csvに書き出しを、全てのリストページをチェック完了するまで繰り返す
          // for(kcnt=1;kcnt<=2000&&endFlag==false;kcnt=kcnt+1){
           for(kcnt=1;kcnt<=2&&endFlag==false;kcnt=kcnt+1){
               FileWriter f;
               if(first) {
                   //最初のアクセス時、hreflist.csvを作り直す
                   f = new FileWriter(dir_path +"\\result\\hreflist.csv", false);
                   first = false;
               } else {
                   f = new FileWriter(dir_path +"\\result\\hreflist.csv", true);
               }
               PrintWriter p = new PrintWriter(new BufferedWriter(f));
          
               //アクセスするべきurlを取得する
               String url;
               //kcntが1のときはTopShearchに、1以外の時はページ数を指定してアクセスする
               if(kcnt==1) {
                   //新着指定が無い場合は、全件表示のリストページを対象とする
                   //url = "https://doda.jp/DodaFront/View/JobSearchList.action?did=T000001&charset=SHIFT-JIS&fktt=4&kk=2&sid=TopSearch";
                   //新着指定がある場合は、新着のみのリストページを対象とする
                   //if(new_or_all.equals("新着のみ")){
                    url = "https://doda.jp/DodaFront/View/JobSearchList/j_op__1/";
                   //}
                   p.println("企業詳細ページURL,取得元URL,リストページ番号,番号");
               }
               //新着指定が無い場合は、全件表示のリストページを対象とする
               else {
                   //url = "https://doda.jp/DodaFront/View/JobSearchList.action?so=50&tp=1&pic=1&ds=0&page="+kcnt;
                   //新着指定がある場合は、新着のみのリストページを対象とする
                    //if(new_or_all.equals("新着のみ")){
                       url = "https://doda.jp/DodaFront/View/JobSearchList/j_op__1/-page__"+kcnt+"/";
                   //}
               }
               Document docHTML;
               //上記のURLにアクセスし、docHTMLにHTMLデータを取得
                   docHTML = Jsoup.connect(url).get();
       
                   //クラス名が"_JobListToDetail"になっているhref（＝企業詳細ページリンク）を取得
                   Elements elemHTML = docHTML.getElementsByClass("_JobListToDetail").select("[href]");
                           
                  //取得したhrefデータを個別に確認して出力する
                  cnt=1;
                  String sameHTML="";
                  for (Element eleHTML: elemHTML) {            
                    if(!eleHTML.attr("href").equals(sameHTML)){
                       p.print(eleHTML.attr("href").replace("/-tab__pr/","/-tab__jd/"));
                       p.print(",");
                       p.print(eleHTML.attr("href"));
                       p.print(",");
                       p.print(kcnt);
                       p.print(",");
                       p.print(cnt);
                       p.println();
                       sameHTML=eleHTML.attr("href");
                       cnt++;
                    }
                 }
                  //一つもhrefが取得できていない場合、endFlagを立てる
                 if(cnt==1){
                          endFlag=true;
                 }
                 //1行の登録が終わったら、一度csvファイルを閉じる
                 p.close();              
          }
       
          } catch (StopRequestException ex) {
                         throw ex;
          } catch (IOException ex) {       
                ex.printStackTrace();
               throw new IllegalStateException(ex);
          }   

   }
}
