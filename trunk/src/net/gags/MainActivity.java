package net.gags;

import net.gags.model.Gags;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener
{
    
    private Gags gags;
    
    private ImageView gagView;
    private Button previousButton;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gagView = (ImageView) findViewById(R.id.gag);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        
        try
        {
            gags = new Gags(Gags.HOT_PAGE);
            gags.refresh();
            gagView.setImageBitmap(gags.getCurrentGag());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            switch(view.getId())
            {
                case R.id.previousButton:
                    gags.previous();
                    gagView.setImageBitmap(gags.getCurrentGag());
                    break;
                    
                case R.id.nextButton:
                    gags.next();
                    gagView.setImageBitmap(gags.getCurrentGag());
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}