package com.beproject.lams;

import com.beproject.lams.dummy.StudentListContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pradnesh Kore on 02-04-2016.
 */
public final class Constants {
    public static final String KEYLECID = "SELLECID";
    public static final int LOADEREVENT = 0;
    public static final int LOADERATTENDANCE = 1;
    public static final int LOADERLECID = 2;
    public static List<StudentListContent.StudentListItem> ITEMSSTUDENT;
    public static String ENROLLID = "";
    public static String USERID = "";
    public static String PASSWD = "";
    public static final String PREFERENCE = "com.beproject.lams";
    public static int USERTYPE = -1;
    public static ArrayList<String> attd= new ArrayList<String>();
    public static String apikey="cnn/Nfq5DvznNA8QosIiPt5O3u8ytZXfD4qL1AOiCH4QZxjvtHKbJw";
    public static String lec_type;
    public static String Class_t;
    public static String dept;
    public static String sub;
    public static Map<String, StudentListContent.StudentListItem> ITMESSTUDENTMAP;
    public static final int LOADERCONTACT=3;
    public static String ip = "192.168.1.103";
    public static String students;
}
