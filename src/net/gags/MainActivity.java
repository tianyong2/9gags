package net.gags;

import java.io.IOException;

import net.gags.controller.GagsController;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, GagsController.ChangeListener {

    private GagsController gags;

    private ScrollView scrollView;
    private TextView titleText;
    private ImageView gagView;
    private TextView pageText;
    private Button previousButton;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        titleText = (TextView) findViewById(R.id.titleText);
        gagView = (ImageView) findViewById(R.id.gagView);
        pageText = (TextView) findViewById(R.id.pageText);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        gags = new GagsController(GagsController.HOT_PAGE);
        gags.addGagHandler(this);

        try {
            gags.next();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
            case R.id.previousButton:
                gags.previous();
                break;

            case R.id.nextButton:
                gags.next();
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onGagChange(int pageIndex, String title, Bitmap image) {
        scrollView.pageScroll(ScrollView.FOCUS_UP);
        titleText.setText(title);
        gagView.setImageBitmap(image);
        pageText.setText(getResources().getString(R.string.page)+" "+pageIndex);
    }

}