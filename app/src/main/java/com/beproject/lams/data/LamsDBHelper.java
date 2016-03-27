package com.beproject.lams.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beproject.lams.data.LamsDataContract.Student;
import com.beproject.lams.data.LamsDataContract.Staff;

import javax.security.auth.Subject;

/**
 * Created by Pradnesh Kore on 07-03-2016.
 */
public class LamsDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lams_db";
    public LamsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String STUDENT_TABLE_CREATE = "CREATE TABLE "+ Student.TABLE_NAME + " (" +
            Student._ID + " INTEGER PRIMARY KEY, " +
            Student.COLUMN_ENROLL_ID + " TEXT UNIQUE NOT NULL, " +
            Student.COLUMN_NAME + " TEXT NOT NULL, " +
            Student.COLUMN_CLASS + " TEXT NOT NULL, " +
            Student.COLUMN_ROLL + " INTEGER NOT NULL, " +
            Student.COLUMN_CONTACT + " BIGINT UNIQUE NOT NULL, " +
            Student.COLUMN_MENTOR + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + Student.COLUMN_MENTOR + ") REFERENCES " + Staff.TABLE_NAME +
            "(" + Staff.COLUMN_STAFF_ID + ")" +
            ");";
        final String STAFF_CREATE = "CREATE TABLE "+ Staff.TABLE_NAME + " (" +
                Staff._ID + " INTEGER PRIMARY KEY, " +
                Staff.COLUMN_STAFF_ID + " TEXT UNIQUE NOT NULL, "+
                Staff.COLUMN_NAME + " TEXT NOT NULL,  " +
                Staff.COLUMN_DEPT + " TEXT NOT NULL, " +
                Staff.COLUMN_EMAIL + " TEXT NOT NULL, " +
                Staff.COLUMN_POST + " TEXT NOT NULL);";
        final String SUBJECT_CREATE = "CREATE TABLE " + LamsDataContract.Subject.TABLE_NAME +"("+
                LamsDataContract.Subject._ID + " TEXT PRIMARY KEY, " +
                LamsDataContract.Subject.COLUMN_SUB_ID + " TEXT UNIQUE NOT NULL, " +
                LamsDataContract.Subject.COLUMN_NAME + " TEXT NOT NULL, " +
                LamsDataContract.Subject.COLUMN_DEPT + " TEXT NOT NULL, " +
                LamsDataContract.Subject.COLUMN_YEAR + " INTEGER NOT NULL, " +
                LamsDataContract.Subject.COLUMN_INCHARGE + " TEXT NOT NULL, "+
                LamsDataContract.Subject.COLUMN_PRACTICAL + " INTEGER NOT NULL, " +
                LamsDataContract.Subject.COLUMN_THEORY + " INTEGER NOT NULL);";
        final String EVENT_CREATE = "CREATE TABLE " + LamsDataContract.Event.TABLE_NAME + "("+
                LamsDataContract.Event._ID + " TEXT PRIMARY KEY, " +
                LamsDataContract.Event.COLUMN_EVENT_HEADER + " TEXT);";
        db.execSQL(STUDENT_TABLE_CREATE);
        db.execSQL(STAFF_CREATE);
        db.execSQL(SUBJECT_CREATE);
        db.execSQL(EVENT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
