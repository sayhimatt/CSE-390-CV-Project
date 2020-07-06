package com.cse390.coronavirus.DatabaseHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * @param item
     */
    public static void addItem(FunItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * @param index
     */
    public static void removeItem(int index) {
        FunItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    /**
     * @param index
     * @return
     */
    public static FunContent.FunItem getItem(int index){
        FunContent.FunItem item = ITEMS.get(index);
        return item;
    }


    /**
     * @param position
     * @return
     */
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


        /**
         *
         */
        public FunItem(){}

        /**
         * @param category
         * @param name
         * @param description
         * @param completed
         * @param id
         */
        public FunItem(String category, String name,String description, boolean completed, String id){
            this.category = category;
            this.name = name;
            this.description = description;
            this.completed = completed;

            this.id = id;
        }

        /**
         * @return
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return
         */
        public boolean isCompleted() {
            return completed;
        }

        /**
         * @param completed
         */
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        /**
         * @return
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * @param obj
         * @return
         */
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

    /**
     *
     */
    public static class FunItemCategoryComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.category.compareTo(o2.category);
        }
    }

    /**
     *
     */
    public static class FunItemNameComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    /**
     *
     */
    public static class FunItemDescriptionComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.description.compareTo(o2.description);
        }
    }

    /**
     *
     */
    public static class FunItemCompletedComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            Boolean completed1 = o1.completed;
            Boolean completed2 = o2.completed;
            return completed1.compareTo(completed2);
        }
    }

    /**
     *
     */
    public static class FunItemCategoryComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.category.compareTo(o2.category);
        }
    }

    /**
     *
     */
    public static class FunItemNameComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.name.compareTo(o2.name);
        }
    }

    /**
     *
     */
    public static class FunItemDescriptionComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.description.compareTo(o2.description);
        }
    }

    /**
     *
     */
    public static class FunItemCompletedComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            Boolean completed1 = o1.completed;
            Boolean completed2 = o2.completed;
            return -completed1.compareTo(completed2);
        }
    }


}
