package net.gags.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class Gag
{
    
    private static String localDir;

    private int gagid;
    private String gagUrl;
    private String title;
    private String imageUrl;
    private String localUrl;
    
    
    static
    {
        localDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9Gags/gags/";
        new File(localDir).mkdirs();
    }

    public Gag(int gagid, String gagUrl, String title, String imageUrl)
    {
        this.gagid = gagid;
        this.gagUrl = gagUrl;
        this.title = title;
        this.imageUrl = imageUrl;
        this.localUrl = localDir + gagid + ".jpg";
    }

    private Bitmap requestGag() throws IOException
    {
        Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
        bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(localUrl));
        return bitmap;
    }

    public Bitmap getBitmap() throws IOException
    {
        if(new File(localUrl).exists())
            return BitmapFactory.decodeFile(localUrl);
        else
            return requestGag();
    }

    public int getGagId()
    {
        return gagid;
    }

    public String getGagUrl()
    {
        return gagUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getLocalUrl()
    {
        return localUrl;
    }

}
