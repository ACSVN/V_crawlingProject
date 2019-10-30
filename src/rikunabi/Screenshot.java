/**
 * Created on Wed Sep 25 14:21:51 JST 2019
 * HeartCore Robo Desktop v5.0.3 (ビルド番号 5.0.3-20190618.1)
 **/
package rikunabi;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;
import java.io.*;
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

public class Screenshot extends DefaultJavaTestScript  {

   public void test() {
       String dirname = getVariableAsString("dir");  // ディレクトリ名(相対パスから逃げ)

    String filename = dirname + "\\result\\rikunabi.csv";    // 入力ファイル名
    String outputname = dirname + "\\result\\rikunabi_URLlist.csv";  // 出力ファイル名

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
   public static void main(String args[]) {
      Screenshot script = new Screenshot();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Screenshot@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
