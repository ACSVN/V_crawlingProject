import java.io.*;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class KintoneMakeData_doda__B60e996ce70890b66 extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.*;
       
           String projectdir_tpr = getVariableAsString("_PROJECT_DIR") + "\\result\\";
           String website_tpr = getVariableAsString("web_site");
           String original_csv = projectdir_tpr + website_tpr + ".csv";         // 元々のcsvファイル
           String replace_csv = projectdir_tpr + website_tpr + "_replace.csv";  // URLを置き換えるためのやつ
           String kintone_csv = projectdir_tpr + website_tpr + "_kintone.csv";  // kintoneに貼り付けるためのcsvファイル(このスクリプトの成果物！！)
       
           try {
               BufferedReader br_original_csv = new BufferedReader(new FileReader(new File(original_csv)));
               FileWriter f;
               PrintWriter pw_kintone_csv;
               BufferedReader print_writer_csv = new BufferedReader(new FileReader(new File(original_csv)));
       
               String line1 = "";
               String line2 = "";
               boolean first = true;
       
               while ((line1 = br_original_csv.readLine()) != null){
                   BufferedReader br_replace_csv = new BufferedReader(new FileReader(new File(replace_csv)));
                   String original[] = line1.split(",");
       
                   if (first) {
                       //f = new FileWriter( kintone_csv, false);
                       //pw_kintone_csv = new PrintWriter(new BufferedWriter(f));
                       pw_kintone_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(kintone_csv,false),"UTF-8")));
       
                       // オリジナルファイル(doda.csv)の目次を１行目に出力
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
                       //f = new FileWriter( kintone_csv, true);
                       //pw_kintone_csv = new PrintWriter(new BufferedWriter(f));
                       pw_kintone_csv = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(kintone_csv,true),"UTF-8")));
                       boolean find_flg=false;
                       while ((line2 = br_replace_csv.readLine()) != null && !find_flg) {
                           String replace[] = line2.split(",");
                           if(original[0].contains(replace[0])){
                              find_flg=true;
                              pw_kintone_csv.print(replace[1]);
                          }
                      }
                         if(!find_flg){
                             pw_kintone_csv.print(original[0]);
                         }
                         pw_kintone_csv.print(",");
                         for (int i = 1; i < original.length; i++) {
                             if (i < original.length - 1) {
                                 pw_kintone_csv.print(original[i]);
                                 pw_kintone_csv.print(",");
                               } else {
                                   pw_kintone_csv.println(original[i]);
                              }
                         }
                     br_replace_csv.close();
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
