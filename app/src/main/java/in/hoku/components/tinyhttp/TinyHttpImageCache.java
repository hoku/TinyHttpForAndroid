package in.hoku.components.tinyhttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Name:    TinyHttpImageCache
 * URL:     https://github.com/hoku/TinyHttpForAndroid
 * License: MIT
 * <p/>
 * Created by hoku.
 */
class TinyHttpImageCache {
    private Context context = null;

    public TinyHttpImageCache(Context context) {
        this.context = context.getApplicationContext();

        if (!context.getCacheDir().exists()) {
            context.getCacheDir().mkdirs();
        }
    }

    public void cacheImage(String url, Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            File saveFile = new File(context.getCacheDir().getAbsolutePath(), createUniqueString(url));
            outputStream = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            // nigiritsubusu
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception e) {
                // nigiritsubusu
            }
        }
    }

    public Bitmap loadCache(String url) {
        InputStream inputStream = null;
        try {
            File cacheFile = new File(context.getCacheDir().getAbsolutePath(), createUniqueString(url));
            inputStream = new FileInputStream(cacheFile);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;

        } catch (Exception e) {
            return null;

        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                // nigiritsubusu
            }
        }
    }

    public void deleteCacheAll() {
        File[] files = context.getCacheDir().listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    private String createUniqueString(String text) {
        return md5Hash("a" + text) + md5Hash(text + "b");
    }

    // md5
    private String md5Hash(String text) {
        try {
            MessageDigest messageDigest = java.security.MessageDigest.getInstance("MD5");
            messageDigest.update(text.getBytes());
            byte digest[] = messageDigest.digest();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                builder.append(String.format("%02x", digest[i] & 0xff));
            }

            return builder.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
