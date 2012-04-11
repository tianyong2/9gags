package net.gags;

import net.gags.model.Gags;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity
{
    
    private Gags gags;
    private ImageView gagView;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gagView = (ImageView) findViewById(R.id.gag);
        
        try
        {
            gags = new Gags(Gags.HOT_PAGE);
            gagView.setImageURI(gags.getCurrentGagUri());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}