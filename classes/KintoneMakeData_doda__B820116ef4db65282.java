import java.io.*;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class KintoneMakeData_doda__B820116ef4db65282 extends DefaultJavaTestScript {
   public void test() {
             //     import java.io.*;
       
           String projectdir_tpr = getVariableAsString("_PROJECT_DIR") + "\\result\\";  // VP_WebCrawlフォルダのディレクトリ(相対パスエラーから逃げ)
           //String projectdir_tpr = "..\\result\\";  // VP_WebCrawlフォルダのディレクトリ(相対パスエラーから逃げ)
           getContext().setVariable("check_detail2",projectdir_tpr );
           String website_tpr = getVariableAsString("web_site");                      // いろんなサイトで使いまわせるようにしてます
           //getContext().setVariable("check_detail2",website_tpr );
               String ip_Gsuite_txt = projectdir_tpr + "ID_Name.txt";                     // 入力ファイル名(input)
           String op_Gsuite_csv = projectdir_tpr + "ID_Name.csv";                     // 出力ファイル名(output)
           int httpcount;                                                             // 文字列調整用
           
           try {
               // gdrive.exeからとったID(URLに加工する)とhtmlNameを出力
               BufferedReader br_Gsuite_txt = new BufferedReader(new FileReader(new File(ip_Gsuite_txt)));
               String line = "";
               boolean first = true;
               File file = new File(op_Gsuite_csv);
               int cnt=0;
               FileWriter filewriter;
               
               while ((line = br_Gsuite_txt.readLine()) != null) {
                   if(first){
                     filewriter = new FileWriter(file);
                   }
                   else{
                       filewriter = new FileWriter(file,true);
                   }
                   PrintWriter pw_Gsuite_csv = new PrintWriter(filewriter);
       
                   cnt=cnt+1;
                   String array[] = line.split("\\s+");  // 空白ごとに分ける(txtなので)
                   if (first) {
                       first = false;
                   } else {
                       pw_Gsuite_csv.print("https://drive.google.com/uc?id=" + array[0] + "&export=download");  // htmlDownloadURL
                       pw_Gsuite_csv.print(",");
                       pw_Gsuite_csv.println(array[1]);  // htmlName
                   }
                   pw_Gsuite_csv.close();
                   getContext().setVariable("check_detail1",cnt );
                   }
               br_Gsuite_txt.close();
           } catch (FileNotFoundException e) {
               getContext().setVariable("check_detail1","err2" );
           } catch (IOException e) {
               getContext().setVariable("check_detail1","err1" );
           }

   }
}
