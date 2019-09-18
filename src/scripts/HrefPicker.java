/**
 * Created on Mon Jun 17 17:27:39 GMT+09:00 2019
 * HeartCore Robo Desktop v5.0.2 (ビルド番号 5.0.2-20190417.1)
 **/
package scripts;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.logging.Level;

public class HrefPicker extends DefaultJavaTestScript  {

   public void test() {
      try {

         String url = "https://doda.jp/DodaFront/View/JobSearchList.action?did=T000001&charset=SHIFT-JIS&fktt=4&kk=2&sid=TopSearch";
         int cnt = 50;
         Document docHTML = Jsoup.connect(url).get();
         for(cnt=1;cnt<=50;++cnt){
            Element eleHTML = docHTML.getElementById(String.valueOf(cnt));
            // Elements eleHTML = docHTML.getElementsByClassName('_JobListToDetail');

            getContext().setVariable("check_detail", cnt+":"+eleHTML.attr("href") );
         }

         //report(new File("results.xml"), "");

      } catch (StopRequestException ex) {
         throw ex;
      } catch (IOException ex) {
         ex.printStackTrace();
        throw new IllegalStateException(ex);
      }
   }
   
   public static void main(String args[]) {
      HrefPicker script = new HrefPicker();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "HrefPicker@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
