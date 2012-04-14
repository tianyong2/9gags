package net.gags.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GagsParser {

    private Document doc;
    private Elements gagsElements;
    private Element paginationElement;

    public GagsParser(String url) throws IOException {
        doc = Jsoup.connect(url).get();
        
        Elements content = doc.select("div#content");
        gagsElements = content.select("li.entry-item");
        paginationElement = content.select("div#pagination").get(0);
    }

    public List<Gag> getGags() {
        ArrayList<Gag> gags = new ArrayList<Gag>();
        Iterator<Element> i = gagsElements.iterator();

        while (i.hasNext()) {
            Element e = i.next();
            gags.add(getGag(e));
        }

        return gags;
    }

    public String getNextPage() {
        return getAttributeValue(paginationElement, "a#next_button", "href");
    }
    
    private Gag getGag(Element e) {
        String gagUrl = e.attr("data-url");
        String title = e.attr("data-text");
        int id = Integer.parseInt(e.attr("gagid"));
        
        Element content = e.select("div.content").get(0);
        String imageUrl = getAttributeValue(content, "img", "src");
        
        return new Gag(id, gagUrl, title, imageUrl);
    }

    private String getAttributeValue(Element e, String query, String attr) {
        Elements elem = e.select(query);
        return elem.get(0).attr(attr);
    }

}
