package in.hoku.components.tinyhttp;

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
 * License: CC0
 * <p/>
 * Created by hoku.
 */
public class TinyHttp {

    public interface OnTinyHttpStateListener {
        public void done(String result);
    }

    private String responseEncoding = null;

    public void setResponseContentEncoding(String responseContentEncoding) {
        responseEncoding = responseContentEncoding;
    }

    public void getText(final String url, final OnTinyHttpStateListener l) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return request(url);
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

    private String request(String url) {
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
                connection.getContentEncoding();

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
            if (connection != null) {
                connection.disconnect();
            }

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
