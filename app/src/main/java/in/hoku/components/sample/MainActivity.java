package in.hoku.components.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
        switch (v.getId()) {
            case R.id.getImageButton:
                requestImage();
                break;

            case R.id.getTextButton:
                requestText();
                break;
        }
    }

    private void requestImage() {
        String url = "https://raw.githubusercontent.com/hoku/GithubImages/master/TinyHttp/TinyHttpImage.png";

        TinyHttp tinyHttp = new TinyHttp();
        tinyHttp.deleteImageCacheAll(this);
        tinyHttp.getImageOrCacheAsync(this, url, new TinyHttp.OnTinyHttpLoadedImageListener() {
            @Override
            public void done(Bitmap bitmap) {
                displayImage(bitmap);
            }
        });
    }

    private void requestText() {
        String url = "https://raw.githubusercontent.com/hoku/GithubImages/master/TinyHttp/TinyHttpText.txt";

        TinyHttp tinyHttp = new TinyHttp();
        tinyHttp.setResponseContentEncoding("UTF-8");
        tinyHttp.getTextAsync(url, new TinyHttp.OnTinyHttpLoadedTextListener() {
            @Override
            public void done(String text) {
                displayText(text);
            }
        });
    }

    private void displayImage(Bitmap bmp) {
        ImageView imageView = (ImageView) findViewById(R.id.mainImage);
        imageView.setImageBitmap(bmp);
    }

    private void displayText(String text) {
        TextView textView = (TextView) findViewById(R.id.mainText);
        textView.setText(text);
    }
}
