package net.gags.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.gags.util.HtmlUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;

public class Gags {

    public static final String HOT_PAGE = "http://9gag.com";
    public static final String TRENDING_PAGE = "http://9gag.com/trending";
    public static final String VOTE_PAGE = "http://9gag.com/vote";

    private String page;
    private ArrayList<Gag> gags;
    private int index;

    private ArrayList<ChangeListener> changeListeners;

    public static interface ChangeListener {
        void setGag(String title, Bitmap image);
    }

    public Gags(String page) {
        this.page = page;
        gags = new ArrayList<Gag>();
        changeListeners = new ArrayList<ChangeListener>();
        index = 0;
    }

    public void refresh() {
        try {
            Elements elem = HtmlUtil.get(page, "div.content");
            Iterator<Element> i = elem.iterator();

            while (i.hasNext()) {
                Element e = i.next();

                try {
                    int id = getGagID(e);
                    String url = getGagURL(e);
                    String title = getGagTitle(e);

                    Gag gag = new Gag(id, url, title);
                    gags.add(gag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            notifyChangeListeners();
        } catch (Exception ex) {
            
        }
    }

    public void previous() {
        if (index > 0) {
            index--;
            try {
                notifyChangeListeners();
            } catch (Exception ex) {
                index++;
            }
        }
    }

    public void next() {
        if (index < gags.size() - 1) {
            index++;
            try {
                notifyChangeListeners();
            } catch (Exception ex) {
                index--;
            }
        }
    }

    private void notifyChangeListeners() throws IOException {
        Gag gag = gags.get(index);
        String title = gag.getTitle();
        Bitmap bitmap = gag.getBitmap();
        
        for (ChangeListener listener : changeListeners)
            listener.setGag(title, bitmap);
    }

    public void addGagHandler(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeGagHandler(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    private int getGagID(Element e) {
        String id = HtmlUtil.getAttributeValue(e, "a", "href").substring(5);
        return Integer.parseInt(id);
    }

    private String getGagURL(Element e) {
        return HtmlUtil.getAttributeValue(e, "img", "src");
    }

    private String getGagTitle(Element e) {
        return HtmlUtil.getAttributeValue(e, "img", "alt");
    }

}
