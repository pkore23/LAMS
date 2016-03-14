package com.beproject.lams;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Pradnesh Kore on 14-03-2016.
 */
public class MyHttpClient extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        String userid = params[0];
        String passwd = params[1];
        String response = "-1";
        try {
            //request
            String data = URLEncoder.encode("userid=" + userid + "&password=" + passwd, "UTF-8");
            URL url = new URL("http://192.168.1.103/lams/appinterface/app_login.php");
            URLConnection conn = url.openConnection();
            conn.getDoOutput();
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }
            response = sb.toString();
        } catch (Exception e) {
            Log.e("AsyncTaskLogin","Error connecting: "+e.getLocalizedMessage());
            e.printStackTrace();
            response = "-1";
        }
        return response;
    }
}
