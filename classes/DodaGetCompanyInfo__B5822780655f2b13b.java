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

public class DodaGetCompanyInfo__B5822780655f2b13b extends DefaultJavaTestScript {
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
           int kcnt = 1, cnt = 1; //finally避けの代入。本来の値はfor文の初期条件式
           try {
       
           //hreflist.csvへの書き出しを、全てのリストページのチェックが完了するまで繰り返す
       
           //ファイル読み込みで使用する３つのクラス
               FileInputStream fi = null;
               InputStreamReader is = null;
               BufferedReader br = null;
               fi = new FileInputStream(dir_path + "\\result\\hreflist.csv");
               is = new InputStreamReader(fi);
               br = new BufferedReader(is);
               String line;
               long i = 1;
       
               //読み込み行
               FileWriter f;
               PrintWriter p;
       
               //項目行の処理
               f = new FileWriter(dir_path + "\\result\\doda.csv", false);
               p = new PrintWriter(new BufferedWriter(f));
               line = br.readLine();
       
               p.println("URL,職種名,会社名,勤務地,本社所在地,郵便番号,住所,採用担当,電話番号,FAX,E-mail,業種,備考,掲載ランク,掲載予定期間,サイト名");
               p.close();
       
               while ((line = br.readLine()) != null) {
                   f = new FileWriter(dir_path + "\\result\\doda.csv", true);
                   p = new PrintWriter(new BufferedWriter(f));
                   String[] data = line.split(",");
                   String url = data[0];
                   p.print(data[0]);
       
                   //URLにアクセスし、docHTMLにHTMLデータを取得
                   //(try)ページが掲載終了であった場合のエラー避け
                   try {
                       getContext().setVariable("test_text", "before");
                       Document docHTML = Jsoup.connect(url).get();
                       getContext().setVariable("test_text", "after");
                       Elements elemHTML;
                       //セレクターで各データを取得
       
                       //職種名を取得
                       elemHTML = docHTML.select("#wrapper > div.head_detail > div > div > p.explain");
                       p.print(",");
                       //p.print(elemHTML.text());
                       p.print('"' + elemHTML.text() + '"');
       
                       //会社名を取得
                       elemHTML = docHTML.select("#wrapper > div.head_detail > div > div > h1");
                       String company_name=elemHTML.text();
                       String company_after="";
                       if(company_name.indexOf("【")!=-1){
                           company_after=company_name.substring(company_name.indexOf("【"));
                           company_name=company_name.replace(company_after,"");
                       }
                       if(company_name.indexOf("(")!=-1){
                           company_after=company_name.substring(company_name.indexOf("("));
                           company_name=company_name.replace(company_after,"");
                       }
                       if(company_name.indexOf("（")!=-1){
                           company_after=company_name.substring(company_name.indexOf("（"));
                           company_name=company_name.replace(company_after,"");
                       }
                       company_name=company_name.replace(" ","");
                       company_name=company_name.replace("　","");
                       //190903kodama追記・カッコが先頭に来たことによって会社名が空白になる処理の回避
                       if(company_name.equals("")){
                           company_name=elemHTML.text();
                       }
       
                       p.print(",");
                       p.print('"' + company_name + '"');
       
                       //勤務地を取得
                       elemHTML = docHTML.select("#job_description_table > tbody > tr > th:contains(勤務地) + td > p");
                                      
                       getContext().setVariable("test_text",elemHTML.text() );
                       if (elemHTML.text().equals("")) {
                           elemHTML = docHTML.select("#shtTabInner2 > div.layout > table > tbody > tr > th:contains(勤務地) + td");
                           p.print(",");
                           p.print('"' + elemHTML.text() + '"');
                       } else {
                           p.print(",");
                           p.print('"' + elemHTML.text());
                           elemHTML = docHTML.select("#job_description_table > tbody > tr > th:contains(勤務地) + td > div > ul");
                           p.print(" " + elemHTML.text() + '"');
                       }
       
                       //本社所在地を取得
                       //dodaは本社所在地の欄なし
                       p.print(",");
       
                       //郵便番号(create by nose)
                       elemHTML = docHTML.select("#company_profile_table > tbody > tr > th:containsOwn(所在地) + td");
                       if (elemHTML.text().equals("")) {
                           elemHTML = docHTML.select("#shtTabInner3 > div.layout.layout30 > div > dl:nth-child(2) > dd");
                       }
                       p.print(",");
                       String postcode = elemHTML.text();
                       postcode = postcode.replace("\"", "");
                       postcode = postcode.replace("-", "");
                       postcode = postcode.replace(" ", "");
                       p.print('"' + postcode.substring(postcode.indexOf("〒") + 1, postcode.indexOf("〒") + 8) + '"');
       
                       //住所を取得
       //              elemHTML = docHTML.select("#company_profile_table > tbody > tr > th:containsOwn(所在地) + td");
       //              if (elemHTML.text().equals("")) {
       //                  elemHTML = docHTML.select("#shtTabInner3 > div.layout.layout30 > div > dl:nth-child(2) > dd");
       //              }
                       p.print(",");
                       p.print('"' + elemHTML.text() + '"');
       
                       //採用担当を取得
                       elemHTML = docHTML.select("#application_method_table > tbody > tr > td > dl > dt:contains(連絡先) + dd > p");
                       p.print(",");
                       p.print('"' + elemHTML.text() + '"');
       
                       //電話番号
                       //dodaは電話番号の欄なし
                       p.print(",");
       
                       //FAX
                       //dodaはFAXの欄なし
                       p.print(",");
       
                       //E-mail
                       //dodaはE-mailの欄なし
                       p.print(",");
       
                       //業種を取得
                       elemHTML = docHTML.select("#shStart > article > div.layout.shtCondList > div > dl > dt:contains(業種) + dd");
                       p.print(",");
                       p.print('"' + elemHTML.text() + '"');
       
                       //備考
                       //dodaは備考の欄なし
                       p.print(",");
       
                       //掲載ランク
                       String rank = "";
                       docHTML = Jsoup.connect(url).get();
                       int j = 0;
                       List<Comment> comments = new ArrayList<>();
                       for (Element element : docHTML.getAllElements()) {
                           for (Node n : element.childNodes()) {
                               if (n.nodeName().equals("#comment")) {
                                   Comment c1 = (Comment)n;
                                   if (c1.getData().trim().startsWith("原稿種別: E")) {
                                       rank = "E";
                                   } else if (c1.getData().trim().startsWith("原稿種別: D")) {
                                       rank = "D";
                                   } else if (c1.getData().trim().startsWith("原稿種別: C")) {
                                       rank = "C";
                                   } else if (c1.getData().trim().startsWith("原稿種別: B")) {
                                       rank = "B";
                                   } else if (c1.getData().trim().startsWith("原稿種別: A")) {
                                       rank = "A";
                                   } else if (c1.getData().trim().startsWith("原稿種別: T")) {
                                       rank = "T";
                                       fi.close();
                                       return;
       
                                   }
                                   comments.add((Comment)n);
                                   j++;
                               }
                           }
                       }
                       getContext().setVariable("check_detail", j);
                       p.print(",");
                       p.print('"' + rank + '"');
       
                       //掲載予定期間を取得
                       elemHTML = docHTML.select("#wrapper > div.head_detail > div > div > p.meta_text");
                       p.print(",");
                       p.print('"' + elemHTML.text() + '"');
       
                       //サイト名をdodaに設定
                       p.print(",");
                       p.print("doda");
       
                       //行の終わり
                       p.print(",");
                       p.println();
                       p.close();
       
                   } catch (IOException e) {
                       p.print(",,,,,,,,,,,,,,");
                       p.print('"' + "掲載期間終了" + '"');
                       p.print(",doda,");
                       p.println();
                       p.close();
                   }
               }
               fi.close();
       
          } catch (StopRequestException ex) {
               throw ex;
          } catch (IOException ex) {
               ex.printStackTrace();
               throw new IllegalStateException(ex);
          } finally {
             getContext().setVariable("check_detail", "end");
          }

   }
}
