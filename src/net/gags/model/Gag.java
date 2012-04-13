package net.gags.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class Gag {

    private static String localDir;

    private int id;
    private String url;
    private String title;
    private String localUrl;

    static {
        localDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9Gags/gags/";
        new File(localDir).mkdirs();
    }

    public Gag(int id, String url, String title) throws IOException {
        this.id = id;
        this.url = url;
        this.title = title;
        this.localUrl = localDir + id + ".jpg";

        Log.i("LocalURL", localUrl);
    }

    private Bitmap requestGag() throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
        bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(localUrl));
        return bitmap;
    }

    public Bitmap getBitmap() throws IOException {
        if (new File(localUrl).exists())
            return BitmapFactory.decodeFile(localUrl);
        else
            return requestGag();
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public String getTitle() {
        return title;
    }

}
