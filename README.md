# TinyHttpForAndroid
Very very easy connection library for android.

# Example 1 (Image)
    // image url
    String url = "https://raw.githubusercontent.com/hoku/GithubImages/master/TinyHttp/TinyHttpImage.png";
    // request!!
    TinyHttp tinyHttp = new TinyHttp();
    tinyHttp.getImageAsync(url, new TinyHttp.OnTinyHttpLoadedImageListener() {
        @Override
        public void done(Bitmap bitmap) {
            ImageView imageView = (ImageView) findViewById(R.id.mainImage);
            imageView.setImageBitmap(bitmap);
        }
    });

# Example 2 (Text)
    // text url
    String url = "https://raw.githubusercontent.com/hoku/GithubImages/master/TinyHttp/TinyHttpText.txt";
    // request!!
    TinyHttp tinyHttp = new TinyHttp();
    tinyHttp.setResponseContentEncoding("UTF-8");
    tinyHttp.getTextAsync(url, new TinyHttp.OnTinyHttpLoadedTextListener() {
        @Override
        public void done(String text) {
            TextView textView = (TextView) findViewById(R.id.mainText);
            textView.setText(text);
        }
    });

# License
MIT
