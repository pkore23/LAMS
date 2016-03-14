package com.beproject.lams.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.sql.SQLDataException;

public class LamsContentProvider extends ContentProvider {

    LamsDBHelper mDBHelper;
    public LamsContentProvider() {
    }

    static final int EVENT = 100;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = LamsDataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,LamsDataContract.PATH_EVENT, EVENT);

        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowsDeleted;
        if(selection == null) selection ="1";
        switch (match){
            case EVENT: rowsDeleted = db.delete(LamsDataContract.Event.TABLE_NAME,selection, selectionArgs);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
        if(rowsDeleted>0)
            getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case EVENT: return LamsDataContract.Event.CONTENT_TYPE;
            default:throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db =mDBHelper.getWritableDatabase();
        Uri returnUri =null;
        long _id;
        switch(match){
            case EVENT: _id=db.insert(LamsDataContract.Event.TABLE_NAME,null,values);
                if(_id>0)
                    returnUri = LamsDataContract.Event.buildEvent(_id);
                else
                        throw new SQLException("Failed to insert row into "+uri);
                break;
            default: throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new LamsDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor retCursor;
        switch(match){
            case EVENT: retCursor = db.query(LamsDataContract.Event.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default: throw new UnsupportedOperationException("Unknoown URI: "+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match){
            case EVENT: rowsUpdated = db.update(LamsDataContract.Event.TABLE_NAME, values, selection, selectionArgs);
                break;
            default: throw new UnsupportedOperationException("Unknown URI: "+uri);
        }
        if(rowsUpdated>0)
            getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}
