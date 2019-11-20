package webcrawl;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawl {
    private String url = "";
    private String selector = "";

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelector() {
        return this.selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public ArrayList<String> getContentList() throws IOException {
        Document doc = Jsoup.connect(this.getUrl()).get();
        Elements elms = doc.select(this.getSelector());

        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < elms.size(); i++) {
            arr.add(elms.get(i).text());
        }
        return arr;
    }
}
