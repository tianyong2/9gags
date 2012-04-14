package net.gags.controller;

import java.io.IOException;
import java.util.ArrayList;

import net.gags.model.Gag;
import net.gags.model.GagsIterator;
import android.graphics.Bitmap;

public class GagsController {

    public static final String HOT_PAGE = GagsIterator.HOT_PAGE;
    public static final String TRENDING_PAGE = GagsIterator.TRENDING_PAGE;
    public static final String VOTE_PAGE = GagsIterator.VOTE_PAGE;

    private GagsIterator iterator;
    
    private ArrayList<ChangeListener> changeListeners;

    public static interface ChangeListener {
        void onGagChange(int pageIndex, String title, Bitmap image);
    }

    public GagsController(String page) {
        changeListeners = new ArrayList<ChangeListener>();
        iterator = new GagsIterator(page);
    }

    public void previous() throws IOException {
        Gag gag = iterator.previous();
        notifyChangeListeners(gag);
    }

    public void next() throws IOException {
        Gag gag = iterator.next();
        notifyChangeListeners(gag);
    }

    private void notifyChangeListeners(Gag gag) throws IOException {
        String title = gag.getTitle();
        Bitmap bitmap = gag.getBitmap();
        
        for (ChangeListener listener : changeListeners)
            listener.onGagChange(iterator.getPageIndex(), title, bitmap);
    }

    public void addGagHandler(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeGagHandler(ChangeListener listener) {
        changeListeners.remove(listener);
    }

}
