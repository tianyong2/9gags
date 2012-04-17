package net.gags;

import net.gags.controller.GagsController;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity implements OnClickListener, GagsController.ChangeListener
{

    private GagsController gags;
    
    private ProgressDialog dialog;

    private ScrollView scrollView;
    private ViewSwitcher viewSwitcher;

    private TextView titleText;
    private ImageView gagView;

    private TextView pageIndexText;
    private TextView gagIndexText;

    private Button previousButton;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dialog = new ProgressDialog(getApplicationContext());
        dialog.setMessage(getResources().getString(R.string.loading_page));
        
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        titleText = (TextView) findViewById(R.id.titleText);
        gagView = (ImageView) findViewById(R.id.gagView);

        pageIndexText = (TextView) findViewById(R.id.pageIndexText);
        gagIndexText = (TextView) findViewById(R.id.gagIndexText);

        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        gags = new GagsController(GagsController.HOT_PAGE);
        gags.addListener(this);
        gags.next();
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.previousButton:
                gags.previous();
                break;

            case R.id.nextButton:
                gags.next();
                break;
        }
    }

    @Override
    public void onPreLoading()
    {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        String message = getResources().getString(R.string.loading_page);
        dialog = ProgressDialog.show(this, null, message, false, true);
    }

    @Override
    public void onGagInfo(int pageIndex, int gagIndex, int numberOfGags, String title)
    {
        dialog.cancel();
        
        viewSwitcher.setDisplayedChild(0);
        
        String spage = getResources().getString(R.string.page);
        pageIndexText.setText(spage + " " + pageIndex);

        String sgag = getResources().getString(R.string.gag);
        gagIndexText.setText(sgag + " " + gagIndex + "/" + numberOfGags);

        if(gagIndex == 1 && pageIndex == 1)
            previousButton.setClickable(false);
        else
            previousButton.setClickable(true);

        nextButton.setClickable(true);
        
        titleText.setText(title);
    }

    @Override
    public void onGagImage(Bitmap image)
    {
        viewSwitcher.setDisplayedChild(1);
        gagView.setImageBitmap(image);
    }

    @Override
    public void onException(Exception ex)
    {
        
    }

}