package com.beproject.lams.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.beproject.lams.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pradnesh Kore on 04-04-2016.
 */
public class uploadAttendance extends AsyncTask<Void, Void, Void> {
    Context mContext;
    public uploadAttendance(Context c){
        mContext =c;
    }
    @Override
    protected Void doInBackground(Void... params) {
        ArrayList<String> at = Constants.attd;
        String response;
        final String ATTENDANCE_URL = "http://"+Constants.ip+"/lams/appinterface/write_attendance.php?q="+Constants.apikey;
        try {
            //request
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(new URI(ATTENDANCE_URL));
            List<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("class",Constants.Class_t));
            String type = (Constants.lec_type=="Lecture")?"LEC":"PR";
            postData.add(new BasicNameValuePair("type",type));
            postData.add(new BasicNameValuePair("subject",Constants.sub));
            for (String ar:at) {
                postData.add(new BasicNameValuePair("students[]",ar));
            }
            post.setEntity(new UrlEncodedFormEntity(postData));
            post.setHeader("Accept","text/html");
            Log.d("MyHttpClient","Post data ready sending request");
            //response
            HttpResponse resp = client.execute(post);
            response = EntityUtils.toString(resp.getEntity());
            if(response.contains("0")){
                Toast.makeText(mContext, "Attendance updated successfully", Toast.LENGTH_SHORT).show();
            }
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
//        Toast.makeText(mContext, "Attendance updated successfully to server", Toast.LENGTH_SHORT).show();
        return null;
    }
}
