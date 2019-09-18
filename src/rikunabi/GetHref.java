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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStreamWriter;

public class GetHref extends DefaultJavaTestScript  {
    
    public void test() {
        ArrayList<String> arr_href_storage = new ArrayList<String>();
        ArrayList<String> arr_hrefs = new ArrayList<String>();
        ArrayList<String> arr_name_com = new ArrayList<String>();
        ArrayList<String> arr_title = new ArrayList<String>();
        ArrayList<String> arr_caption = new ArrayList<String>();
        ArrayList<String> arr_rank = new ArrayList<String>();
        
        try {
            //get variable from tpr file
            String url_web_item = getContext().getVariableAsString("url_web_item");
            String menu_item = getContext().getVariableAsString("menu_item");
            String web_name = getContext().getVariableAsString("web_name");
            String class_href = getContext().getVariableAsString("class_href");
            String class_company = getContext().getVariableAsString("class_company");
            String class_title = getContext().getVariableAsString("class_title");
            String class_caption = getContext().getVariableAsString("class_caption");
            String str_url = getContext().getVariableAsString("url");
            String class_domain = getContext().getVariableAsString("class_domain");
            String page_item = getContext().getVariableAsString("page_item");
            int num_page = Integer.parseInt(page_item);
            
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            
            //load list href in data storage
            //String path_file_data_storage = fullPathDir + "/result/data_storage_" + web_name + ".csv";

            //create new file if have not data storage
            //this.createNewFile(path_file_data_storage);
            //load data storge of href
            //arr_href_storage = this.readDataFromCSV(path_file_data_storage);
            
            String fullPath = fullPathDir +  "/html_file";
            
            String content = "";

            Document doc = Jsoup.connect(url_web_item).get();
                    
            Elements hrefs = doc.select(class_href);
                
            for(int j = 0; j <  hrefs.size(); j++){
                String str_url_tmp = "";
                //String caption_tmp = "";
                
                String caption_tmp = doc.select("body > div > div > div.rnn-stage > div > div > div > ul > li:nth-child(" + (j+1) + ") > div > div.rnn-jobOfferList__item__offer__catch > a > div > div.rnn-offerCatch__contents > p").text();
                //String caption_tmp = captions.get(i).text();
                if(caption_tmp.length() == 0){
                    caption_tmp = doc.select("body > div > div > div > div > div > div > ul.rnn-group.rnn-group--xm.rnn-jobOfferList > li:nth-child(" + (j+1) + ") > div > div > a > div > div > p.rnn-offerCatch__contents__title.js-abScreen__main__title").text();
                }
                if(caption_tmp.length() == 0){
                    caption_tmp = doc.select("body > div > div > div > div > div > div > ul.rnn-group.rnn-group--xm.rnn-jobOfferList > li:nth-child(" + (j+1) + ") > div > div.rnn-jobOfferList__item__offer__catch > div > div > p").text();
                }
                
                String rank = doc.select("div.rnn-group.rnn-group--xm > ul.rnn-group.rnn-group--xm.rnn-jobOfferList > li:nth-child(" + (j+1) + ")").attr("data-ntype");
                                
                if(hrefs.get(j).attr("href").indexOf(class_domain) < 0){
                    str_url_tmp = class_domain + hrefs.get(j).attr("href");
                }else{
                    str_url_tmp = hrefs.get(j).attr("href");
                }
                str_url_tmp = str_url_tmp.replace("msg/", "");
                str_url_tmp = str_url_tmp.replace("/nx1_", "/nx2_");
                str_url_tmp = str_url_tmp.replaceAll("[/]+[adv]+[0-9]+[/]", "/");
                
                //check url item have exist in data storage
                if(!arr_href_storage.contains(str_url_tmp)){
                    arr_hrefs.add(str_url_tmp);
                    arr_caption.add(caption_tmp);
                    arr_rank.add(rank);
                }
            }
            String path_href_report = fullPathDir + "/result/hreflist.csv";
            //this.createNewFile(path_href_report);
            this.writeHrefDataToCSVFile(path_href_report, arr_hrefs, page_item, menu_item);

            //get content each href and write content to csv file
            String path_file_data_result = fullPathDir + "/result/" + web_name + ".csv";
            this.getContentHref(path_file_data_result, arr_hrefs, arr_caption, arr_rank);
            
            //include list href to data storage
            //this.writeHrefToStorageCSVFile(path_file_data_storage, arr_hrefs);

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
    
    public void writeHrefDataToCSVFile(String path_file, ArrayList<String> hrefs, String num_page, String menu_item){
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
                out.append(",");
                out.append(menu_item);
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
        for(int i = 0; i < hrefs.size(); i++){
            if(rank.get(i).equals("t")){
                continue;
            }
            String str_url = hrefs.get(i);
            String str_caption = captions.get(i);
            String str_rank = rank.get(i);
            
            this.getContentAndWriteToCSVFile(path_file, str_url, str_caption, str_rank);
        }
    }
    
    public void getContentAndWriteToCSVFile(String path_file_result, String href, String caption, String rank){
        String web_name = getContext().getVariableAsString("web_name");
        String class_company = getContext().getVariableAsString("class_company");
        String class_title = getContext().getVariableAsString("class_title");
        String class_info_company = getContext().getVariableAsString("class_company_info");
        String class_info_job = getContext().getVariableAsString("class_info_job");
        String class_date_period = getContext().getVariableAsString("class_date_period");
        String class_industry = getContext().getVariableAsString("class_industry");
        
        try {

            Document doc_charset = Jsoup.connect(href).get();
            String charset = doc_charset.charset().toString();
            
            String title = doc_charset.select(class_title).text();
            if(title.length() == 0){
                title = doc_charset.select("#jobs_head > div.multicol.clr.company > div.leftcol.agent_width > h2 > span").text();
            }
            //String name_company = doc_charset.select(class_company).text();
            String name_company = doc_charset.select("p.rnn-offerCompanyName").text();
            if(name_company.length() == 0){
                name_company = doc_charset.select("#jobs_head > div.multicol.clr.company > div.leftcol.agent_width > h1 > span").text();
            }
            name_company = name_company.replace("\"", "");
                name_company = name_company.replace("　", "");
                name_company = name_company.replace(" ", "");
            
            String company_address = doc_charset.select(class_info_company.replace("contains(???)", "contains(連絡先)")).text();
            company_address = company_address.replace(name_company, "");
            company_address = company_address.replace("（ホームページ） ", "");
            if(company_address.length() == 0){
                company_address = doc_charset.select("#overview > div.multicol.clr > div.leftcol > dl > dt:contains(事業所) + dd").text();
            }
            String postcode = company_address.replace("\"", "");
            postcode = postcode.replace("-", "");
            postcode = postcode.replace(" ", "");
            postcode = postcode.substring(postcode.indexOf("〒") + 1, postcode.indexOf("〒") + 8) ;
            
            String company_recruiter = "";
            String company_phone = "";
            
            String work_location = doc_charset.select(class_info_job.replace("contains(???)", "contains(勤務地)")).text();
            if(work_location.length() == 0){
                work_location = doc_charset.select("#n_gen_agent > div.css_layout > div > dl > dt:contains(勤務地) + dd").text();
            }
            String post_date_period = doc_charset.select(class_date_period).text();
            String field_industry = doc_charset.select(class_industry.replace("contains(??????)", "contains(業種から探す)")).text();
            if(field_industry.length() == 0){
                field_industry = doc_charset.select("#overview > div.multicol.clr > div.leftcol > dl > dt:contains(業種) + dd").text();
            }
            
            String main_office = "";
            String fax = "";
            String email = "";
            String remark = "";
            //String rank = doc_charset.select(class_rank).attr("data-ntype");
            
            this.writeContentToCSVFile(path_file_result, href, title, name_company, work_location, main_office, postcode,company_address, company_recruiter, company_phone, fax, email, field_industry, remark, rank, post_date_period, web_name);
            
        } catch (StopRequestException ex) {
            getContext().setVariable("check_status", "GetHref.java function getContentAndWriteToCSVFile. StopRequestException: " + ex.toString() + href);
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function getContentAndWriteToCSVFile. IOException: " + e.toString() + href);
            throw new RuntimeException(e);
        }
            
    }
    
    public void writeContentToCSVFile(String path_file, String href, String title, String name_company, String work_location, String main_office, String postcode,String company_address, String company_recruiter, String company_phone, String fax, String email, String field_industry, String remark, String rank, String post_date_period, String web_name){
        
        try{
            
            File in = new File(path_file);
            PrintWriter out = null;
            OutputStream os = null;
            if ( in.exists() && !in.isDirectory() ) {
                os = new FileOutputStream(path_file, true);
            }
            else {
                os = new FileOutputStream(path_file, false);
            }
            
            //set format file
            os.write(239);
            os.write(187);
            os.write(191);
            
            //create output file
            out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            
            out.print(href);
            out.print(",");
            out.print('"' + title.replace("\"", "") + '"');
            out.print(",");
            //out.print('"' + caption.replace("\"", "") + '"');
            //out.print(",");
            out.print('"' + name_company.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + work_location.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + main_office.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + postcode.replace("\"", "") + '"');
            out.print(",");
                  out.print('"' + company_address.replace("\"", "") + '"');
                  out.print(",");
            //out.print('"' + field_industry.replace("\"", "") + '"');
            //out.print(",");
            out.print('"' + company_recruiter.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + company_phone.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + fax.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + email.replace("\"", "") + '"');
            out.print(",");
            //out.print('"' + company_address.replace("\"", "") + '"');
            //out.print(",");
                  out.print('"' + field_industry.replace("\"", "") + '"');
                  out.print(",");
            out.print('"' + remark.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + rank.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + post_date_period.replace("\"", "") + '"');
            out.print(",");
            out.print(web_name);
            out.print("\n");
            
            out.close();
            getContext().setVariable("check_status", "GetHref.java function writeContentToCSVFile successe!!!!!. title: " + title);
        }catch(IOException e){
            getContext().setVariable("check_status", "GetHref.java function writeContentToCSVFile. IOException: " + e.toString());
        }
    }
    
    public String convertStringShiftJISToUTF8(String str_in){
        String str_out = "";
        try{
            str_in = str_in.replace(",", "__");
            byte[] shiftJisBytes  = str_in.getBytes("Shift_JIS");
            String str_tmp = new String(shiftJisBytes, "Shift_JIS");
            byte[] utf8Bytes  = str_tmp.getBytes("UTF-8");
            str_out = new String(utf8Bytes, "Shift_JIS");
            
        }catch(IOException e){
            str_out = "error";
            throw new RuntimeException(e);
        }
        
        return str_out;
    }

    public static void main(String args[]) {
        GetHref script = new GetHref();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "GetHref@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
