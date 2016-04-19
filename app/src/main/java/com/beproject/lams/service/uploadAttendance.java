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
import java.util.Collection;
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
        String[] at = new String[Constants.attd.size()];
        at=  Constants.attd.toArray(at);
        String response;
        final String ATTENDANCE_URL = "http://"+Constants.ip+"/lams/appinterface/write_attendance.php?q="+Constants.apikey;
        try {
            //request

            HttpClient client = new DefaultHttpClient();
            URI uriAt = new URI(ATTENDANCE_URL);
            HttpPost post = new HttpPost(uriAt);
            List<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("class",Constants.Class_t));
            String type = (Constants.lec_type.equals("Lecture"))?"LEC":"PR";
            postData.add(new BasicNameValuePair("type",type));
            postData.add(new BasicNameValuePair("subject",Constants.sub));
            for (String ar:at) {
                postData.add(new BasicNameValuePair("students[]",ar));
                Log.e("StudentsCheck",ar);
            }
            post.setEntity(new UrlEncodedFormEntity(postData));
            post.setHeader("Accept","text/html");
            Log.d("MyHttpClient","Post data ready sending request");
            //response
            HttpResponse resp = client.execute(post);
            response = EntityUtils.toString(resp.getEntity());
            Log.e("AttendanceUpload",response);
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
        Constants.attd.clear();
        return null;
    }
}
