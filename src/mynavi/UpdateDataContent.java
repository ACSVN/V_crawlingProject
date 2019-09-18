/**
 * Created on Sun Jul 14 18:40:53 ICT 2019
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

import com.opencsv.CSVReader;

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

import java.io.FileWriter;

public class UpdateDataContent extends DefaultJavaTestScript  {

    public void test() {
        
        String index_row_empty = getContext().getVariableAsString("index_row_empty");
        String href_empty = getContext().getVariableAsString("href_empty");
        String rank = getContext().getVariableAsString("rank");
        String web_name = getContext().getVariableAsString("web_name");
        String class_title = getContext().getVariableAsString("class_title");
        String class_company = getContext().getVariableAsString("class_company");
        String class_company_address = getContext().getVariableAsString("class_company_address");
        String class_company_recruiter = getContext().getVariableAsString("class_company_recruiter");
        String class_company_phone = getContext().getVariableAsString("class_company_phone");
        String class_info_job = getContext().getVariableAsString("class_info_job");
        String class_date_period = getContext().getVariableAsString("class_date_period");
        String class_industry = getContext().getVariableAsString("class_industry");
        String class_main_office = getContext().getVariableAsString("class_main_office");
        String class_remark = getContext().getVariableAsString("class_remark");

        try {
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            
            String fullPath = fullPathDir +  "/html_file";
            String path_file_data_result = fullPathDir + "/result/" + web_name + ".csv";
            
            String content = "";

            File folder = new File(fullPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                //getContext().setVariable("check_status", "listOfFiles.length: " + listOfFiles.length);
                File file = listOfFiles[i];
                if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                    
                    String path_file_html = fullPath + "/" + file.getName();
                    content = Jsoup.parse(new File(path_file_html), "UTF-8").toString();
                    Document doc_charset = Jsoup.parse(content);

                    String title = doc_charset.select(class_title).text();
                    String name_company = doc_charset.select(class_company).text();
                    String company_address = doc_charset.select(class_company_address.replace("contains(??)", "contains(住所)")).text();
                    String company_recruiter = doc_charset.select(class_company_recruiter.replace("contains(????)", "contains(採用担当)")).text();
                    String company_phone = doc_charset.select(class_company_phone.replace("contains(??)", "contains(電話番号)")).text();
                    String work_location = doc_charset.select(class_info_job.replace("contains(???)", "contains(勤務地)")).text();
                    String post_date_period = doc_charset.select(class_date_period).text();
                    String field_industry = doc_charset.select(class_industry.replace("contains(??)", "contains(業種)")).text();
                    String main_office = doc_charset.select(class_main_office.replace("contains(?????)", "contains(本社所在地)")).text();
                    String remark = doc_charset.select(class_remark.replace("contains(??)", "contains(備考)")).text();
                    String fax = "";
                    String email = "";
                    String caption = "";
            
                    getContext().setVariable("check_status", "UpdateDataContent function. Updated data content url empty");
                    
                    this.updateRowFromCSVFile(path_file_data_result, href_empty, title, caption, name_company, work_location, main_office, company_address, company_recruiter, company_phone, fax, email, field_industry, remark, rank, post_date_period, web_name);
                    
                }
            }
        }  catch (StopRequestException ex) {
            getContext().setVariable("check_status", "UpdateDataContent function. StopRequestException: " + ex.toString());
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_status", "UpdateDataContent function. IOException: " + e.toString());
            throw new RuntimeException(e);
        }
    }
    
    public void updateRowFromCSVFile(String path_file, String href, String title, String caption, String company_name, String work_location, String main_office, String company_address, String recruiter, String company_phone, String fax, String email, String industry, String remark, String rank, String post_period, String web_name){
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
            
            out.print(href);
            out.print(",");
            out.print('"' + title.replace("\"", "") + '"');
            out.print(",");
            //out.print('"' + caption.replace("\"", "") + '"');
            //out.print(",");
            out.print('"' + company_name.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + work_location.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + main_office.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + company_address.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + recruiter.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + company_phone.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + fax.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + email.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + industry.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + remark.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + rank.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + post_period + '"');
            out.print(",");
            out.print(web_name);
            out.print("\n");
            
            out.flush();
            out.close();
            getContext().setVariable("check_status", "GetHref.java function writeContentToCSVFile successe!!!!!. title: " + title);
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function writeContentToCSVFile. IOException: " + e.toString());
        }
    }
   
    public static void main(String args[]) {
        UpdateDataContent script = new UpdateDataContent();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "UpdateDataContent@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
