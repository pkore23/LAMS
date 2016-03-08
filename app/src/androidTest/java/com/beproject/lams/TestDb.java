package com.beproject.lams;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.beproject.lams.data.LamsDBHelper;
import com.beproject.lams.data.LamsDataContract;

import java.util.HashSet;

/**
 * Created by Pradnesh Kore on 08-03-2016.
 */
public class TestDb extends ApplicationTestCase<Application> {
    SQLiteDatabase db;
    public TestDb() {
        super(Application.class);
    }

    public void deleteTheDb(){
        mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDb();
    }
    public void testCreateDb(){

        mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
        db = new LamsDBHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();

    }

    public void testTables(){

        mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
        db = new LamsDBHelper(
                this.mContext).getWritableDatabase();

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(LamsDataContract.Student.TABLE_NAME);
        tableNameHashSet.add(LamsDataContract.Staff.TABLE_NAME);
        //tableNameHashSet.add(LamsDataContract.Subject.TABLE_NAME);

        //verify tables exists in db
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        //verify all tables exists
        do{
            tableNameHashSet.remove(c.getString(0));
        }while(c.moveToNext());

        assertTrue("All tables not created", tableNameHashSet.isEmpty());
        c.close();
        db.close();
    }
     public void testColumnsStudent(){
         mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
         db = new LamsDBHelper(
                 this.mContext).getWritableDatabase();

         final HashSet<String> studentColumns = new HashSet<String>();
         studentColumns.add(LamsDataContract.Student._ID);
         studentColumns.add(LamsDataContract.Student.COLUMN_ENROLL_ID);
         studentColumns.add(LamsDataContract.Student.COLUMN_NAME);
         studentColumns.add(LamsDataContract.Student.COLUMN_CLASS);
         studentColumns.add(LamsDataContract.Student.COLUMN_ROLL);
         studentColumns.add(LamsDataContract.Student.COLUMN_MENTOR);

         Cursor c = db.rawQuery("PRAGMA table_info(" + LamsDataContract.Student.TABLE_NAME + ")",
                 null);
         assertTrue("This means we were unable to query table information from database",c.moveToFirst());
         int columnIndex = c.getColumnIndex("name");
         do{
             studentColumns.remove(c.getString(columnIndex));
         }while(c.moveToNext());
         assertTrue("Error: this means we were unable to create all columns "+studentColumns.size(), studentColumns.isEmpty());
         c.close();
         db.close();
     }
     public void testColumnsStaff(){
         mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
         db = new LamsDBHelper(
                 this.mContext).getWritableDatabase();

         final HashSet<String> columns = new HashSet<String>();
         columns.add(LamsDataContract.Staff._ID);
         columns.add(LamsDataContract.Staff.COLUMN_STAFF_ID);
         columns.add(LamsDataContract.Staff.COLUMN_NAME);
         columns.add(LamsDataContract.Staff.COLUMN_DEPT);
         columns.add(LamsDataContract.Staff.COLUMN_EMAIL);
         columns.add(LamsDataContract.Staff.COLUMN_POST);

         Cursor c = db.rawQuery("PRAGMA table_info(" + LamsDataContract.Staff.TABLE_NAME + ")",
                 null);
         assertTrue("This means we were unable to query table information from database",c.moveToFirst());
         int columnIndex = c.getColumnIndex("name");
         do{
             columns.remove(c.getString(columnIndex));
         }while(c.moveToNext());
         assertTrue("Error: this means we were unable to create all columns "+columns.size(), columns.isEmpty());
         c.close();
         db.close();
     }

    public void testColumnsSubject(){
         mContext.deleteDatabase(LamsDBHelper.DATABASE_NAME);
         db = new LamsDBHelper(
                 this.mContext).getWritableDatabase();

         final HashSet<String> columns = new HashSet<String>();
         columns.add(LamsDataContract.Subject._ID);
         columns.add(LamsDataContract.Subject.COLUMN_SUB_ID);
         columns.add(LamsDataContract.Subject.COLUMN_NAME);
         columns.add(LamsDataContract.Subject.COLUMN_DEPT);
         columns.add(LamsDataContract.Subject.COLUMN_INCHARGE);
         columns.add(LamsDataContract.Subject.COLUMN_PRACTICAL);
         columns.add(LamsDataContract.Subject.COLUMN_THEORY);
         columns.add(LamsDataContract.Subject.COLUMN_YEAR);

         Cursor c = db.rawQuery("PRAGMA table_info(" + LamsDataContract.Subject.TABLE_NAME + ")",
                 null);
         assertTrue("This means we were unable to query table information from database",c.moveToFirst());
         int columnIndex = c.getColumnIndex("name");
         do{
             columns.remove(c.getString(columnIndex));
         }while(c.moveToNext());
         assertTrue("Error: this means we were unable to create all columns "+columns.size(), columns.isEmpty());
         c.close();
         db.close();
     }

}
