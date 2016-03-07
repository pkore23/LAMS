package com.beproject.lams.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beproject.lams.data.LamsDataContract.Student;
import com.beproject.lams.data.LamsDataContract.Staff;

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
            Student._ID + "INTEGER PRIMARY KEY, " +
            Student.COLUMN_ENROLL_ID + "TEXT UNIQUE NOT NULL, " +
            Student.COLUMN_NAME + "TEXT NOT NULL, " +
            Student.COLUMN_CLASS + "TEXT NOT NULL, " +
            Student.COLUMN_CONTACT + "BIGINT UNIQUE NOT NULL, " +
            Student.COLUMN_MENTOR + "TEXT NOT NULL, " +
            "FOREIGN KEY (" + Student.COLUMN_MENTOR + ") REFERENCES " + Staff.TABLE_NAME +
            "(" + Staff.COLUMN_STAFF_ID + ")" +
            ");";
        final String STAFF_CREATE = "CREATE TABLE "+ Staff.TABLE_NAME + " (" +
                Staff._ID + "INTEGER PRIMARY KEY, " +
                Staff.COLUMN_STAFF_ID + "TEXT UNIQUE NOT NULL, "+
                Staff.COLUMN_NAME + "TEXT NOT NULL, " +
                Staff.COLUMN_DEPT + "TEXT NOT NULL" +
                Staff.COLUMN_EMAIL + "TEXT NOT NULL" +
                Staff.COLUMN_POST + "TEXT NOT NULL);";
        db.execSQL(STUDENT_TABLE_CREATE);
        db.execSQL(STAFF_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
