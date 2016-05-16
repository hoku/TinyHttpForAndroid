# TinyHttpForAndroid
Very very easy connection library for android.

# Example
    TinyHttp tinyHttp = new TinyHttp();
    tinyHttp.getText(url, new TinyHttp.OnTinyHttpStateListener() {
        @Override
        public void done(String result) {
            TextView textView = (TextView) findViewById(R.id.mainText);
            textView.setText(result);
        }
    });

# License
CC0
