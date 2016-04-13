package com.beproject.lams.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.beproject.lams.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Pradnesh Kore on 13-04-2016.
 */
public class SetEvent extends AsyncTask<String,Void,Void> {
    String baseUrl = "http://"+ Constants.ip+"/lams/";;

    @Override
    protected Void doInBackground(String... params) {
        if(params[0]=="Extra Lecture"){
            baseUrl += "extralec.php";
        }
        else{
            baseUrl +="seminar.php";
        }
        ArrayList<NameValuePair> al= new ArrayList<NameValuePair>();
        al.add(new BasicNameValuePair("submit","true"));
        al.add(new BasicNameValuePair("subject",params[1]));
        al.add(new BasicNameValuePair("name",params[2]));
        al.add(new BasicNameValuePair("date", params[3]));
        HttpClient hp = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(new URI(baseUrl));
            post.setEntity(new UrlEncodedFormEntity(al));
            hp.execute(post);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
