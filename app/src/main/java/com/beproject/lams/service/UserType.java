package com.beproject.lams.service;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.beproject.lams.Constants;
import com.beproject.lams.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pradnesh Kore on 03-04-2016.
 */
public class UserType extends AsyncTask<Void, Void, Integer> {
    private static final String APP_INTERFACE_TYPE_URL = "http://192.168.43.135/lams/appinterface/app_usertype.php";
    Context mContext;
    public UserType(Context c){
        mContext = c;
    }
    @Override
    protected Integer doInBackground(Void... params) {
        try {
            Uri uri = Uri.parse(APP_INTERFACE_TYPE_URL).buildUpon().appendQueryParameter("q", mContext.getString(R.string.apikey)).build();
            // Create the request to server, and open the connection
            HttpClient urlConnection = new DefaultHttpClient();
            HttpPost get =new HttpPost(uri.toString());
            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("userid", Constants.USERID));
            get.setEntity(new UrlEncodedFormEntity(postData));
            Log.e("GetUserDetail","Url connected");
            HttpResponse resp = urlConnection.execute(get);
            String data = EntityUtils.toString(resp.getEntity());
            Log.e("GetUserDetail",data);
            String init[]=data.split("@@");
            return Integer.parseInt(init[1]);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("GetUserDetail","Some other problem");
        return -1;
    }
}
