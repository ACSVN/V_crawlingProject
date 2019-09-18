import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
 
 public class ExportCsv {
    public static void ExportCsv(int[] idList, String[] hrefList){
     
        try {
 
            // 出力ファイルの作成
            FileWriter f = new FileWriter("href.csv", false);
            PrintWriter p = new PrintWriter(new BufferedWriter(f));
 
            // ヘッダーを指定する
            p.print("href");
            p.print(",");
            p.print("k");
            p.print(",");
            p.print("i");
            p.println();
 
            // 内容をセットする
            for(int i = 0; i < idList.length; i++){
                p.print(idList[i]);
                p.print(",");
                p.print(hrefList[i]);
                p.println();    // 改行
            }
 
            // ファイルに書き出し閉じる
            p.close();
 
            System.out.println("ファイル出力完了！");
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         
    }
    }
