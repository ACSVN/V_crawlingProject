/**
 * Created on Wed Jul 03 09:03:36 GMT 2019
 * HeartCore Robo Desktop v5.0.2 (Build No. 5.0.2-20190417.1)
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

public class GetHref extends DefaultJavaTestScript  {
    
    public void test() {
        ArrayList<String> arr_href_storage = new ArrayList<String>();
        ArrayList<String> arr_hrefs = new ArrayList<String>();
        ArrayList<String> arr_name_com = new ArrayList<String>();
        ArrayList<String> arr_title = new ArrayList<String>();
        ArrayList<String> arr_caption = new ArrayList<String>();
        ArrayList<String> arr_rank = new ArrayList<String>();
       
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            
            //get variable from tpr file
            String new_or_all = getContext().getVariableAsString("new_or_all");
            String web_name = getContext().getVariableAsString("web_name");
            //String class_href = getContext().getVariableAsString("class_href");
            //if(new_or_all=="新着のみ"){                
                String class_class_href = getContext().getVariableAsString("class_href");
                String class_href = class_class_href.replace("contains(�V��)", "contains(新着)");
            //}else{
                //String class_href = getContext().getVariableAsString("class_href");
            //}
            String class_company = getContext().getVariableAsString("class_company");
            String class_title = getContext().getVariableAsString("class_title");
            String class_caption = getContext().getVariableAsString("class_caption");
            String str_url = getContext().getVariableAsString("url");
            String class_domain = getContext().getVariableAsString("class_domain");
            String page_item = getContext().getVariableAsString("page_item");
            //int num_page = Integer.parseInt(page_item);
            
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            getContext().setVariable("aaaaa", "fullPathDir");
            //load list href in data storage
                  String path_file_data_storage = fullPathDir + "/result/data_storage_" + web_name + ".csv";              
                  
            //create new file if have not data storage
            this.createNewFile(path_file_data_storage);
            //load data storge of href
            arr_href_storage = this.readDataFromCSV(path_file_data_storage);
            
            String fullPath = fullPathDir +  "/html_file";
            
            String content = "";

            File folder = new File(fullPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                getContext().setVariable("check_status", "listOfFiles.length: " + listOfFiles.length);
                File file = listOfFiles[i];
                if (file.isFile() && file.getName().endsWith(".html")) {
                    String path_file_html = fullPath + "/" + file.getName();
                    content = Jsoup.parse(new File(path_file_html), "UTF-8").toString();
                    Document doc = Jsoup.parse(content);

                    String arr_class_href[] = class_href.split("&and&");
                    String arr_class_title[] = class_title.split("&and&");

                    for(int cls = 0; cls < arr_class_href.length; cls++) {
                        Elements hrefs = doc.select(arr_class_href[cls]);
                        Elements captions = doc.select(class_caption);
                        //Elements titles = doc.select(arr_class_title[cls]);
                        //Elements company_names = doc.select(class_company);
                        
                        for(int j = 0; j <  hrefs.size(); j++){
                            //String newFlg =doc.select("body > div.wrapper > div:nth-child(5) > form > div > div:nth-child(" +(32+j)+") > div > div.cassetteRecruitRecommend__label").toString();
                            //if (newFlg==null){continue;}
                            String str_url_tmp = "";
                            String caption_tmp = captions.get(j).text();
                            String rank_tmp = doc.select("body > div.wrapper > div:nth-child(5) > form > div > div:nth-child(" + (j+32) + ")").toString();
                            String rank = "";
                            
                                                            
         
                           if(rank_tmp.indexOf("<img") < 0){
                                rank = "MT-D";
                            }else if(rank_tmp.indexOf("<img width=\"120\" height=\"90\"") >= 0){
                                rank = "MT-C";
                            }else if(rank_tmp.indexOf("<img width=\"160\" height=\"120\"") >= 0){
                                rank = "MT-B";
                            }else if(rank_tmp.indexOf("<img width=\"900\" height=\"150\"") >= 0){
                                rank = "MT-S";
                            }else if(rank_tmp.indexOf("<img width=\"200\" height=\"150\"") >= 0){
                                rank = "MT-A";
                            }
                            
                            //String title_tmp = titles.get(j).text();
                            //String name_com = company_names.get(j).text();
                            
                            if(hrefs.get(j).attr("href").indexOf(class_domain) < 0){
                                str_url_tmp = class_domain + hrefs.get(j).attr("href");
                            }else{
                                str_url_tmp = hrefs.get(j).attr("href");
                            }
                            str_url_tmp = str_url_tmp.replace("msg/", "");
                            str_url_tmp = str_url_tmp.replaceAll("[/]+[a-zA-Z]+[0-9]+[/]", "/");
                            
                            //check url item have exist in data storage
                            if(!arr_href_storage.contains(str_url_tmp)){
                                arr_hrefs.add(str_url_tmp);
                                arr_caption.add(caption_tmp);
                                arr_rank.add(rank);
                                //arr_title.add(title_tmp);
                                //arr_name_com.add(name_com);
                            }                          
                        }
                    }
                    //delete href list file exist and create new file
                    String path_href_report = fullPathDir + "/result/hreflist.csv";
                    //String path_href_report = fullPathDir + "/result/" + dateFormat.format(date) + "_hreflist.csv";
                    //this.deleteCreateNewFile(path_href_report);
                    this.createNewFile(path_href_report);
                    this.writeHrefDataToCSVFile(path_href_report, arr_hrefs, page_item);

                    //get content each href and write content to csv file
                    String path_file_data_result = fullPathDir + "/result/" + web_name + ".csv";
                    //String path_file_data_result = fullPathDir + "/result_mynavi/" + dateFormat.format(date) + "_result_" + web_name + ".csv";
                    this.getContentHref(path_file_data_result, arr_hrefs, arr_caption, arr_rank);
                    
                    //include list href to data storage
                    this.writeHrefToStorageCSVFile(path_file_data_storage, arr_hrefs);
                }
            }

        } catch (StopRequestException ex) {
            getContext().setVariable("check_status", "GetHref. function test. StopRequestException: " + ex.toString());
            throw ex;
        } catch (Exception e) {
            getContext().setVariable("check_status", "GetHref. function test. Exception: " + e.toString());
            System.err.println("Usage: java PageSaver url local_file");
        }
    }
    
    public void createNewFile(String dir_path){
        try{
            File file = new File(dir_path);
            if(!file.exists()){
                file.createNewFile();
            }
            
            getContext().setVariable("path_href_file", dir_path);
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java. Create new file csv error. " + e.toString());
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
            getContext().setVariable("check_status", "GetHref.java. Create new file csv error. " + e.toString());
        }
    }
    
    public ArrayList<String> readDataFromCSV(String path_file_name){
        
        BufferedReader fileReader = null;
        //Create a new list of href to be filled by CSV file data 
        ArrayList<String> list_variable = new ArrayList<String>();
        
        try {
             
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(path_file_name));
            //Read the CSV file header to skip it
            fileReader.readLine();
             
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all information available in line
                String[] infor = line.split(",");
                list_variable.add(infor[0]);
            }
            
            getContext().setVariable("check_status", "GetHref.java function readDataFromCSV. Total href in data storage: " + list_variable.size() );
        } 
        catch (Exception e) {
            getContext().setVariable("check_status", "GetHref.java function readDataFromCSV error. Error in CsvFileReader !!!. IOException: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                getContext().setVariable("check_status", "GetHref.java function readDataFromCSV error. Error while closing fileReader !!!. IOException: " + e.toString());
                e.printStackTrace();
            }
        }
        return list_variable;
    }
    
    public void writeHrefDataToCSVFile(String path_file, ArrayList<String> hrefs, String num_page){
        try{
            File in = new File(path_file);
            PrintWriter out = null;
            if ( in.exists() && !in.isDirectory() ) {
                out = new PrintWriter(new FileOutputStream(new File(path_file), true));
            }
            else {
                out = new PrintWriter(path_file);
            }
            for(int i = 0; i < hrefs.size(); i++){
                //out.append(" ");
                //out.append(",");
                out.append(hrefs.get(i));
                out.append(",");
                out.append(num_page);
                out.append(",");
                out.append(Integer.toString(i+1));
                out.append("\n");
            }
            //out.append(mapstring);
            out.close();
            getContext().setVariable("check_status", "GetHref.java function writeHrefDataToCSVFile success!!!!");
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function writeHrefDataToCSVFile error. IOException: " + e.toString());
        }
    }
    
    public void writeHrefToStorageCSVFile(String path_file, ArrayList<String> hrefs){
        try{
            File in = new File(path_file);
            PrintWriter out = null;
            if ( in.exists() && !in.isDirectory() ) {
                out = new PrintWriter(new FileOutputStream(new File(path_file), true));
            }
            else {
                out = new PrintWriter(path_file);
            }
            for(int i = 0; i < hrefs.size(); i++){
                out.append(hrefs.get(i));
                out.append("\n");
            }
            //out.append(mapstring);
            out.close();
            getContext().setVariable("check_status", "GetHref.java function writeHrefToStorageCSVFile success!!!!");
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function writeHrefToStorageCSVFile error. IOException: " + e.toString());
        }
    }
    
    public void getContentHref(String path_file, ArrayList<String> hrefs, ArrayList<String> captions, ArrayList<String> rank){
        String row_ind_href_empty = getContext().getVariableAsString("row_ind_href_empty");
        int row_ind = Integer.parseInt(row_ind_href_empty);
        for(int i = 0; i < hrefs.size(); i++){
            String str_url = hrefs.get(i);
            String str_caption = captions.get(i);
            String str_rank = rank.get(i);
            row_ind = row_ind+1;
            this.getContentAndWriteToCSVFile(path_file, str_url, str_caption, str_rank, row_ind);
            
            getContext().setVariable("row_ind_href_empty", row_ind);
        }
    }
    
    public void getContentAndWriteToCSVFile(String path_file_result, String href, String caption, String rank, int row_ind){
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

            Document doc_charset = Jsoup.connect(href).get();
            String charset = doc_charset.charset().toString();
            
            String title = doc_charset.select(class_title).text();
                  String name_company = doc_charset.select(class_company).text();
                  String company_after="";
                  if(name_company.indexOf(" | ")!=-1){
                      name_company=name_company.substring(0,name_company.indexOf("|")-1);
                  }
                  if(name_company.indexOf("｜")!=-1){
                      name_company=name_company.substring(0,name_company.indexOf("｜"));
                  }
                  if(name_company.indexOf("　| ")!=-1){
                      name_company=name_company.substring(0,name_company.indexOf("　| ")-1);
                  }
                  if(name_company.indexOf("　| ")!=-1){
                      name_company=name_company.substring(0,name_company.indexOf("|"));
                  }
                           if(name_company.indexOf("│")!=-1){
                            name_company=name_company.substring(0,name_company.indexOf("│")-1);
                           }

                                    
                  name_company=name_company.replace(" ","");
                  name_company=name_company.replace("　","");
                  
            
            
            String company_address = doc_charset.select(class_company_address.replace("contains(??)", "contains(住所)")).text();
                  String postcode = company_address.replace("\"", "");
                  postcode = postcode.replace("-", "");
                  postcode = postcode.replace(" ", "");
                  if(postcode.indexOf("〒")!=-1){
                  postcode = postcode.substring(postcode.indexOf("〒") + 1, postcode.indexOf("〒") + 8) ;
                  }
                  String company_recruiter = doc_charset.select(class_company_recruiter.replace("contains(????)", "contains(採用担当)")).text();
                  String company_phone = doc_charset.select(class_company_phone.replace("contains(??)", "contains(電話番号)")).text();
            String work_location = doc_charset.select(class_info_job.replace("contains(???)", "contains(勤務地)")).text();
            String post_date_period = doc_charset.select(class_date_period).text();
            String field_industry = doc_charset.select(class_industry.replace("contains(??)", "contains(業種)")).text();
            String main_office = doc_charset.select(class_main_office.replace("contains(?????)", "contains(本社所在地)")).text();
            String remark = doc_charset.select(class_remark.replace("contains(??)", "contains(備考)")).text();

            String fax = "";
            String email = "";
            //String rank = "";
            
            if(name_company.length() == 0){
                this.writeHrefEmptyCSVFile(row_ind, href, rank);
            }else{
                this.writeContentToCSVFile(path_file_result, href, title, caption, name_company, work_location, main_office, postcode,company_address, company_recruiter, company_phone, fax, email, field_industry, remark, rank, post_date_period, web_name);
            }
        
        } catch (StopRequestException ex) {
            getContext().setVariable("check_status", "GetHref.java function getContentAndWriteToCSVFile. StopRequestException: " + ex.toString() + href);
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function getContentAndWriteToCSVFile. IOException: " + e.toString() + href);
            throw new RuntimeException(e);
        }
            
    }
    
    public void writeHrefEmptyCSVFile(int rows_ind, String href, String rank){
        try{
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            
            String path_file = fullPathDir + "/result/href_empty.csv";
            
            File in = new File(path_file);
            PrintWriter out = null;
            if ( in.exists() && !in.isDirectory() ) {
                out = new PrintWriter(new FileOutputStream(new File(path_file), true));
            }
            else {
                out = new PrintWriter(path_file);
            }
            
            out.append("");
            out.append(",");
            out.append(Integer.toString(rows_ind));
            out.append(",");
            out.append(href);
            out.append(",");
            out.append(rank);
            out.append("\n");
            
            out.flush();
            out.close();
            getContext().setVariable("check_status", "GetHref.java function writeHrefToStorageCSVFile success!!!!");
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function writeHrefToStorageCSVFile error. IOException: " + e.toString());
        }
    }

    public void writeContentToCSVFile(String path_file, String href, String title, String caption, String company_name, String work_location, String main_office, String postcode,String company_address, String recruiter, String company_phone, String fax, String email, String industry, String remark, String rank, String post_period, String web_name){
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
                  out.print('"' + postcode.replace("\"", "") + '"');
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
        GetHref script = new GetHref();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "GetHref@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
