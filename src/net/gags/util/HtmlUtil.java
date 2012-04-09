package net.gags.util;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil
{

    public static Elements get(String url, String query) throws IOException
    {
        Document doc = Jsoup.connect(url).get();
        return doc.select(query);
    }
    
    public static String getAttributeValue(Element e, String tag, String attr)
    {
        Elements elem = e.getElementsByTag(tag);
        Iterator<Element> i = elem.iterator();
        if(i.hasNext()) return i.next().attr(attr);
        else return null;
    }
    
}
