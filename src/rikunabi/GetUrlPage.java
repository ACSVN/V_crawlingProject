/**
 * Created on Thu Jul 04 12:04:18 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package rikunabi;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;

public class GetUrlPage extends DefaultJavaTestScript  {

	public void test() {
		try {
			String replace_nxt_p = getContext().getVariableAsString("replace_nxt_p");
			String page_item = getContext().getVariableAsString("page_item");
			String str_url = getContext().getVariableAsString("url_web");
			String case_display = getContext().getVariableAsString("case_display");
			String url_page = "";
			
			int page_curr = Integer.parseInt(page_item);
			int str_page  = (page_curr-1)*(Integer.parseInt(case_display))+1;
			String str_replace =  "crn" + String.valueOf(str_page) + ".html";
			url_page = str_url + str_replace;
			
			getContext().setVariable("path_url_page", url_page);
		} catch (StopRequestException ex) {
			throw ex;
		}
	}

	public static void main(String args[]) {
		GetUrlPage script = new GetUrlPage();
		ApplicationSupport robot = new ApplicationSupport();
		AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "GetUrlPage@" + Integer.toHexString(script.hashCode()), args, System.out, false);
		new Thread(runnable).start();
	}
}
