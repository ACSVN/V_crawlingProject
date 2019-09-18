/**
 * Created on Sat Jul 13 09:41:32 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package mynavi;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubstringHref extends DefaultJavaTestScript  {

   public void test() {
      try {
          String str_href = getContext().getVariableAsString("href_empty");
          int ind = str_href.indexOf("/?ty");
          String tmp = str_href.substring(0, ind);
          String fullPath = URLDecoder.decode(tmp, "UTF-8");
          getContext().setVariable("href_sub", fullPath);
      }  catch (StopRequestException ex) {
            throw ex;
        } catch(IOException e){
            getContext().setVariable("href_sub", "Load data storge error. " + e.toString());
            throw new RuntimeException(e);
        }
   }
   
   public static void main(String args[]) {
      SubstringHref script = new SubstringHref();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "SubstringHref@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
