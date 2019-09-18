/**
 * Created on Wed Jul 03 04:54:15 GMT 2019
 * HeartCore Robo Desktop v5.0.2 (Build No. 5.0.2-20190417.1)
 **/
package rikunabi;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetTotalPage extends DefaultJavaTestScript  {

   public void test() {
       
      try {
          String url_item = getContext().getVariableAsString("url_web");
          String class_total = getContext().getVariableAsString("class_total");
          String case_display = getContext().getVariableAsString("case_display");
          String replace_nxt_p = getContext().getVariableAsString("replace_nxt_p");
          
          int total_page = 0;
          int dis_case = Integer.parseInt(case_display);
          Document doc = Jsoup.connect(url_item).get();
          String charset = doc.charset().toString();
          
          String total_url = doc.select(class_total).first().text();
          total_url = total_url.replace(replace_nxt_p, "");
          int total_q = Integer.parseInt(total_url);
          if(total_q <= dis_case){
              total_page  = 1;
          }else if(total_q%dis_case == 0){
              total_page  = total_q/dis_case;
          }else{
              total_page  = (total_q/dis_case)+1;
          }
          
          getContext().setVariable("number_pages", total_page);
      } catch (StopRequestException ex) {
         getContext().setVariable("number_pages", 0);
         throw ex;
      }catch (IOException ex) {
          ex.printStackTrace();
          getContext().setVariable("number_pages", 0);
          throw new IllegalStateException(ex);
      }
   }
   
   public  boolean isNullOrEmpty(String str) {
        if(str == null || str.isEmpty() || str == "" || str.equals("null"))
            return true;
        return false;
    }
   
   public static void main(String args[]) {
      GetTotalPage script = new GetTotalPage();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "GetTotalPage@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
