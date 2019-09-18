import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import com.tplan.robot.scripting.*;
import java.io.*;
import java.util.*;

public class rikunabi__B419e1cd92b236f00 extends DefaultJavaTestScript {
   public void test() {
             
       //     import java.io.BufferedReader;
       //     import java.io.File;
       //     import java.io.FileNotFoundException;
       //     import java.io.FileReader;
       //     import java.io.IOException;
       //     import java.util.ArrayList;
       //     import java.util.Arrays;
       //     import java.util.List;
       //     import java.io.FileWriter;
       //     import java.io.BufferedWriter;
       //     import java.io.PrintWriter;
       
           String dirname = getVariableAsString("dir");  // ディレクトリ名(相対パスから逃げ)
       
           String filename = dirname + "\\result\\rikunabi.csv";    // 入力ファイル名
           String outputname = dirname + "\\result\\rikunabi_URLlist.csv";  // 出力ファイル名
           getContext().setVariable("check_detail", "1");
           try {
               BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
               PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputname)));
               getContext().setVariable("check_detail", "2");
       
               String line = "";
               boolean first = true;
       
               while ((line = br.readLine()) != null) {
                   getContext().setVariable("check_detail", line);
                   String array[] = line.split(",");  // カンマごとに分ける
                   if (first) {
                       first = false;  // 最初の行だけこっちの処理(出力なし)
                   } else {
                       pw.println(array[0].substring(array[0].indexOf("http")));  // コピーしたい行のみ出力
                   }
               }
               br.close();
               pw.close();
       
           } catch (FileNotFoundException e) {
               getContext().setVariable("check_detail", "99");
           } catch (IOException e) {
               getContext().setVariable("check_detail", "999");
           }
       

   }
}
