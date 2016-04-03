package com.beproject.lams;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pradnesh Kore on 03-04-2016.
 */
public class UserType extends AsyncTask<Void, Void, Integer> {
    private static final String APP_INTERFACE_TYPE_URL = "http://192.168.1.103/app_usertype.php";
    Context mContext;
    public UserType(Context c){
        mContext = c;
    }
    @Override
    protected Integer doInBackground(Void... params) {
        try {
            Uri uri = Uri.parse(APP_INTERFACE_TYPE_URL).buildUpon().appendQueryParameter("q", mContext.getString(R.string.apikey)).build();
            URL url = null;
            url = new URL(uri.toString());
            // Create the request to server, and open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return -1;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return -1;
            }
            int data = Integer.parseInt(buffer.toString());
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
