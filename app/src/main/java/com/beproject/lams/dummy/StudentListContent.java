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
 */
public class StudentListContent {

    public StudentListContent(Cursor c){
        if(c.moveToFirst()){
            do{
                StudentListItem newItem = new StudentListItem(c.getString(c.getColumnIndex(LamsDataContract.Student.COLUMN_ENROLL_ID)),c.getString(c.getColumnIndex(LamsDataContract.Student.COLUMN_NAME)));
                addItem(newItem);
            }while (c.moveToNext());
        }
    }

    /**
     * An array of sample (dummy) items.
     */
    public List<StudentListItem> ITEMS = new ArrayList<StudentListItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, StudentListItem> ITEM_MAP = new HashMap<String, StudentListItem>();

    private static final int COUNT = 25;


    private  void addItem(StudentListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static StudentListItem createDummyItem(int position) {
        return new StudentListItem(String.valueOf(position), "Item " + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class StudentListItem {
        public final String id;
        public final String content;

        public StudentListItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
