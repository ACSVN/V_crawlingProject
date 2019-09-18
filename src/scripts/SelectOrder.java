/**
 * Created on Sun Jul 07 17:06:20 GMT+09:00 2019
 * HeartCore Robo Desktop v5.0.2 (ビルド番号 5.0.2-20190417.1)
 **/
package scripts;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;
import java.awt.EventQueue;
import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSlider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectOrder extends DefaultJavaTestScript implements ActionListener {
    
    public static final int ROWS = 1;
    public static final int COLS = 5;

    private JFrame frame;
    private JPanel flowLayoutPanel;
    private JPanel gridLayoutPanel;

    private JTextField[][] flowFields;
    private JTextField[][] gridFields;

   public void test() {
      try {
                  //改行コードを取得
                  String br = System.getProperty("line.separator");
                  
                  frame = new JFrame("Webクローラ設定");
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel,  BoxLayout.Y_AXIS));


        //取得するWebサイトの設定
            mainPanel.add(new JLabel("取得するWebサイトを選択"));            
            String[] SelectSite = {"Doda", "mynavi", "rikunabi"};
            JComboBox<String> combo1;
            combo1 = new JComboBox<String>(SelectSite);
            combo1.setPreferredSize(new Dimension(80, 30));
            mainPanel.add(combo1);
//            combo1.addActionListener(this);
            
        //新着のみとすべての変更設定
                 mainPanel.add(new JLabel("取得方法設定"));            
                 String[] new_or_all = {"新着のみ", "全て"};
            JComboBox<String> combo2;
                  combo2 = new JComboBox<String>(new_or_all);
            combo2.setPreferredSize(new Dimension(80, 30));
                  mainPanel.add(combo2);
                     
                   //キャプチャ取得有無の設定
                 mainPanel.add(new JLabel("キャプチャ取得設定"));            
                 String[] capture = {"有", "無"};
                 JComboBox<String> combo3;
                 combo3 = new JComboBox<String>(capture);
                 combo3.setPreferredSize(new Dimension(80, 30));
                 mainPanel.add(combo3);        
                 
                 //キャプチャ取得有無の設定
                 mainPanel.add(new JLabel("kintoneへのアップロード"));            
                 String[] kintone_upload = {"有", "無"};
                 JComboBox<String> combo4;
                 combo4 = new JComboBox<String>(kintone_upload);
                 combo4.setPreferredSize(new Dimension(80, 30));
                 mainPanel.add(combo4); 
             
            int result=-1;
            int clicknum=-1;
            while(clicknum != 0){            
            
             result = JOptionPane.showConfirmDialog(null, mainPanel, "Webクローラ動作設定", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                    clicknum = JOptionPane.showConfirmDialog(null, "対象サイト:"+(String)combo1.getSelectedItem()+br+"新着/すべて:"+(String)combo2.getSelectedItem()+br+"キャプチャ取得:"+(String)combo3.getSelectedItem()+br+"kintoneへのアップロード:"+(String)combo4.getSelectedItem()+br+"で実行しますか？","入力確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    getContext().setVariable("option_btn", 0);
                              getContext().setVariable("web_site", (String)combo1.getSelectedItem());
                              getContext().setVariable("new_or_all", (String)combo2.getSelectedItem());
                              getContext().setVariable("capture", (String)combo3.getSelectedItem());
                              getContext().setVariable("kintone_upload", (String)combo4.getSelectedItem());
            } else {
                    getContext().setVariable("option_btn", 1);
                    getContext().setVariable("web_site", "");
                    getContext().setVariable("new_or_all","");
                    getContext().setVariable("capture", "");
                    getContext().setVariable("kintone_upload", "");
                    clicknum=0;
            }
        }
            
      } catch (StopRequestException ex) {
         throw ex;
          }
     }
    public void actionPerformed(ActionEvent e) {
//          getContext().setVariable("text1", (String)combo1.getSelectedItem());
 //         getContext().setVariable("text2", (String)combo2.getSelectedItem());
   }
   
   public static void main(String args[]) {
      SelectOrder script = new SelectOrder();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Volume@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
   
   
}
