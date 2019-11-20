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

    private JFrame frame;

    public void test() {
        try {
            //改行コードを取得
            String br = System.getProperty("line.separator");

            frame = new JFrame("Webクローラ設定");
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            //取得するWebサイトの設定
            mainPanel.add(new JLabel("取得するWebサイトを選択"));
            String[] SelectSite = {"Doda", "mynavi", "rikunabi"};

            JComboBox<String> combo1;
            combo1 = new JComboBox<String>(SelectSite);
            combo1.setPreferredSize(new Dimension(80, 30));
            mainPanel.add(combo1);

            int result = -1;
            int clicknum = -1;

            while(clicknum != 0){
                result = JOptionPane.showConfirmDialog(null, mainPanel, "Webクローラ動作設定", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    clicknum = JOptionPane.showConfirmDialog(null, "対象サイト:"+(String)combo1.getSelectedItem()+br+"で実行しますか？","入力確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    getContext().setVariable("option_btn", 0);
                    getContext().setVariable("web_site", (String)combo1.getSelectedItem());
                } else {
                    getContext().setVariable("option_btn", 1);
                    getContext().setVariable("web_site", "");
                    clicknum = 0;
                }
            }
        } catch (StopRequestException ex) {
            throw ex;
        }
    }

    public static void main(String args[]) {
        SelectOrder script = new SelectOrder();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Volume@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
