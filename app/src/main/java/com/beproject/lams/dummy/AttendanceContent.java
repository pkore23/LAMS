package com.beproject.lams.dummy;

import android.database.Cursor;

import com.beproject.lams.data.LamsDataContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AttendanceContent {

    /**
     * An array of sample (dummy) items.
     */
    public final List<AttendanceItem> ITEMS = new ArrayList<AttendanceItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public AttendanceContent(Cursor c){
        if(c!=null){
            COUNT = c.getCount();
            c.moveToFirst();
            do{
            AttendanceItem i = new AttendanceItem(c.getString(c.getColumnIndex(LamsDataContract.Student.COLUMN_ENROLL_ID)),c.getPosition()+": "+c.getString(c.getColumnIndex(LamsDataContract.Student.COLUMN_NAME)));
            addItem(i);
            }while(c.moveToNext());
        }
        else{
            COUNT = 1;
            AttendanceItem i = new AttendanceItem("-1","Unable to load student list. Plaeas refresh data");
            addItem(i);
        }
    }
    public final Map<String, AttendanceItem> ITEM_MAP = new HashMap<>();

    private int COUNT;



    private void addItem(AttendanceItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * A item representing a piece of content.
     */
    public static class AttendanceItem {
        public final String id;
        public final String content;

        public AttendanceItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
