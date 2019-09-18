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

public class HTTrack__B60d8b3274a22b334 extends DefaultJavaTestScript {
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
           String web_site = getVariableAsString("web_site");  // ディレクトリ名(相対パスから逃げ)
           getContext().setVariable("check_detail", web_site);
           //特に指定が無い場合は、hreflist.csvからデータを参照する
           String filename = dirname + "\\result\\hreflist.csv";    // 入力ファイル名
           //dodaの場合は、doda.csvからリストを拝借する
           //190903kodama"Doda"から"doda"に変更
           if(web_site.equals("doda")){
               filename = dirname + "\\result\\doda.csv";
           //
           }else if(web_site.equals("rikunabi")){
               filename = dirname + "\\result\\rikunabi.csv";
           }
           String outputname = dirname + "\\result\\hreflist.txt";  // 出力ファイル名
       
           try {
               BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
               PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputname)));
       
               String line = "";
               boolean first = true;
       
               while ((line = br.readLine()) != null) {
                   String array[] = line.split(",");  // カンマごとに分ける
                   if (first) {
                       first = false;  // 最初の行だけこっちの処理(出力なし)
                   } else {
                       pw.println(array[0]);  // コピーしたい行のみ出力
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
