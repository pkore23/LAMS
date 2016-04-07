package com.beproject.lams.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.beproject.lams.LoginActivityFragment;
import com.beproject.lams.R;

import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pradnesh Kore on 14-03-2016.
 */
public class MyHttpClient extends AsyncTask<String,Void,String> {

    Context mC;
    public MyHttpClient(Context C) {
        mC = C;
    }
    @SuppressWarnings("All")
    @Override
    protected String doInBackground(String... params) {
        String userid = params[0];
        String passwd = params[1];
        String response = "-1";
        try {
            //request
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(new URI("http://192.168.43.135/lams/appinterface/app_login.php?q="+mC.getString(R.string.apikey)));
            List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
            postData.add(new BasicNameValuePair("userid",userid));
            postData.add(new BasicNameValuePair("password",passwd));
            post.setEntity(new UrlEncodedFormEntity(postData));
            post.setHeader("Accept","text/html");
            Log.d("MyHttpClient","Post data ready sending request");
            //response
            HttpResponse resp = client.execute(post);
            response = EntityUtils.toString(resp.getEntity());
            Log.d("MyHttpClient", "Response complete: " + response);
        }
        catch(UnsupportedEncodingException use){
            Log.e("AsyncTaskLogin","Error connecting: "+use.getLocalizedMessage());
            use.printStackTrace();
            response = "-1";
        }
        catch (Exception e) {
            Log.e("AsyncTaskLogin","Error connecting: "+e.getLocalizedMessage());
            e.printStackTrace();
            response = "-1";
        }
        return response;
    }


}
