/**
 * Created on Mon May 27 10:33:49 ICT 2019
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

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.lang.Object;

public class DeleteFileExist extends DefaultJavaTestScript  {

    public void test() {
        try {

            //String path = getContext().getVariableAsString("_PROJECT_DIR");
            //String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath +  "/html_file";

            //File folder = new File(fullPath);
            //File[] listOfFiles = folder.listFiles();
            //for (int i = 0; i < listOfFiles.length; i++) {
            //File file = listOfFiles[i];
            //if (file.isFile() && file.getName().endsWith(".html")) {
            //boolean success = file.delete();
            //}
            //if(file.isDirectory()){
            //}
            //}
            this.deletedFileAndFolder();
            getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file success!!!");

        } catch (StopRequestException ex) {
            getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. StopRequestException: " + ex.toString());
            throw ex;
        } catch (Exception e) {
            getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. Exception: " + e.toString());
            System.err.println("Usage: java PageSaver url local_file");
        }
    }
   
    public void deletedFileAndFolder(){
        try {
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPath = URLDecoder.decode(path, "UTF-8");
            fullPath = fullPath +  "/html_file";
            File folder = new File(fullPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                File file = listOfFiles[i];
                this.deleteDirectory(file);
                //if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                    //file.delete();
                //}
                //if(file.isDirectory()){
                    //this.deleteDirectory(file);
                //}
            }
        } catch (StopRequestException ex) {
            getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. StopRequestException: " + ex.toString());
            throw ex;
        } catch (Exception e) {
            getContext().setVariable("check_status", "DeleteFileExist.java. Deleted html file!!!. Exception: " + e.toString());
            System.err.println("Usage: java PageSaver url local_file");
        }
    }
    
    public static void deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        directory.delete();
    }
    public static void main(String args[]) {
        DeleteFileExist script = new DeleteFileExist();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "DeleteFileExist@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
