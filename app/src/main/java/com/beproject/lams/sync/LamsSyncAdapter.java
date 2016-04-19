package com.beproject.lams.sync;

/**
 * Created by Pradnesh Kore on 03-04-2016.
 */
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.beproject.lams.BuildConfig;
import com.beproject.lams.Constants;
import com.beproject.lams.UserActivity;
import com.beproject.lams.R;
import com.beproject.lams.data.LamsDataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.Vector;

public class
LamsSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = LamsSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int WEATHER_NOTIFICATION_ID = 3004;


    private static final String[] NOTIFY_STUDENT_PROJECTION = new String[] {
            LamsDataContract.Student.COLUMN_ENROLL_ID,
            LamsDataContract.Student.COLUMN_NAME,
            LamsDataContract.Student.COLUMN_DEPT,
            LamsDataContract.Student.COLUMN_CLASS,
            LamsDataContract.Student.COLUMN_ROLL,
            LamsDataContract.Student.COLUMN_CONTACT,
            LamsDataContract.Student.COLUMN_PCONTACT,
            LamsDataContract.Student.COLUMN_MENTOR,
            LamsDataContract.Student.COLUMN_UNAME,
    };


    public LamsSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String data;String[] init;

        // Will contain the raw JSON response as a string.


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String APP_INTERFACE_STUDENT_URL =
                    "http://"+ Constants.ip+"/lams/appinterface/get_students.php";
            final String APP_INTERFACE_STAFF_URL =
                    "http://"+Constants.ip+"/lams/appinterface/get_staff.php";

            Uri uri = Uri.parse(APP_INTERFACE_STUDENT_URL).buildUpon().appendQueryParameter("q",getContext().getString(R.string.apikey)).build();
            URL url = new URL(uri.toString());
            String rows[];
            ContentValues cv[];

            // Create the request to server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            data = buffer.toString();
            if(!data.contains("0 rows returned")) {
                init = data.split("@@");
                rows = init[0].split("!");
                cv = new ContentValues[rows.length];
                int i = 0;
                try {
                    for (String row : rows) {
                        if (row != "\t") {
                            String columns[] = row.split("#");
                            cv[i] = new ContentValues();
                            cv[i].put(LamsDataContract.Student.COLUMN_ENROLL_ID, columns[0]);
                            cv[i].put(LamsDataContract.Student.COLUMN_NAME, columns[1]);
                            cv[i].put(LamsDataContract.Student.COLUMN_DEPT, columns[2]);
                            cv[i].put(LamsDataContract.Student.COLUMN_CLASS, columns[3]);
                            cv[i].put(LamsDataContract.Student.COLUMN_ROLL, columns[4]);
                            cv[i].put(LamsDataContract.Student.COLUMN_CONTACT, columns[5]);
                            cv[i].put(LamsDataContract.Student.COLUMN_PCONTACT, columns[6]);
                            cv[i].put(LamsDataContract.Student.COLUMN_MENTOR, columns[7]);
                            cv[i].put(LamsDataContract.Student.COLUMN_UNAME, columns[8]);
                            i++;
                        }
                    }
                    try {
                        getContext().getContentResolver().bulkInsert(LamsDataContract.Student.CONTENT_URI, cv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch (ArrayIndexOutOfBoundsException ae){
                    ae.printStackTrace();
                }
            }
            uri = Uri.parse(APP_INTERFACE_STAFF_URL).buildUpon().appendQueryParameter("q",getContext().getString(R.string.apikey)).build();
            url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            data = buffer.toString();
            if(!data.contains("0 rows returned")) {
                init = data.split("@@");
                rows = init[0].split("!");
                cv = new ContentValues[rows.length];
                int i = 0;
                try {
                    for (String row : rows) {
                        if (row != "\t") {
                            String columns[] = row.split("#");
                            cv[i] = new ContentValues();
                            cv[i].put(LamsDataContract.Staff.COLUMN_STAFF_ID, columns[0]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_NAME, columns[1]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_DEPT, columns[2]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_EMAIL, columns[3]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_POST, columns[4]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_UNAME, columns[5]);
                            cv[i].put(LamsDataContract.Staff.COLUMN_ISADMIN, Integer.parseInt(columns[6]));
                            i++;
                        }
                    }
                    getContext().getContentResolver().bulkInsert(LamsDataContract.Staff.CONTENT_URI, cv);
                }catch (ArrayIndexOutOfBoundsException ae){
                    ae.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                getContext().getContentResolver().delete(LamsDataContract.Event.CONTENT_URI, "date<=?", new String[]{"date(now)"});
            }
            url = new URL("http://"+Constants.ip+"/lams/appinterface/get_events.php?q="+getContext().getString(R.string.apikey));

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            data = buffer.toString();
            if(!data.contains("0 rows returned")) {
                init = data.split("@@");
                rows = init[0].split("!");
                cv = new ContentValues[rows.length];
                int i = 0;
                try{
                    for (String row : rows) {
                    Log.e("RowDetail", row);
                    if (row != "\t") {
                        String columns[] = row.split("#");
                        cv[i] = new ContentValues();
                        cv[i].put(LamsDataContract.Event.COLUMN_EVENT_ID, columns[0]);
                        cv[i].put(LamsDataContract.Event.COLUMN_EVENT_TOPIC, columns[1]);
                        cv[i].put(LamsDataContract.Event.COLUMN_EVENT_TYPE, columns[2]);
                        cv[i].put(LamsDataContract.Event.COLUMN_EVENT_STAFF_GEN, columns[3]);
                        cv[i].put(LamsDataContract.Event.COLUMN_EVENT_DATE, columns[4]);
                        i++;
                    }
                }
                getContext().getContentResolver().bulkInsert(LamsDataContract.Event.CONTENT_URI, cv);
            }catch (ArrayIndexOutOfBoundsException ae){
                ae.printStackTrace();
            }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            url = new URL("http://"+Constants.ip+"/lams/appinterface/get_lecs.php?q="+getContext().getString(R.string.apikey));

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            data = buffer.toString();
            if(!data.contains("0 rows returned")) {
                init = data.split("@@");
                rows = init[0].split("!");
                cv = new ContentValues[rows.length];
                int i = 0;
                try {
                    for (String row : rows) {
                        if (row != "\t") {
                            String columns[] = row.split("#");
                            cv[i] = new ContentValues();
                            cv[i].put(LamsDataContract.Lecture.COLUMN_LEC_ID, columns[0]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_DEPT, columns[1]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_class, columns[2]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_SUBJECT, columns[3]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_TYPE, columns[4]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_TIME, columns[5]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_STAFF, columns[6]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_DAY, columns[7]);
                            cv[i].put(LamsDataContract.Lecture.COLUMN_LOCATION, columns[8]);
                            i++;
                        }
                    }
                    getContext().getContentResolver().bulkInsert(LamsDataContract.Lecture.CONTENT_URI, cv);
                }catch (ArrayIndexOutOfBoundsException ae){
                    ae.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            url = new URL("http://"+Constants.ip+"/lams/appinterface/get_subject.php?q="+getContext().getString(R.string.apikey));

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            data = buffer.toString();
            if(!data.contains("0 rows returned")) {
                init = data.split("@@");
                rows = init[0].split("!");
                cv = new ContentValues[rows.length];
                int i = 0;
                try {
                    for (String row : rows) {
                        if (row != "\t") {
                            String columns[] = row.split("#");
                            cv[i] = new ContentValues();
                            cv[i].put(LamsDataContract.Subject.COLUMN_SUB_ID, columns[0]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_NAME, columns[1]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_INCHARGE, columns[2]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_DEPT, columns[3]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_YEAR, columns[4]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_SEM, columns[5]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_OTHER_STAFF1, columns[6]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_OTHER_STAFF2, columns[7]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_OTHER_STAFF3, columns[8]);
                            cv[i].put(LamsDataContract.Subject.COLUMN_OTHER_STAFF4, columns[9]);
                            i++;
                        }
                    }
                    getContext().getContentResolver().bulkInsert(LamsDataContract.Lecture.CONTENT_URI, cv);
                }catch (ArrayIndexOutOfBoundsException ae){
                    ae.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */


    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        LamsSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}