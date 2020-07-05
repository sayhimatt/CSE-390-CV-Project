package com.cse390.coronavirus.DatabaseHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class PlannerContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<PlannerItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PlannerItem> ITEM_MAP = new HashMap<>();

    public static void setItems(List<PlannerItem> items){
        ITEMS = items;
    }

    public static void addItem(PlannerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int index) {
        PlannerItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    public static void editItem(int index, PlannerItem item){
        ITEMS.add(index,item);
        ITEM_MAP.put(item.id, item);
    }

    public static PlannerItem getItem(int index){
        PlannerItem item = ITEMS.get(index);
        return  item;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class PlannerItem {
        private String name;
        private String category;
        private String description;
        private boolean completed;
        private Date dueDate;
        private String id;


        public PlannerItem(){}


        public PlannerItem(String name,String category,String description, boolean completed, Date dueDate, String id){
            this.name = name;
            this.description = description;
            this.category = category;
            this.completed = completed;
            this.dueDate = dueDate;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public boolean equals(@Nullable Object obj) {


            final PlannerContent.PlannerItem other = (PlannerContent.PlannerItem) obj;
            if (this.name.equals(other.name) && this.category.equals(other.category) && this.description.equals(other.description)
                    && this.completed == other.completed && this.dueDate.equals(other.dueDate) && this.id.equals(other.id)
            ){
                return true;
            }else{
                return false;
            }

        }
    }
}