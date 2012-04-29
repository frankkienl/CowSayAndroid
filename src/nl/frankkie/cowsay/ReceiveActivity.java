package nl.frankkie.cowsay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 *
 * @author Frankkie
 */
public class ReceiveActivity extends Activity {

    TextView tvOutput;
    String text;
    String fullText;
    int fontSize = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Layout
        setContentView(R.layout.main);
        tvOutput = (TextView) findViewById(R.id.tv);
        tvOutput.setTypeface(Typeface.MONOSPACE);
        tvOutput.setTextSize(fontSize);
        //Remove button from layout
        View btn = findViewById(R.id.btn);
        btn.setVisibility(View.GONE);
        //Get text
        Object o = getLastNonConfigurationInstance();
        if (o == null) {
            Intent intent = getIntent();
            text = intent.getStringExtra("text"); //via Widget
            if (text == null){
                text = intent.getStringExtra("android.intent.extra.TEXT");
            }
        } else {
            text = o.toString();
        }
        //CowSay
        CowSayApi api = new CowSayApi();
        api.setBalloonText(text);
        api.setCowFile("default.cow");
        fullText = api.make();
        tvOutput.setText(fullText);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        //Keep text after portrait / landscape switch
        return text;
    }
}
