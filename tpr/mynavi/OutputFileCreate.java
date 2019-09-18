/**
 * Created on Thu Jul 11 11:34:26 ICT 2019
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputFileCreate extends DefaultJavaTestScript  {

    public void test() {
        try {
        
            String web_name = getContext().getVariableAsString("web_name");
        
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            
            //DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            //Date date = new Date();
            
            //String path_href_report = fullPathDir + "/result_mynavi/" + dateFormat.format(date) + "_hreflist.csv";
            String path_href_report = fullPathDir + "/result/hreflist.csv";
            this.deleteCreateNewFile(path_href_report);
            ArrayList<String> title_href = new ArrayList<String>() {{
                add("URL");
                add("Page number");
                add("No/Page");
                add("Rank");
            }};
            this.writeTemplateFile(path_href_report, title_href);
            
            //String path_file_data_result = fullPathDir + "/result_mynavi/" + dateFormat.format(date) + "_result_" + web_name + ".csv";
            String path_file_data_result = fullPathDir + "/result/" + web_name + ".csv";
            this.deleteCreateNewFile(path_file_data_result);
            ArrayList<String> title_result = new ArrayList<String>() {{
                add("URL");
                add("職種名");
                //add("キャプション");
                add("会社名");
                add("勤務地");
                add("本社所在地");
                add("住所");
                add("採用担当");
                add("電話番号");
                add("FAX"); //new
                add("E-mail"); //new
                add("業種");
                add("備考");
                add("掲載ランク"); //new
                add("掲載予定期間");
                add("サイト名");
            }};
            this.writeTemplateFile(path_file_data_result, title_result);
            
            //create file contains href get content empty with jsoup
            String path_href_empty =  fullPathDir + "/result/href_empty.csv";
            this.deleteCreateNewFile(path_href_empty);
            ArrayList<String> href_empty = new ArrayList<String>() {{
                add(" ");
                add("Rows No");
                add("Href");
            }};
            this.writeTemplateFile(path_href_empty, href_empty);
            
        } catch (IOException ex) {
            getContext().setVariable("check_status", "deleteCreateNewFile. java function test error. " + ex.toString());
        }
    }
    
    public void deleteCreateNewFile(String dir_path){
        try{
            File file = new File(dir_path);
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            getContext().setVariable("path_href_file", dir_path);
        }catch(IOException e){
            getContext().setVariable("check_status", "deleteCreateNewFile.java. deleteCreateNewFile Create new file csv error. " + e.toString());
        }
    }
    
    public void writeTemplateFile(String path_file, ArrayList<String> title_header){
        try{
            File in = new File(path_file);
            OutputStream os = null;
            PrintWriter out = null;
            if ( in.exists() && !in.isDirectory() ) {
                os = new FileOutputStream(new File(path_file), true);
            }
            else {
                os = new FileOutputStream(new File(path_file));
            }
            os.write(239);
            os.write(187);
            os.write(191);
            
            out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            
            for(int i = 0; i < title_header.size(); i++){
                out.print('"' + title_header.get(i) + '"');
                if(i == title_header.size()-1){
                    out.print("\n");
                }else{
                    out.print(",");
                }
                
            }
            out.flush();
            out.close();
            getContext().setVariable("check_status", "OutputFileCreate.java function writeTemplateFile success!!!!");
        }catch(IOException e){
            getContext().setVariable("check_status", "OutputFileCreate.java function writeTemplateFile error. IOException: " + e.toString());
        }
    }
   
    public static void main(String args[]) {
        OutputFileCreate script = new OutputFileCreate();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "OutputFileCreate@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
