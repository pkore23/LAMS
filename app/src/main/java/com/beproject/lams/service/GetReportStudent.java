package com.beproject.lams.service;

import android.content.Context;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.jar.Attributes;

import android.os.AsyncTask;

import com.beproject.lams.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Created by Pradnesh Kore on 07-04-2016.
 */
public class GetReportStudent extends AsyncTask<String, Void, String> {
    Context mContext;
    public GetReportStudent(Context c){
        mContext = c;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URI uri = new URI("http://192.168.43.135/lams/appinterface/app_report_stud.php?q="+mContext.getString(R.string.apikey));
            HttpClient client = new DefaultHttpClient();
            HttpPost post= new HttpPost(uri);
            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("enroll",params[0]));
            HttpResponse resp = client.execute(post);
            return EntityUtils.toString(resp.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
