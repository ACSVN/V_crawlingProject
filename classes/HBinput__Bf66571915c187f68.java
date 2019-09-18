import java.io.*;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class HBinput__Bf66571915c187f68 extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.*;
           String projectdir_tpr = getVariableAsString("_PROJECT_DIR") + "\\result\\";
           //String website_tpr = getVariableAsString("web_site");
           String original_csv = projectdir_tpr + "HB.csv";         // 元々のcsvファイル
           String noBOM_csv = projectdir_tpr + "HB_noBOM.csv";  // noBOMに貼り付けるためのcsvファイル(このスクリプトの成果物！！)
       
           try {
               BufferedReader br_original_csv = new BufferedReader(new InputStreamReader(new FileInputStream(original_csv),"UTF-8"));
               FileWriter f;
               PrintWriter pw_noBOM_csv;
       //        BufferedReader print_writer_csv = new BufferedReader(new InputStreamReader(new FileInputStream(original_csv),"UTF-8"));
       
               String line1 = "";
               String line2 = "";
               boolean first = true;
       
               while ((line1 = br_original_csv.readLine()) != null){
                   String original[] = line1.split(",");
       
                   if (first) {
                       pw_noBOM_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(noBOM_csv,false),"UTF-8")));
       
                       // オリジナルファイル({web_site}.csv)の目次を１行目に出力
                       for (int i = 0; i < original.length; i++) {
                           if (i < original.length - 1) {
                               pw_noBOM_csv.print(original[i]);
                               pw_noBOM_csv.print(",");
                           } else {
                               pw_noBOM_csv.println(original[i]);
                           }
                       }
                       first = false;
                   } else {
                       // 2行目以降は1列目でURLの置き換え、2列目以降で元々のcsvデータを出力
                       pw_noBOM_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(noBOM_csv,true),"UTF-8")));
                       boolean find_flg=false;
                           String replace[] = line2.split(",");
                          //a列目で、urlの置き換え処理
                             //pw_noBOM_csv.print(original[0]);
                             String url_str = original[0];
                             if(url_str.indexOf("https://")!=-1){
                                 url_str=url_str.substring(url_str.indexOf("https://"));
                             }
                             pw_noBOM_csv.print(url_str);
                             
                         pw_noBOM_csv.print(",");
                         for (int i = 1; i < original.length; i++) {
                             if (i < original.length - 1) {
                                 pw_noBOM_csv.print(original[i]);
                                 pw_noBOM_csv.print(",");
                               } else {
                                   pw_noBOM_csv.println(original[i]);
                              }
                         }
                   }
                   pw_noBOM_csv.close() ;
               }
       //        br_original_csv.close();
           } catch (FileNotFoundException e) {
               getContext().setVariable("check_detail1","errNotFound" );
           } catch (IOException e) {
               getContext().setVariable("check_detail1","errIO" );
           }

   }
}
