package com.example.key.com.weatherforcasting;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//以下代码参考资料：http://blog.sina.com.cn/s/blog_6b04c8eb01013v9n.html，有改动
public class Connect {
    public  byte[] readStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public  String GetHtml(String urlpath) throws Exception {
        URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(6 * 1000);
        conn.setReadTimeout(6 * 1000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            String html = new String(data);
            return html;
        }
        return String.valueOf(conn.getURL());
    }
}
