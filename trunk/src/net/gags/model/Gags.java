package net.gags.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import net.gags.util.HtmlUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

public class Gags
{
    
    public static final String HOT_PAGE = "http://9gag.com";
    public static final String TRENDING_PAGE = "http://9gag.com/trending";
    public static final String VOTE_PAGE = "http://9gag.com/vote";
    
    private String page;
    private ArrayList<Gag> gags;
    private int index;
    
    private ArrayList<Handler> gagHandlers;
    
    
    public Gags(String page) throws IOException
    {
        this.page = page;
        gags = new ArrayList<Gag>();
        gagHandlers = new ArrayList<Handler>();
        index = 0;
    }
    
    public void refresh() throws IOException
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
        
        notifyGagHandlers();
    }
    
    public Bitmap getCurrentGag() throws MalformedURLException, IOException
    {
        return gags.get(index).getBitmap();
    }
    
    public void previous()
    {
        if(index > 0) index--;
    }
    
    public void next()
    {
        if(index < gags.size()-1) index++;
    }
    
    private void notifyGagHandlers()
    {
//        for(Handler handler : gagHandlers)
//            handler.
    }
    
    public void addGagHandler(Handler handler)
    {
        gagHandlers.add(handler);
    }
    
    public void removeGagHandler(Handler handler)
    {
        gagHandlers.remove(handler);
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
