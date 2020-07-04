package com.cse390.coronavirus.DatabaseHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public static final Map<String, FunItem> ITEM_MAP = new HashMap<>();

    public static void addItem(FunItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int index) {
        FunItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    public static FunContent.FunItem getItem(int index){
        FunContent.FunItem item = ITEMS.get(index);
        return item;
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
        public String name;
        private String description;
        private boolean completed;
        private String id;


        public FunItem(){}

        public FunItem(String category, String description, String name, boolean completed, String id){
            this.category = category;
            this.name = name;
            this.description = description;
            this.completed = completed;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            final FunContent.FunItem other = (FunContent.FunItem) obj;
            if (this.category.equals(other.category) && this.name.equals(other.name) && this.description.equals(other.description)
                    && this.completed == other.completed && this.id.equals(other.id)
            ){
                return true;
            }else{
                return false;
            }
        }
    }
}
