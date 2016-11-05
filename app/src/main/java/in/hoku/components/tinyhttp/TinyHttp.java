package in.hoku.components.tinyhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Name:    TinyHttp
 * URL:     https://github.com/hoku/TinyHttpForAndroid
 * License: MIT
 * <p/>
 * Created by hoku.
 */
public class TinyHttp {

    public interface OnTinyHttpLoadedTextListener {
        void done(String result);
    }

    public interface OnTinyHttpLoadedImageListener {
        void done(Bitmap result);
    }

    private String responseEncoding = null;


    public void setResponseContentEncoding(String responseContentEncoding) {
        responseEncoding = responseContentEncoding;
    }

    public String getText(final String url) {
        return requestString(url);
    }

    public void getTextAsync(final String url, final OnTinyHttpLoadedTextListener l) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return getText(url);
            }

            @Override
            protected void onPostExecute(String result) {
                if (l != null) {
                    l.done(result);
                }
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public Bitmap getImage(final String url) {
        return requestBitmap(url);
    }

    public void getImageAsync(final String url, final OnTinyHttpLoadedImageListener l) {
        AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return getImage(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (l != null) {
                    l.done(result);
                }
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private String requestString(String url) {
        HttpURLConnection connection = null;

        try {
            final URL targetUrl = new URL(url);

            connection = (HttpURLConnection) targetUrl.openConnection();
            connection.connect();

            final StringBuilder result = new StringBuilder();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                String encoding = connection.getContentEncoding();

                InputStreamReader isr;
                if (encoding != null) {
                    isr = new InputStreamReader(in, encoding);
                } else if (responseEncoding != null) {
                    isr = new InputStreamReader(in, responseEncoding);
                } else {
                    isr = new InputStreamReader(in);
                }
                BufferedReader bfr = new BufferedReader(isr);

                String line;
                while ((line = bfr.readLine()) != null) {
                    result.append(line);
                }

                closeStream(bfr);
                closeStream(isr);
                closeStream(in);
            }
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) connection.disconnect();
        }

        return null;
    }


    private Bitmap requestBitmap(String url) {
        HttpURLConnection connection = null;

        try {
            final URL targetUrl = new URL(url);

            connection = (HttpURLConnection) targetUrl.openConnection();
            connection.connect();

            final StringBuilder result = new StringBuilder();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(in);
                closeStream(in);

                return bitmap;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) connection.disconnect();
        }

        return null;
    }


    private static void closeStream(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
