package com.cse390.coronavirus.DatabaseHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlannerContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PlannerItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PlannerItem> ITEM_MAP = new HashMap<>();

    public static void addItem(PlannerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int index) {
        PlannerItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    public static PlannerItem getItem(int index){
        PlannerItem item = ITEMS.get(index);
        return  item;
    }


    public static void removeTopItem(){
        ITEMS.remove(ITEMS.size()-1);
    }

    private static PlannerItem createPlannerItem(int position) {

        return new PlannerItem("Emtpy Category", "CSE 101" , "No Description", false, new Date(), String.valueOf(position));
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
    public static class PlannerItem {
        private String category;
        private String subject;
        private String description;
        private boolean completed;
        private Date dueDate;
        private String id;

        public PlannerItem(){}


        public PlannerItem(String category, String subject, String description, boolean completed, Date dueDate, String id){
            this.category = category;
            this.description = description;
            this.subject = subject;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}