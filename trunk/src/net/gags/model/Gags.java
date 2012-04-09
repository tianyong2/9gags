package net.gags.model;

import java.util.ArrayList;

public class Gags
{
    
    public static final String HOT_PAGE = "http://9gag.com";
    public static final String TRENDING_PAGE = "http://9gag.com/trending";
    public static final String VOTE_PAGE = "http://9gag.com/vote";
    
    private String page;
    private ArrayList<Gag> gags;
    
    
    public Gags(String page)
    {
        this.page = page;
        gags = new ArrayList<Gag>();
    }

}
