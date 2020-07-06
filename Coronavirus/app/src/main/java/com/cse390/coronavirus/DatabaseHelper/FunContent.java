package com.cse390.coronavirus.DatabaseHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for fun activities in the database
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FunContent {

    /**
     * An array of fun activities
     */
    public static final List<FunItem> ITEMS = new ArrayList<>();

    /**
     * A map of fun activities
     */
    public static final Map<String, FunItem> ITEM_MAP = new HashMap<>();

    /**
     * Adds a new fun activity to the list and map
     * @param item
     */
    public static void addItem(FunItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * Removes a fun activity from the list
     * @param index
     */
    public static void removeItem(int index) {
        FunItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    /**
     * Returns the fun activity at the given index
     * @param index the position of the desired fun activity
     * @return
     */
    public static FunContent.FunItem getItem(int index){
        FunContent.FunItem item = ITEMS.get(index);
        return item;
    }


    /**
     *  Creates details about a given fun activity
     *  NOT USED
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
     * A fun item representing the content in any fun activity
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
         * These variables make up a new fun activity
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
     * Compares the two fun activities category values
     */
    public static class FunItemCategoryComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.category.compareTo(o2.category);
        }
    }

    /**
     * Compares the two fun activities name values
     */
    public static class FunItemNameComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    /**
     * Compares the two fun activities description values
     */
    public static class FunItemDescriptionComparator implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return o1.description.compareTo(o2.description);
        }
    }

    /**
     * Compares the two fun activities completion values
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
     * Compares the inverse of two fun activities category values
     */
    public static class FunItemCategoryComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.category.compareTo(o2.category);
        }
    }

    /**
     * Compares the inverse of two fun activities name values
     */
    public static class FunItemNameComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.name.compareTo(o2.name);
        }
    }

    /**
     * Compares the inverse of two fun activities description values
     */
    public static class FunItemDescriptionComparatorReverse implements Comparator<FunContent.FunItem> {
        @Override
        public int compare(FunContent.FunItem o1, FunContent.FunItem o2) {
            return -o1.description.compareTo(o2.description);
        }
    }

    /**
     * Compares the inverse of two fun activities completion values
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
