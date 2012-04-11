package net.gags.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import net.gags.util.HtmlUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.net.Uri;
import android.util.Log;

public class Gags
{
    
    public static final String HOT_PAGE = "http://9gag.com";
    public static final String TRENDING_PAGE = "http://9gag.com/trending";
    public static final String VOTE_PAGE = "http://9gag.com/vote";
    
    private String page;
    private ArrayList<Gag> gags;
    private int index;
    
    
    public Gags(String page) throws IOException
    {
        this.page = page;
        gags = new ArrayList<Gag>();
        index = 0;
        
        parseGags();
    }
    
    private void parseGags() throws IOException
    {
        Elements elem = HtmlUtil.get(page, "div.content");
        Iterator<Element> i = elem.iterator();
        
        while(i.hasNext())
        {
            Element e = i.next();
            
            try
            {
                int id = getGagID(e);
                String url = getGagURL(e);
                String title = getGagTitle(e);
                
                Gag gag = new Gag(id, url, title); 
                gags.add(gag);
                Log.i("Added", gag.getLocalUrl());
            }
            catch(Exception ex) 
            {
                Log.e("Error", ex.getMessage());
            }
        }
    }
    
    public Uri getCurrentGagUri() throws MalformedURLException, IOException
    {
        gags.get(index).requestImage();
        return Uri.fromFile(new File(gags.get(index).getLocalUrl()));
    }
    
    private int getGagID(Element e)
    {
        String id = HtmlUtil.getAttributeValue(e, "a", "href").substring(5);
        return Integer.parseInt(id);
    }
    
    private String getGagURL(Element e)
    {
        return HtmlUtil.getAttributeValue(e, "img", "src");
    }
    
    private String getGagTitle(Element e)
    {
        return HtmlUtil.getAttributeValue(e, "img", "alt");
    }

}
