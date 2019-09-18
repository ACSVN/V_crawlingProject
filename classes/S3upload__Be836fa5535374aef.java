import java.io.*;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class S3upload__Be836fa5535374aef extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.*;
           String projectdir_tpr = getVariableAsString("_PROJECT_DIR") + "\\result\\";
           String website_tpr = getVariableAsString("web_site");
           String original_csv = projectdir_tpr + website_tpr + ".csv";         // 元々のcsvファイル
           String kintone_csv = projectdir_tpr + website_tpr + "_kintone.csv";  // kintoneに貼り付けるためのcsvファイル(このスクリプトの成果物！！)
       
           try {
       
               BufferedReader br_original_csv=null;
               if(website_tpr.equals("doda")){
                   br_original_csv = new BufferedReader(new InputStreamReader(new FileInputStream(original_csv),"Shift-JIS"));
               }
               else if(website_tpr.equals("mynavi")){
                   br_original_csv = new BufferedReader(new InputStreamReader(new FileInputStream(original_csv),"UTF-8"));
               }else if(website_tpr.equals("rikunabi")){
                   br_original_csv = new BufferedReader(new InputStreamReader(new FileInputStream(original_csv),"UTF-8"));
               }
       
               FileWriter f;
               PrintWriter pw_kintone_csv;
               BufferedReader print_writer_csv = new BufferedReader(new FileReader(new File(original_csv)));
       
               String line1 = "";
               String line2 = "";
               boolean first = true;
       
               while ((line1 = br_original_csv.readLine()) != null){
                   String original[] = line1.split(",");
       
                   if (first) {
                       pw_kintone_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(kintone_csv,false),"UTF-8")));
       
                       // オリジナルファイル({web_site}.csv)の目次を１行目に出力
                       for (int i = 0; i < original.length; i++) {
                           if (i < original.length - 1) {
                               pw_kintone_csv.print(original[i]);
                               pw_kintone_csv.print(",");
                           } else {
                               pw_kintone_csv.println(original[i]);
                           }
                       }
                       first = false;
                   } else {
                       // 2行目以降は1列目でURLの置き換え、2列目以降で元々のcsvデータを出力
                       pw_kintone_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(kintone_csv,true),"UTF-8")));
                       boolean find_flg=false;
                           String replace[] = line2.split(",");
                           
                           
                           //a列目で、urlの置き換え処理
                             String url_str = original[0];
                             String url_str_wrk=url_str;
                           //dodaの処理
                         if(website_tpr.equals("doda")){
                             url_str = url_str.replace("https://","https://vp-web-crawl.s3-ap-northeast-1.amazonaws.com/"+website_tpr+"/");
                             url_str = url_str + "index.html";
                         }else if(website_tpr.equals("mynavi")){                      
                             if(url_str.indexOf("/?")!=-1){
                                url_str=url_str.substring(0,url_str.indexOf("/?")+1);
                             }
                             url_str = url_str.replace("https://","https://vp-web-crawl.s3-ap-northeast-1.amazonaws.com/"+website_tpr+"/");
                             url_str = url_str + "index.html";
                             
                         }else if(website_tpr.equals("rikunabi")){                      
                             if(url_str.indexOf("cmi")!=-1){
                                url_str_wrk=url_str.substring(url_str_wrk.indexOf("cmi"));
                             }
                             if(url_str_wrk.indexOf("/?")!=-1){
                                url_str_wrk=url_str_wrk.substring(0,url_str_wrk.indexOf("/?"));
                             }
                             if(url_str_wrk.indexOf("/")!=-1){
                                url_str_wrk = url_str_wrk.replace("/","_");
                             }
                             url_str = "https://vp-web-crawl.s3-ap-northeast-1.amazonaws.com/"+website_tpr+"/"+ url_str_wrk+".png";
                         }
                         pw_kintone_csv.print(url_str);
                             
                         pw_kintone_csv.print(",");
                         for (int i = 1; i < original.length; i++) {
                             if (i < original.length - 1) {
                                 pw_kintone_csv.print(original[i]);
                                 pw_kintone_csv.print(",");
                               } else {
                                   pw_kintone_csv.println(original[i]);
                              }
                         }
                   }
                   pw_kintone_csv.close() ;
               }
           } catch (FileNotFoundException e) {
               getContext().setVariable("check_detail1","errNotFound" );
           } catch (IOException e) {
               getContext().setVariable("check_detail1","errIO" );
           }

   }
}
