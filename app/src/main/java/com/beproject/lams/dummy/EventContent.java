package com.beproject.lams.dummy;

import android.database.Cursor;
import android.util.Log;

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
public class EventContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<EventItem> ITEMS = new ArrayList<EventItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, EventItem> ITEM_MAP = new HashMap<String, EventItem>();

    private static final int COUNT = 25;

    public EventContent(Cursor c){
        String event;
        EventItem eventI;
        try {
            if (c.moveToFirst()) {
                do {
                    event = c.getString(c.getColumnIndex(LamsDataContract.Event.COLUMN_EVENT_HEADER));
                    eventI = new EventItem(String.valueOf(c.getPosition()), event);
                    ITEMS.add(eventI);
                    ITEM_MAP.put(eventI.id, eventI);

                } while (c.moveToNext());
            } else if (c != null) {
                eventI = new EventItem(String.valueOf(-1), "No event found in local db");
                ITEMS.add(eventI);
                ITEM_MAP.put(eventI.id, eventI);
            } else {
                eventI = new EventItem(" ", "No event found in local db");
                ITEMS.add(eventI);
                ITEM_MAP.put(eventI.id, eventI);
            }
        }
        catch (NullPointerException ne){
            Log.e("EventContent","Error: "+ne.getLocalizedMessage());
            eventI = new EventItem(" ", "No event found in local db");
            ITEMS.add(eventI);
            ITEM_MAP.put(eventI.id, eventI);
        }
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class EventItem {
        public final String id;
        public final String content;

        public EventItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
