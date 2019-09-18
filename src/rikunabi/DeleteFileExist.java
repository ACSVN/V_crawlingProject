/**
 * Created on Mon May 27 10:33:49 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package rikunabi;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DeleteFileExist extends DefaultJavaTestScript  {

	public void test() {
		try {

			String path = getContext().getVariableAsString("_PROJECT_DIR");
			String fullPath = URLDecoder.decode(path, "UTF-8");
			fullPath = fullPath +  "/html_file";

			File folder = new File(fullPath);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];
				if (file.isFile() && file.getName().endsWith(".html")) {
					boolean success = file.delete();
				}
			}

			getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file success!!!");

		} catch (StopRequestException ex) {
			getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. StopRequestException: " + ex.toString());
			throw ex;
		} catch (Exception e) {
			getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. Exception: " + e.toString());
			System.err.println("Usage: java PageSaver url local_file");
		}
	}
   
	public static void main(String args[]) {
		DeleteFileExist script = new DeleteFileExist();
		ApplicationSupport robot = new ApplicationSupport();
		AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "DeleteFileExist@" + Integer.toHexString(script.hashCode()), args, System.out, false);
		new Thread(runnable).start();
	}
}
