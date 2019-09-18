/**
 * Created on Mon Aug 26 19:55:50 JST 2019
 * HeartCore Robo Desktop v5.0.3 (ビルド番号 5.0.3-20190618.1)
 **/
 package scripts;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;

import java.sql.*;
import java.sql.Timestamp;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.Date;
// import java.text.SimpleDateFormat;
import java.lang.Object;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
public class NoseTest extends DefaultJavaTestScript  {

   public void test() {
       
      try {
          String output_path = getContext().getVariableAsString("_PROJECT_DIR");
          String db_name = "quoc.db";
          String filepath="";
          String web_name="";

          try{
            File file = new File(output_path+"\\result\\web_site.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

             web_name= br.readLine();           
             br.close();
           }catch(FileNotFoundException e){
           }catch(IOException e){
       }
          
          
          //String web_name= getContext().getVariableAsString("web_name");
               //getContext().setVariable("web_name", "web_site");
               //web_name = getContext().getVariableAsString("web_site");
                      String file_name = web_name + ".csv";
                      getContext().setVariable("test1", file_name);
               String file_all = "all.csv";
               //getContext().setVariable("output_path", "dir");
               //getContext().setVariable("file_path", "output_path" + "file_name");
                String file_full_path = output_path + "\\result\\" + web_name + ".csv";
                filepath = output_path + "\\result\\HB.csv";
            //String path_db = "jdbc:sqlite:{_PROJECT_DIR}/database/" + db_name;
                String path_db = "jdbc:sqlite:C:/sqlite/db/test/" + db_name;
            String table1 = "result_headTable";
            String table2 = "result_branchTable";
            String table3 = "resultTableTest";
                 String table_result = "resultTestTable";
            
            connectDB(db_name);
            createTable(db_name,web_name,table_result, file_name, path_db);
            //inputCSV(db_name,web_name,file_full_path,path_db);
            compareData(table_result, web_name, db_name );
            //getContext().setVariable("Number", "44");
            createFileOutput(web_name);
            exportDataToCSV(db_name, web_name, table_result);

            } catch (StopRequestException ex) {
         throw ex;
      }
   }
   private Connection  connectDB(String db_name){
        Connection conn = null;
        try {

            String path_db = "jdbc:sqlite:C:/sqlite/db/test/" + db_name;
            Class.forName("org.sqlite.JDBC");
            // c = DriverManager.getConnection("jdbc:sqlite:test.db");
            conn = DriverManager.getConnection(path_db);
            
        } catch ( Exception e ) {
            getContext().setVariable("error", e.getClass().getName() + ": " + e.getMessage());
        }
        getContext().setVariable("result", "Opened database successfully");
        return conn;
    }

    //☆テーブルを落とす

public void inputCSV(String db_name,String web_name, String file_name, String path_db){
    getContext().setVariable("test2", file_name);
        try{
            FileInputStream fis = new FileInputStream(new File(file_name));
            InputStreamReader isr;
            if(web_name.equals("doda")){
                  isr = new InputStreamReader(fis , "SHIFT-JIS");
            }else{
                 isr = new InputStreamReader(fis , "UTF-8");
             }
            BufferedReader br = new BufferedReader(isr);
            getContext().setVariable("test2", "3");
            csv2db(web_name, br, path_db, web_name); //CSVでDBの内容を追加(上書き)する
            getContext().setVariable("test2", "4");
            br.close(); isr.close(); fis.close();
            getContext().setVariable("test2", "2");

            //FileInputStream fis2 = new FileInputStream(new File(inputCsv));
            //InputStreamReader isr2 = new InputStreamReader(fis2 , "UTF-8");
            //BufferedReader br2 = new BufferedReader(isr2);
            //String outText = matchDB(table, br2); //CSVに対応するDBのデータを取り出す
            //br2.close(); isr2.close(); fis2.close();//

            //FileOutputStream fos = new FileOutputStream(new File(outputCsv));
            //OutputStreamWriter osw = new OutputStreamWriter(fos , "UTF-8");
            //BufferedWriter bw = new BufferedWriter(osw);
            //bw.write(outText);
            //bw.close(); osw.close(); fos.close();
        } 
             catch(FileNotFoundException exception) { exception.printStackTrace(); 
             getContext().setVariable("test2", "file not found");}
                  
        catch(IOException exception) { exception.printStackTrace(); 
        getContext().setVariable("test2", "file not found2");}
    }




    //☆テーブルの作成
   public void createTable(String db_name,String web_name, String table_result, String file_name, String path_db){
       getContext().setVariable("Number", "20");
        // SQLite connection string
            // String url = "jdbc:sqlite:C://sqlite/db/test/" + db_name;
            String url = "jdbc:sqlite:C:/sqlite/db/test/" + db_name;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + web_name + "(\n"
                        //+ "    id  VARCHAR (50) PRIMARY KEY NOT NULL,\n"
                        + "    URL TEXT, \n"
                        + "    sorttitle TEXT, \n"
                        + "    companyname TEXT, \n"
                        + "    location TEXT, \n"
                        + "    mainaddress TEXT, \n"
                        + "    postcode TEXT, \n"
                        + "    address TEXT, \n"
                        + "    inchargeof TEXT, \n"
                        + "    phone TEXT, \n"
                        + "    fax TEXT, \n"
                        + "    email TEXT,\n"
                        + "    sort TEXT,\n"
                        + "    remarks TEXT, \n"
                        + "    rank TEXT, \n"
                        + "    term TEXT, \n"
                        + "    sitename TEXT \n"
                        + ")";
                        

        String sql2 = "CREATE TABLE IF NOT EXISTS CorporateNumberTable(\n"
                        + "    sequenceNumber TEXT,\n"
                        + "    corporateNumber TEXT PRIMARY KEY,\n"
                        + "    process TEXT,\n"
                        + "    correct TEXT,\n"
                        + "    updateDate TEXT,\n"
                        + "    changeDate TEXT,\n"
                        + "    name TEXT,\n"
                        + "    nameImageId TEXT,\n"
                        + "    kind TEXT,\n"
                        + "    prefectureName TEXT,\n"
                        + "    cityName TEXT,\n"
                        + "    streetNumber TEXT,\n"
                        + "    addressImageId TEXT,\n"
                        + "    prefectureCode TEXT,\n"
                        + "    cityCode TEXT,\n"
                        + "    postCode TEXT,\n"
                        + "    addressOutside TEXT,\n"
                        + "    addressOutsideImageId TEXT,\n"
                        + "    closeDate TEXT,\n"
                        + "    closeCause TEXT,\n"
                        + "    successorCorporateNumber TEXT,\n"
                        + "    changeCause TEXT,\n"
                        + "    assignmentDate TEXT,\n"
                        + "    latest TEXT,\n"
                        + "    enName TEXT,\n"
                        + "    enPrefectureName TEXT,\n"
                        + "    enCityName TEXT,\n"
                        + "    enAddressOutside TEXT,\n"
                        + "    furigana TEXT,\n"
                        + "    hihyoji TEXT\n"
                        + ")";

          String sql3 = "CREATE TABLE IF NOT EXISTS " + web_name + "newtable(\n"
                        + "    id  INT PRIMARY KEY,\n"
                        + "    URL TEXT, \n"
                        + "    sorttitle TEXT, \n"
                        + "    companyname TEXT, \n"
                        + "    location TEXT, \n"
                                    + "    mainaddress TEXT, \n"
                                    + "    postcode TEXT, \n"
                        + "    address TEXT, \n"
                        + "    inchargeof TEXT, \n"
                        + "    phone TEXT, \n"
                        + "    fax TEXT, \n"
                        + "    email TEXT,\n"
                        + "    sort TEXT,\n"
                        + "    remarks TEXT, \n"
                        + "    rank TEXT, \n"
                        + "    term TEXT, \n"
                        + "    sitename TEXT \n"
                        + ")";

        String sql4 = "CREATE TABLE IF NOT EXISTS " + table_result + "(\n"
                        + "    id  INT, \n"
                        + "    URL TEXT, \n"
                        + "    corporateNum TEXT, \n"
                        + "    branchNum INT, \n"
                        + "    sorttitle TEXT, \n"
                        + "    companyname TEXT, \n"
                        + "    location TEXT, \n"
                        + "    mainaddress TEXT, \n"
                        + "    postcode TEXT, \n"
                        + "    address TEXT, \n"
                        + "    inchargeof TEXT, \n"
                        + "    phone TEXT, \n"
                        + "    fax TEXT, \n"
                        + "    email TEXT,\n"
                        + "    sort TEXT,\n"
                        + "    remarks TEXT, \n"
                        + "    rank TEXT, \n"
                        + "    term TEXT, \n"
                        + "    sitename TEXT \n"
                        + ")";

          String sql5 = "CREATE TABLE IF NOT EXISTS " + table_result + "_1(\n"
                        + "    URL TEXT, \n"
                        + "    corporateNum TEXT, \n"
                        + "    branchNum TEXT, \n"
                        + "    sorttitle TEXT, \n"
                        + "    companyname TEXT, \n"
                        + "    location TEXT, \n"
                        + "    mainaddress TEXT, \n"
                        + "    address TEXT, \n"
                        + "    inchargeof TEXT, \n"
                        + "    phone TEXT, \n"
                        + "    fax TEXT, \n"
                        + "    email TEXT,\n"
                        + "    sort TEXT,\n"
                        + "    remarks TEXT, \n"
                        + "    rank TEXT, \n"
                        + "    term TEXT, \n"
                        + "    sitename TEXT \n"
                        + ")";
                        getContext().setVariable("Number", "3");
                        
                        
                                 String drop1 ="DROP TABLE " + web_name + ";\n";
                                 String drop2 ="DROP TABLE CorporateNumberTable;\n";
                                 String drop3 ="DROP TABLE " + web_name + "newtable;\n";
                                 String drop4 ="DROP TABLE " + table_result + ";\n";
                                 String drop5 ="DROP TABLE " + table_result + "_1;\n";
                        //try (Connection conn = DriverManager.getConnection(url);
                //Statement stmt = conn.createStatement()) {
          try {
              Connection conn = DriverManager.getConnection(url);
              Statement stmt = conn.createStatement();
              
            // create a new table
            //stmt.execute(drop1);
            getContext().setVariable("test3", "20");
            //stmt.execute(sql);
            getContext().setVariable("test3", "21");
            //insertDB(db_name, file_name,web_name);
            getContext().setVariable("test3", "22");
                 //stmt.execute(sql2);
            getContext().setVariable("test3", "23");
                 stmt.execute(drop3);
                 getContext().setVariable("test3", "24");
                        stmt.execute(sql3);
                        getContext().setVariable("test3", "25");
                        stmt.execute(sql4);
            stmt.execute(drop4);
                        getContext().setVariable("test3", "26");
            stmt.execute(sql4);
            getContext().setVariable("test3", "27");

//stmt.execute(sql5);
            stmt.execute(drop5);
            getContext().setVariable("test3", "28");
            stmt.execute(sql5);
            getContext().setVariable("test3", "29");
                       getContext().setVariable("result", "Created table database successfully");
        } catch (SQLException e) {
            // System.out.println(e.getMessage());
            getContext().setVariable("error", e.getMessage());
        }
    }

    //☆コマンドの実行
   public void compareData(String table_result, String web_name, String db_name){
        //String sql = "INSERT INTO " + web_name + "newtable\n"
                    //+"SELECT rowid, * FROM " + web_name +";\n";
                    String sql = "INSERT INTO " + web_name + "newtable\n"
                    +"SELECT rowid, * FROM " +web_name +";\n";
                    
        String sql1 ="INSERT INTO " + table_result + "\n"
            +"SELECT p.id, p.URL, pp.corporateNumber, 9999, p.sorttitle, p.companyname, p.location, p.mainaddress, p.postcode, p.address, p.inchargeof, p.phone, p.fax, p.email, p.sort, p.remarks, p.rank, p.term, p.sitename\n"
            //+"FROM CorporateNumberTable as pp, " + web_name + " as p\n"
            +"FROM CorporateNumberTable as pp, " + web_name + "newtable as p\n"
            +"WHERE pp.name = p.companyname and substr(pp.postCode,1,3) <> substr(p.postcode,1,3)\n"
            +"GROUP BY p.URL\n"
            +"ORDER BY p.id;\n";

       String sql2 ="INSERT INTO " + table_result + "\n"
            +"SELECT r.id, r.URL, r.corporateNum,\n"
            +"(SELECT count(*) FROM " + table_result + " as r1 WHERE r1.companyname = r.companyname and r1.postcode < r.postcode) + 1,\n"
            +"r.sorttitle, r.companyname, r.location, r.mainaddress, r.postcode, r.address, r.inchargeof, r.phone, r.fax, r.email, r.sort, r.remarks, r.rank, r.term, r.sitename\n"
            +"FROM " + table_result + " as r\n"
            +"ORDER BY r.id;\n";

        String sql3 = " DELETE FROM " + table_result + " WHERE branchNum = 9999;\n";

        String sql4 = "INSERT INTO " + table_result + "\n"
        +"SELECT p.id, p.URL, pp.corporateNumber, 0, p.sorttitle, p.companyname, p.location, p.mainaddress, p.postcode, p.address, p.inchargeof, p.phone, p.fax, p.email, p.sort, p.remarks, p.rank, p.term, p.sitename\n"
            +"FROM CorporateNumberTable as pp, " + web_name + "newtable as p\n"
            +"WHERE pp.name = p.companyname and substr(pp.postCode,1,3) = substr(p.postcode,1,3)\n"
            +"GROUP BY p.URL\n"
        +"ORDER BY p.id;\n";
        
        String sql5 = "INSERT INTO " + table_result + "\n"
        +"SELECT p.id, p.URL, 99, 0, p.sorttitle, p.companyname, p.location, p.mainaddress, p.postcode, p.address, p.inchargeof, p.phone, p.fax, p.email, p.sort, p.remarks, p.rank, p.term, p.sitename\n"
        +"FROM " + web_name + "newtable as p\n"
        +"WHERE NOT EXISTS(SELECT * FROM " + table_result + " as r WHERE p.id = r.id)\n"
        +"GROUP BY p.URL\n"
        +"ORDER BY p.id;\n";

        String sql6 ="INSERT INTO " + table_result + "_1\n"
        +"SELECT URL, corporateNum, branchNum, sorttitle, companyname, location, mainaddress, address, inchargeof, phone, fax, email, sort, remarks, rank, term, sitename\n"
        +"FROM " + table_result + "\n"
        +"GROUP BY URL\n"
            +"ORDER BY id;\n";
        
             //String sql6 ="SELECT rowid, * FROM " +mynavi;\n";
        

        //String sql6 = ".separator ,\n";
       //String sql7 = ".output " + web_name + "HB\n"
        //+"SELECT * FROM " + table_result + "_1;\n";

                //try (Connection conn = this.connectDB(db_name);
                //Statement stmt = conn.createStatement()) {

        try {
            getContext().setVariable("Number", "66");
            Connection conn = this.connectDB(db_name);
            getContext().setVariable("Number", "77");
            Statement stmt = conn.createStatement();
            getContext().setVariable("Number", "88");
                  stmt.execute(sql);
                   getContext().setVariable("Number", "s0");
            stmt.execute(sql1);
            getContext().setVariable("Number", "s1");
            stmt.execute(sql2);
            getContext().setVariable("Number", "s2");
            stmt.execute(sql3);
            getContext().setVariable("Number", "s3");
            stmt.execute(sql4); //test
            getContext().setVariable("Number", "s4");
            stmt.execute(sql5); //test
            getContext().setVariable("Number", "s5");
            stmt.execute(sql6);
            getContext().setVariable("Number", "s6");
            //stmt.execute(sql7); //test
            //getContext().setVariable("Number", "s7");
            //stmt.execute(sql8); //test
            getContext().setVariable("result", "Created table database successfully");
        } catch (SQLException e) {
            getContext().setVariable("error", e.getMessage() + ", sql: " + sql);
        }
    }
    public void exportDataToCSV(String db_name, String web_name, String table_result){
        
        
        String sql = "SELECT * FROM " + table_result + "_1";
        try (Connection conn = this.connectDB(db_name);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){

            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");

                 String path_file = fullPathDir + "\\result\\HB.csv";
                 this.deleteCreateNewFile(path_file);

            while (rs.next()) {
                //str_query = str_query +  rs.getString("name") + "," + rs.getString("corporateNumber") + "," + rs.getString("postCode") + "/n";
                writeContentToCSVFile(path_file, rs.getString("URL"), rs.getString("corporateNum"), rs.getString("branchNum"), rs.getString("sorttitle"), rs.getString("companyname"), rs.getString("location"), rs.getString("mainaddress"), rs.getString("address"), rs.getString("inchargeof"), rs.getString("phone"), rs.getString("fax"), rs.getString("email"), rs.getString("sort"), rs.getString("remarks"), rs.getString("rank"), rs.getString("term"), rs.getString("sitename"));
            }
            getContext().setVariable("check_status", "Export database to csv file successful.");
        } catch (SQLException e) {
            getContext().setVariable("error", "Export database to csv file. Error: " + e.getMessage());
        }  catch (Exception e) {
            getContext().setVariable("error", "Export database to csv file. Error: " + e.getMessage());
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


public void createFileOutput(String path_file){
        try {
            //Get directory project
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");
            
            ArrayList<String> title_result = new ArrayList<String>() {{
                add("URL");
                add("corporateNumber");
                add("枝番");
                add("職種名");
                add("会社名");
                add("勤務地");
                add("本社所在地");
                add("住所");
                add("採用担当");
                add("電話番号");
                add("FAX");
                add("E-mail");
                add("業種");
                add("備考");
                add("掲載ランク");
                add("掲載予定期間");
                add("サイト名");
            }};
            getContext().setVariable("check_status", "createFileOutput. java function successfull. ");
        } catch (IOException ex) {
            getContext().setVariable("check_status", "createFileOutput. java function test error. " + ex.toString());
        }
    }


public void writeContentToCSVFile(String path_file, String URL, String corporateNum, String branchNum, String sorttitle, String companyname, String location, String mainaddress, String address, String inchargeof, String phone, String fax, String email, String sort, String remarks, String rank, String term,String sitename){
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
            
            out.print(URL);
            out.print(",");
            out.print('"' + corporateNum.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + branchNum.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + sorttitle.replace("\"", "") + '"');
                  out.print(",");
            out.print('"' + companyname.replace("\"", "") + '"');
            out.print(",");            
            out.print('"' + location.replace("\"", "") + '"');
            out.print(",");            
            out.print('"' + mainaddress.replace("\"", "") + '"');
            out.print(",");            
            out.print('"' + address.replace("\"", "") + '"');
            out.print(",");            
            out.print('"' + inchargeof.replace("\"", "") + '"');
            out.print(",");            
            out.print('"' + phone.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + fax.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + email.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + sort.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + remarks.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + rank.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + term.replace("\"", "") + '"');
            out.print(",");
            out.print('"' + sitename.replace("\"", "") + '"');
            out.print("\n");

            
            
            out.flush();
            out.close();
            getContext().setVariable("check_status", "writeContentToCSVFile. Successful");
        }catch(IOException e){
            getContext().setVariable("check_status", "writeContentToCSVFile. IOException: " + e.toString());
        }
    }

    //private static void csv2db(String table, BufferedReader br, String path_db, String web_name) 
    private void csv2db(String table, BufferedReader br, String path_db, String web_name) 
    {
        getContext().setVariable("test2", "5");
        Connection connection = null;
        try{
            getContext().setVariable("test2", "6");

            connection = DriverManager.getConnection(path_db); //DB名指定
            Statement statement = connection.createStatement();
            getContext().setVariable("test2", "7");

            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists "+ web_name +" (uid integer UNIQUE, value string)"); //table名指定
            getContext().setVariable("test2", "8");

            String line = ""; //一行ずつ読み込む                
            boolean header = true; //ヘッダーフラグ
            while((line = br.readLine()) != null)
            {
                getContext().setVariable("test2", line);

                if(header) { header=false; continue; } //headerはとばす

                StringTokenizer token = new StringTokenizer(line, ",");
                int column_queue = 16; //何カラム存在するか
                String insert="";
                /**
                * パターン1: 100, 1111, null
                * パターン2: 100, 1111, null, etc ,etc...
                * パターン3: 100, 1111, relation
                * パターン4: 100, 1111, relation, etc, etc...
                **/
                while(token.hasMoreTokens())
                {
                    String tmpString = token.nextToken();
                    System.out.println("column :"+(++column_queue)+"\t"+tmpString);
                    //2と3列目だけ
                    if(column_queue == 2 || column_queue == 3) insert += "\'" + tmpString + "\', ";
                }
                if(column_queue == 2) //column=3がなく，column=2で止まっていた場合の追加
                { //3列目に何も書かれていない場合はDBから情報を引っ張ってきて，上書きする
                    String uid = insert.substring(0,insert.length()-2).replaceAll("'","");
                    String value = getDB(table, uid, path_db,web_name);
                    insert +=  "\'" + value + "\', ";
                    System.out.print("select * from "+web_name+" where uid = "+uid);
                    System.out.println(" → uid : "+uid+"\t getString('value') : "+value);
                }                
                
                insert = insert.substring(0,insert.length()-2);//最後の","までを削除した文            
                   insert ="insert or replace into "+ web_name +" values("+insert+")";                   
                  System.out.println("insert: \t"+insert);
                   statement.executeUpdate(insert);
            }
        }
        catch( IOException exception) { exception.printStackTrace(); }
        catch(SQLException exception) { System.err.println("exception :"+exception.getMessage()); }
        finally {
            try{ if(connection != null) connection.close(); }
            catch(SQLException exception) { System.err.println(exception); }
        }
    }
    
    
        private static String getDB(String table, String uid, String path_db, String web_name)
    {
        String value = "";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(path_db); //DB名指定
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String sql = "select * from "+ web_name +" where uid = "+uid;
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
            {
                value = rs.getString("value");
            }
        } 
        catch(SQLException exception) { System.err.println("exception :"+exception.getMessage()); }
        finally {
            try{ if(connection != null) connection.close(); }
            catch(SQLException exception) { System.err.println(exception); }            
        }
        return value;
    }
    
    
    
    public void insertDB(String db_name, String file_name,String web_name){
        getContext().setVariable("Number", "db1");
        String sql = "INSERT INTO "+ web_name +" (URL, sorttitle, companyname, location, mainaddress, postcode, address, inchargeof, phone, fax, email, sort, remarks, rank, term, sitename) " 
                                   + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                   
        //Connection conn = this.connectDB(db_name);
        //PreparedStatement pstmt = null;
        getContext().setVariable("Number", "db2");
        try(Connection conn = this.connectDB(db_name);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
                    
            String path = getContext().getVariableAsString("_PROJECT_DIR");
            String fullPathDir = URLDecoder.decode(path, "UTF-8");
            fullPathDir = fullPathDir.replace("\\", "/");

                  //String fullPath = fullPathDir +  "/" + file_name;
                  String fullPath = fullPathDir +  "\\result\\" + file_name;
            // BufferedReader br = new BufferedReader(new FileReader(fullPath));
            getContext().setVariable("Number", "db3");
            Path path_file = Paths.get(fullPath);
            BufferedReader br = Files.newBufferedReader(path_file);
            String line;
            
            while ( (line=br.readLine()) != null){
                String[] values = line.split(",");
                getContext().setVariable("Number", "db4");
                //Date date= new Date();
                //long time = date.getTime();
                //Timestamp ts = new Timestamp(time);
                
                //String sql = "INSERT INTO mynavi (id, URL, sorttitle, companyname, location, mainaddress, address, inchargeof, phone, fax, email, sort, remarks, rank, term, sitename) " 
                            //+ "values('" + values[0] + "', '" + values[1] + "', '" + values[2] + "', '" + values[3] + "', '" + values[4] + "', '" + values[5] + "', '" + values[6] + "', '" + values[7] + "', '" + values[8] + "', '" 
                            //+ values[9] + "', '" + values[10] + "', '" + values[11] + "', '" + values[12] + "', '" + values[13] + "', '" + values[4] + "')";
                //getContext().setVariable("result", sql);
                
                // pstmt = conn.prepareStatement(sql);
                
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                getContext().setVariable("Number", "db5");
                //pstmt.setString(1, Long.toString(timestamp.getTime()));
                pstmt.setString(1, values[0]);
                getContext().setVariable("Number", "db6");
                pstmt.setString(2, values[1]);
                getContext().setVariable("Number", "db7");
                pstmt.setString(3, values[2]);
                getContext().setVariable("Number", "db8");
                pstmt.setString(4, values[3]);
                getContext().setVariable("Number", "db9");
                pstmt.setString(5, values[4]);
                getContext().setVariable("Number", "db10");
                pstmt.setString(6, values[5]);
                getContext().setVariable("Number", "db11");
                pstmt.setString(7, values[6]);
                getContext().setVariable("Number", "db12");
                pstmt.setString(8, values[7]);
                getContext().setVariable("Number", "db13");
                pstmt.setString(9, values[8]);
                getContext().setVariable("Number", "db14");
                pstmt.setString(10, values[9]);
                getContext().setVariable("Number", "db15");
                pstmt.setString(11, values[10]);
                getContext().setVariable("Number", "db16");
                pstmt.setString(12, values[11]);
                getContext().setVariable("Number", "db17");
                pstmt.setString(13, values[12]);
                getContext().setVariable("Number", "db18");
                pstmt.setString(14, values[13]);
                getContext().setVariable("Number", "db19");
                pstmt.setString(15, values[14]);
                getContext().setVariable("Number", "db20");
                pstmt.setString(16, values[15]);
                
                //getContext().setVariable("Number", "db6");
                pstmt.executeUpdate();
            }
            getContext().setVariable("Number", "db7");
            br.close();
            getContext().setVariable("result", "Import data from csv file to database successfull");
        }catch(IOException e){
            getContext().setVariable("error", e.toString());
        } catch (SQLException e) {
            // System.out.println(e.getMessage());
            getContext().setVariable("error", e.getMessage());
        }
    }
    
   public static void main(String args[]) {
      NoseTest script = new NoseTest();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "NoseTest@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
   }
