package com.beproject.lams.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 *
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 10;

    static String studRep[] = {"1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%",
            "1:ABC:Subject 1=73%\nSubject 2=09%\nSubject 3=46%"};
    static {
        // Add some sample items.
        for(String studenta:studRep){
            String det[] = studenta.split(":");
            addItem(new DummyItem(det[0],det[1],det[2]));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
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
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
