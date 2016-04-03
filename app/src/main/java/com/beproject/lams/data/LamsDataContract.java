package com.beproject.lams.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pradnesh Kore on 05-03-2016.
 */
public class LamsDataContract {
    //Content URI for application
    public static final String CONTENT_AUTHORITY = "com.beproject.lams";
    //Base content uri
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    //Possible paths
    public static final String PATH_STUDENT = "student";
    public static final String PATH_SUBJECT = "subject";
    public static final String PATH_LECTURE = "lecture";
    public static final String PATH_STAFF = "staff";
    public static final String PATH_EVENT = "event";


    //Class to define contents of student table
    public static final class Student implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENT).build(); //table uri
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENT; //Directory type
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENT; //tem type
        public static final String TABLE_NAME = "student"; //table name

        //Columns
        public static final String COLUMN_ENROLL_ID = "enroll_id";
        public static final String COLUMN_NAME = "s_name";
        public static final String COLUMN_CLASS = "s_class";
        public static final String COLUMN_DEPT = "s_dept";
        public static final String COLUMN_ROLL = "s_roll_no";
        public static final String COLUMN_CONTACT = "s_contact";
        public static final String COLUMN_PCONTACT = "p_contact";
        public static final String COLUMN_MENTOR = "s_mentor";
        public static final String COLUMN_UNAME = "username";

        //Uri builder functions
        public static Uri buildStudentRoll(int roll){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ROLL, Integer.toString(roll)).build();
        }
        public static Uri buildStudentEnroll(int enroll){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(enroll)).build();
        }
        public static Uri buildStudentContact(long contact){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_CONTACT, Long.toString(contact)).build();
        }
        public static Uri buildStudentName(String name){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_NAME, name).build();
        }
        public static Uri buildStudentClass(String sclass){
            return CONTENT_URI.buildUpon().appendPath(sclass).build();
        }
        public static Uri buildMentor (String stId){
            return CONTENT_URI.buildUpon().appendPath(stId).build();
        }
    }

    //Class defining content of subject table
    public static final class Subject implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBJECT).build(); //table uri
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECT; //Directory type
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECT; //tem type
        public static final String TABLE_NAME = "subject"; //table name

        //columns
        public static final String COLUMN_SUB_ID = "sb_id";
        public static final String COLUMN_NAME = "sb_name";
        public static final String COLUMN_INCHARGE = "incharge";
        public static final String COLUMN_DEPT = "dept";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_THEORY = "theory";
        public static final String COLUMN_PRACTICAL = "practical";

        //uri builder functions
        public static Uri buildId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildName(String name) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_NAME, name).build();
        }

        public static Uri buildIncharge(String incharge) {
            return CONTENT_URI.buildUpon().appendPath(incharge).build();
        }

        public static Uri buildTheorywithYear(String year) {
            return CONTENT_URI.buildUpon().appendPath(year)
                    .appendQueryParameter(COLUMN_THEORY, "true").build();
        }

        public static Uri buildPracwithYear(String year) {
            return CONTENT_URI.buildUpon().appendPath(year)
                    .appendQueryParameter(COLUMN_PRACTICAL, "true").build();
        }

    }
        //Contents of staff table
        public static final class Staff implements BaseColumns {

            //Connection data
            public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STAFF).build();
            public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECT; //Directory type
            public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECT; //tem type
            public static final String TABLE_NAME = "staff"; //staff table name

            //Columns
            public static final String COLUMN_STAFF_ID = "staff_id";
            public static final String COLUMN_NAME = "staff_name";
            public static final String COLUMN_DEPT = "st_dept";
            public static final String COLUMN_EMAIL = "st_mail";
            public static final String COLUMN_POST = "st_post";
            public static final String COLUMN_UNAME = "username";
            public static final String COLUMN_ISADMIN = "is_admin";

            //Uri buliders for staff
            public static Uri buildId(String id){
                return CONTENT_URI.buildUpon().appendPath(id).build();
            }
            public static Uri buildName(String name){
                return CONTENT_URI.buildUpon().appendPath(name).build();
            }
            public static Uri builDept(String dept){
                return CONTENT_URI.buildUpon().appendPath(dept).build();
            }
            public static Uri buildPost(String post){
                return CONTENT_URI.buildUpon().appendPath(post).build();
            }

        }

    public static final class Event implements BaseColumns{

        public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT; //Directory type
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT; //tem type
        public static final String TABLE_NAME = "event"; //event table name

        //columns
        public static final String COLUMN_EVENT_HEADER = "event_header";

        public static Uri buildEvent(String evh){
            return CONTENT_URI.buildUpon().appendPath(evh).build();
        }

        public static Uri buildEvent(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
    
    public static final class Lecture implements BaseColumns{
        
        public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LECTURE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LECTURE; //Directory
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LECTURE; //Item type
        public static final String TABLE_NAME = "lecture";
        
        //Columns
        public static final String COLUMN_LEC_ID = "lec_id";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_STAFF = "staff";
        public static final String COLUMN_ATTD = "attd_table";
        
        public static Uri buildLecture(String lecid){
            return CONTENT_URI.buildUpon().appendPath(lecid).build();
        }
        
        public static Uri buildStaff(String staffid){
            return CONTENT_URI.buildUpon().appendPath(staffid).build();
        }
        
        public static Uri buildSubject(String subid){
            return CONTENT_URI.buildUpon().appendPath(subid).build();
        }
        
        public static Uri buildDate(String dt){
            return CONTENT_URI.buildUpon().appendPath(dt).build();
        }
    }


}
