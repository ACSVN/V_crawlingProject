/**
 * Created on Thu Sep 19 21:05:59 JST 2019
 * HeartCore Robo Desktop v5.0.3 (ビルド番号 5.0.3-20190618.1)
 **/
package HTTrack;
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

public class edittext extends DefaultJavaTestScript  {

    public void test() {

        String dirname = getVariableAsString("_PROJECT_DIR");
        String web_site = getVariableAsString("web_site");
        String filename = dirname + "\\result\\hreflist.csv";

        if (web_site.equals("doda")) {
            filename = dirname + "\\result\\doda.csv";
        } else if (web_site.equals("rikunabi")) {
            filename = dirname + "\\result\\rikunabi.csv";
        }
        String outputname = dirname + "\\result\\hreflist.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            PrintWriter pw = new PrintWriter(new FileWriter(new File(outputname)));

            String line = "";
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String array[] = line.split(",");
                if (first) {
                    first = false;
                } else {
                    pw.println(array[0]);
                }
            }
            br.close();
            pw.close();
        } catch (FileNotFoundException e) {
            getContext().setVariable("result", "99");
        } catch (IOException e) {
            getContext().setVariable("result", "999");
        }
    }

   public static void main(String args[]) {
      edittext script = new edittext();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Script1@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
