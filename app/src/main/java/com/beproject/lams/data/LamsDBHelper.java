package com.beproject.lams.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beproject.lams.data.LamsDataContract.Student;
import com.beproject.lams.data.LamsDataContract.Staff;
import com.beproject.lams.sync.LamsSyncService;

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
            Student.COLUMN_ENROLL_ID + " TEXT NOT NULL, " +
            Student.COLUMN_NAME + " TEXT NOT NULL, " +
            Student.COLUMN_DEPT + " TEXT NOT NULL, " +
            Student.COLUMN_CLASS + " TEXT NOT NULL, " +
            Student.COLUMN_ROLL + " INTEGER NOT NULL, " +
            Student.COLUMN_CONTACT + " BIGINT UNIQUE NOT NULL, " +
            Student.COLUMN_PCONTACT + " BIGINT UNIQUE NOT NULL, " +
            Student.COLUMN_MENTOR + " TEXT NOT NULL, " +
            Student.COLUMN_UNAME + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + Student.COLUMN_MENTOR + ") REFERENCES " + Staff.TABLE_NAME +
            "(" + Staff.COLUMN_STAFF_ID + ")" +
            ");";
        final String STAFF_CREATE = "CREATE TABLE "+ Staff.TABLE_NAME + " (" +
                Staff._ID + " INTEGER PRIMARY KEY, " +
                Staff.COLUMN_STAFF_ID + " TEXT UNIQUE NOT NULL, "+
                Staff.COLUMN_NAME + " TEXT NOT NULL,  " +
                Staff.COLUMN_DEPT + " TEXT NOT NULL, " +
                Staff.COLUMN_EMAIL + " TEXT NOT NULL, " +
                Staff.COLUMN_ISADMIN + " INT NOT NULL, " +
                Staff.COLUMN_UNAME + " TEXT NOT NULL, " +
                Staff.COLUMN_POST + " TEXT NOT NULL);";
        final String SUBJECT_CREATE = "CREATE TABLE " + LamsDataContract.Subject.TABLE_NAME +"("+
                LamsDataContract.Subject._ID + " INTEGER PRIMARY KEY, " +
                LamsDataContract.Subject.COLUMN_SUB_ID + " TEXT UNIQUE NOT NULL, " +
                LamsDataContract.Subject.COLUMN_NAME + " TEXT NOT NULL, " +
                LamsDataContract.Subject.COLUMN_DEPT + " TEXT NOT NULL, " +
                LamsDataContract.Subject.COLUMN_YEAR + " INTEGER NOT NULL, " +
                LamsDataContract.Subject.COLUMN_INCHARGE + " TEXT NOT NULL, "+
                LamsDataContract.Subject.COLUMN_OTHER_STAFF1 + " Text, "+
                LamsDataContract.Subject.COLUMN_OTHER_STAFF2 + " Text, "+
                LamsDataContract.Subject.COLUMN_OTHER_STAFF3 + " Text, "+
                LamsDataContract.Subject.COLUMN_OTHER_STAFF4 + " Text "+");";
        final String EVENT_CREATE = "CREATE TABLE " + LamsDataContract.Event.TABLE_NAME + "("+
                LamsDataContract.Event._ID + " INTEGER PRIMARY KEY, " +
                LamsDataContract.Event.COLUMN_EVENT_ID + " TEXT, "+
                LamsDataContract.Event.COLUMN_EVENT_STAFF_GEN + " TEXT, "+
                LamsDataContract.Event.COLUMN_EVENT_TYPE + " TEXT, "+
                LamsDataContract.Event.COLUMN_EVENT_TOPIC + " TEXT, "+
                LamsDataContract.Event.COLUMN_EVENT_DATE + " TEXT);";
        final String LECTURE_CREATE = "CREATE TABLE "+ LamsDataContract.Lecture.TABLE_NAME + "("+
                LamsDataContract.Lecture._ID + " INTEGER PRIMARY KEY, "+
                LamsDataContract.Lecture.COLUMN_LEC_ID + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_SUBJECT + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_DAY + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_TIME + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_TYPE + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_DEPT + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_class + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_STAFF + " TEXT NOT NULL, "+
                LamsDataContract.Lecture.COLUMN_LOCATION + " TEXT NOT NULL "+
                ")";
        db.execSQL(STUDENT_TABLE_CREATE);
        db.execSQL(STAFF_CREATE);
        db.execSQL(SUBJECT_CREATE);
        db.execSQL(EVENT_CREATE);
        db.execSQL(LECTURE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
