import java.io.*;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class KintoneMakeData_doda__Bb0a150ff4f6313fc extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.*;
       
           String projectdir_tpr = getVariableAsString("_PROJECT_DIR") + "\\result\\";  // VP_WebCrawlフォルダのディレクトリ(相対パスエラーから逃げ)
           String website_tpr = getVariableAsString("web_site");                      // いろんなサイトで使いまわせるようにしてます
       //    String ip_Gsuite_txt = projectdir_tpr + "ID_Name.txt";                     // 入力ファイル名(input)
       //    String op_Gsuite_csv = projectdir_tpr + "ID_Name.csv";                     // 出力ファイル名(output)
           String ip_URL_csv = projectdir_tpr + website_tpr + ".csv";                 // ウェブサイトURLからhtml名(key)を取得するためのやつ
           String op_URL_csv = projectdir_tpr + website_tpr + "_output.csv";          // それを貼り付けるためのやつ
           int httpcount;                                                             // 文字列調整用
           
           try {
               // doda.csvファイルのURLからhtmlNameを取得して出力
               BufferedReader br_URL_csv = new BufferedReader(new FileReader(new File(ip_URL_csv)));
               PrintWriter pw_URL_csv = new PrintWriter(new BufferedWriter(new FileWriter(op_URL_csv)));
               boolean first = true;
               String line = "";
               while ((line = br_URL_csv.readLine()) != null) {
                   String array[] = line.split(",");  // カンマごとに分ける(csvなので)
                   if (first) {
                       first = false;
                   } else {
                       String id[] = array[0].split("/");  // カンマで分けたA列のURLをさらに"/"で分けて分解する
                       String gsuiteID = id[6].replace("j_jid__", "");
                       httpcount = array[0].indexOf("http");
                       if(httpcount != -1){
                           pw_URL_csv.print(array[0].substring(httpcount));
                       }
                       pw_URL_csv.print(",");
                       pw_URL_csv.println(gsuiteID + ".html");
                   }
               }
               br_URL_csv.close();
               pw_URL_csv.close();
           } catch (FileNotFoundException e) {
           } catch (IOException e) {
           }

   }
}
