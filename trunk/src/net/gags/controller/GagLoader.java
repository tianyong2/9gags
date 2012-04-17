package net.gags.controller;

import java.util.List;

import net.gags.controller.GagsController.ChangeListener;
import net.gags.model.Gag;
import net.gags.model.GagsIterator;
import android.os.AsyncTask;
import android.util.Log;

public class GagLoader extends AsyncTask<GagsIterator, Integer, GagInfo>
{

    public static int PREVIOUS = 1;
    public static int NEXT = 2;
    
    private static int PRE_LOADING = 1;
    private static int GAG_INFO = 2;
    private static int GAG_IMAGE_INFO = 3;
    
    private List<ChangeListener> listeners;
    private GagInfo info;
    private int operation;
    
    
    public GagLoader(List<ChangeListener> listeners, GagInfo info, int operation)
    {
        this.listeners = listeners;
        this.info = info;
        this.operation = operation;
    }

    @Override
    protected GagInfo doInBackground(GagsIterator... params)
    {
        try
        {
            publishProgress(PRE_LOADING);
            
            GagsIterator iterator = params[0];
            
            Gag gag;
            if(operation == PREVIOUS)
                gag = iterator.previous();
            else
                gag = iterator.next();
            
            info.setPageIndex(iterator.getPageIndex());
            info.setGagIndex(iterator.getGagIndex());
            info.setNumberOfGags(iterator.getNumberOfGags());
            info.setTitle(gag.getTitle());
            
            publishProgress(GAG_INFO);
            
            info.setImage(gag.getBitmap());
            
            publishProgress(GAG_IMAGE_INFO);
            
            return info;
        }
        catch(Exception ex)
        {
            Log.e("Error", ex.getMessage());
            for(ChangeListener listener : listeners)
                listener.onException(ex);
            return null;
        }
    }
    
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        if(values[0].intValue() == PRE_LOADING)
        {
            for(ChangeListener listener : listeners)
                listener.onPreLoading();
        }
        else if(values[0].intValue() == GAG_INFO)
        {
            for(ChangeListener listener : listeners)
                listener.onGagInfo(info.getPageIndex(), info.getGagIndex(), info.getNumberOfGags(), info.getTitle());
        }
        else if(values[0].intValue() == GAG_IMAGE_INFO)
        {
            for(ChangeListener listener : listeners)
                listener.onGagImage(info.getImage());
        }
    }

}
