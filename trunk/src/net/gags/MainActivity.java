package net.gags;

import net.gags.model.Gags;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, Gags.ChangeListener {

    private Gags gags;

    private TextView titleText;
    private ImageView gagView;
    private Button previousButton;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	titleText = (TextView) findViewById(R.id.titleText);
	gagView = (ImageView) findViewById(R.id.gagView);
	previousButton = (Button) findViewById(R.id.previousButton);
	nextButton = (Button) findViewById(R.id.nextButton);

	previousButton.setOnClickListener(this);
	nextButton.setOnClickListener(this);

        gags = new Gags(Gags.HOT_PAGE);
        gags.addGagHandler(this);
        gags.refresh();
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
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void setGag(String title, Bitmap image) {
        titleText.setText(title);
        gagView.setImageBitmap(image);
    }

}