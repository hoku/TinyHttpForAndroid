package in.hoku.components.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.hoku.components.tinyhttp.R;
import in.hoku.components.tinyhttp.TinyHttp;

/**
 * Created by hoku.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        //String url = "https://www.google.co.jp/";
        String url = "http://www.yahoo.co.jp/";

        TinyHttp tinyHttp = new TinyHttp();
        tinyHttp.setResponseContentEncoding("UTF-8");
        tinyHttp.getText(url, new TinyHttp.OnTinyHttpStateListener() {
            @Override
            public void done(String result) {
                result = result.substring(0, 3000);
                displayText(result + " .........");
            }
        });
    }

    private void displayText(String text) {
        TextView textView = (TextView) findViewById(R.id.mainText);
        textView.setText(text);
    }
}
