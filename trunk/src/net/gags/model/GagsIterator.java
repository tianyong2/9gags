package net.gags.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GagsIterator
{

    private List<String> pages;
    private int pageIndex;

    private List<Gag> gags;
    private int gagIndex;

    private static String MAIN = "http://9gag.com";
    public static String HOT_PAGE = "/hot";
    public static String TRENDING_PAGE = "/trending";
    public static String VOTE_PAGE = "/vote";

    public GagsIterator(String page)
    {
        pages = new ArrayList<String>();
        pages.add(page);
        pageIndex = -1;
    }

    public Gag next() throws IOException
    {
        if(gags == null || gagIndex == gags.size() - 1)
            nextPage();

        gagIndex++;
        Gag gag = gags.get(gagIndex);
        return gag;
    }

    public Gag previous() throws IOException
    {
        if(gagIndex == 0)
            previousPage();

        gagIndex--;
        Gag gag = gags.get(gagIndex);
        return gag;
    }

    public int getPageIndex()
    {
        return pageIndex + 1;
    }

    public int getGagIndex()
    {
        return gagIndex + 1;
    }

    public int getNumberOfGags()
    {
        return gags.size();
    }

    private void nextPage() throws IOException
    {
        pageIndex++;
        GagsParser parser = new GagsParser(MAIN + pages.get(pageIndex));

        gags = parser.getGags();
        gagIndex = -1;

        if(pageIndex == pages.size() - 1)
            pages.add(parser.getNextPage());
    }

    private void previousPage() throws IOException
    {
        pageIndex--;
        GagsParser parser = new GagsParser(MAIN + pages.get(pageIndex));

        gags = parser.getGags();
        gagIndex = gags.size();
    }

}
