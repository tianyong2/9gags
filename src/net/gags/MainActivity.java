package net.gags;

import java.net.URL;
import java.util.Iterator;

import net.gags.util.HtmlUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageView gag = (ImageView) findViewById(R.id.gag);
        
        try
        {
            Elements elem = HtmlUtil.get("http://9gag.com/", "div.content");
            Iterator<Element> i = elem.iterator();
            
//            while(i.hasNext())
            if(i.hasNext())
            {
                Element e = i.next();
                Log.i("ID", getImageID(e));
                Log.i("URL", getImageURL(e));
//                gag.setImageURI(new Uri(getImageURL(e)));
            }
        }
        catch(Exception ex)
        {
            Log.e("IOException", ex.getMessage());
        }
        
//        gag.setImageURI(uri);
    }
    
    private String getImageID(Element e)
    {
        return HtmlUtil.getAttributeValue(e, "a", "href");
    }
    
    private String getImageURL(Element e)
    {
        return HtmlUtil.getAttributeValue(e, "img", "src");
    }
}