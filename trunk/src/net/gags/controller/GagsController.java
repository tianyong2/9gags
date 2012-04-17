package net.gags.controller;

import java.util.ArrayList;
import java.util.List;

import net.gags.model.GagsIterator;
import android.graphics.Bitmap;

public class GagsController
{

    public static final String HOT_PAGE = GagsIterator.HOT_PAGE;
    public static final String TRENDING_PAGE = GagsIterator.TRENDING_PAGE;
    public static final String VOTE_PAGE = GagsIterator.VOTE_PAGE;
    
    private GagsIterator iterator;
    
    private List<ChangeListener> listeners;
    
    
    public static interface ChangeListener
    {
        void onPreLoading();
        
        void onGagInfo(int pageIndex, int gagIndex, int numberOfGags, String title);

        void onGagImage(Bitmap image);
        
        void onException(Exception ex);
    }

    public GagsController(String page)
    {
        listeners = new ArrayList<ChangeListener>();
        iterator = new GagsIterator(page);
    }

    public void previous()
    {
        getGagLoader(GagLoader.PREVIOUS).execute(iterator);
    }

    public void next()
    {
        getGagLoader(GagLoader.NEXT).execute(iterator);
    }

    private GagLoader getGagLoader(int operation)
    {
        return new GagLoader(listeners, new GagInfo(), operation);
    }

    public void addListener(ChangeListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(ChangeListener listener)
    {
        listeners.remove(listener);
    }
    
}
