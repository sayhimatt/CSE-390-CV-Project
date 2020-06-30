package com.cse390.coronavirus.DatabaseHelper;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FunContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<FunItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, FunItem> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createFunItem(i));
        }
    }

    public static void addItem(FunItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int index) {
        FunItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }


    public static void removeTopItem(){
        ITEMS.remove(ITEMS.size()-1);
    }

    private static FunItem createFunItem(int position) {

        return new FunItem("Emtpy Category", "No Description", false, new Date(), position);
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
    public static class FunItem {
        private String category;
        private String description;
        private boolean completed;
        private Date dueDate;
        private int id;




        public FunItem(String category, String description, boolean completed, Date dueDate, int id){
            this.category = category;
            this.description = description;
            this.completed = completed;
            this.dueDate = dueDate;
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
