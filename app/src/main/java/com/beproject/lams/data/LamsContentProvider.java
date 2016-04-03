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
    static final int STUDENT = 200;
    static final int STAFF = 300;
    static final int SUBJECT = 400;
    static final int LECTURE = 500;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = LamsDataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,LamsDataContract.PATH_EVENT, EVENT);
        matcher.addURI(authority,LamsDataContract.PATH_STUDENT, STUDENT);
        matcher.addURI(authority,LamsDataContract.PATH_STAFF, STAFF);
        matcher.addURI(authority,LamsDataContract.PATH_SUBJECT, SUBJECT);
        matcher.addURI(authority,LamsDataContract.PATH_LECTURE, LECTURE);

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
            case STUDENT: rowsDeleted = db.delete(LamsDataContract.Student.TABLE_NAME,selection, selectionArgs);
                break;
            case STAFF: rowsDeleted = db.delete(LamsDataContract.Staff.TABLE_NAME,selection, selectionArgs);
                break;
            case SUBJECT: rowsDeleted = db.delete(LamsDataContract.Subject.TABLE_NAME,selection, selectionArgs);
                break;
            case LECTURE: rowsDeleted = db.delete(LamsDataContract.Lecture.TABLE_NAME,selection, selectionArgs);
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
            case STUDENT: return LamsDataContract.Student.CONTENT_TYPE;
            case SUBJECT: return LamsDataContract.Subject.CONTENT_TYPE;
            case STAFF: return LamsDataContract.Staff.CONTENT_TYPE;
            case LECTURE: return LamsDataContract.Lecture.CONTENT_TYPE;
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
            case STUDENT: _id=db.insert(LamsDataContract.Student.TABLE_NAME,null,values);
                if(_id>0)
                    returnUri = LamsDataContract.Event.buildEvent(_id);
                else
                    throw new SQLException("Failed to insert row into "+uri);
                break;
            case STAFF: _id=db.insert(LamsDataContract.Staff.TABLE_NAME,null,values);
                if(_id>0)
                    returnUri = LamsDataContract.Event.buildEvent(_id);
                else
                    throw new SQLException("Failed to insert row into "+uri);
                break;
            case SUBJECT: _id=db.insert(LamsDataContract.Subject.TABLE_NAME,null,values);
                if(_id>0)
                    returnUri = LamsDataContract.Event.buildEvent(_id);
                else
                    throw new SQLException("Failed to insert row into "+uri);
                break;
            case LECTURE: _id=db.insert(LamsDataContract.Event.TABLE_NAME,null,values);
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
    @SuppressWarnings("All")
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch(match){
            case EVENT: db.beginTransaction();
                try{
                    for (ContentValues cv: values) {
                        long _id = db.insert(LamsDataContract.Event.TABLE_NAME,null,cv);
                        if(_id!=-1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case STUDENT: db.beginTransaction();
                try{
                    for (ContentValues cv: values) {
                        long _id = db.insert(LamsDataContract.Student.TABLE_NAME,null,cv);
                        if(_id!=-1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case SUBJECT: db.beginTransaction();
                try{
                    for (ContentValues cv: values) {
                        long _id = db.insert(LamsDataContract.Subject.TABLE_NAME,null,cv);
                        if(_id!=-1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case STAFF: db.beginTransaction();
                try{
                    for (ContentValues cv: values) {
                        long _id = db.insert(LamsDataContract.Staff.TABLE_NAME,null,cv);
                        if(_id!=-1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case LECTURE: db.beginTransaction();
                try{
                    for (ContentValues cv: values) {
                        long _id = db.insert(LamsDataContract.Lecture.TABLE_NAME,null,cv);
                        if(_id!=-1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
