package com.cse390.coronavirus.DatabaseHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * @param items
     */
    public static void setItems(List<PlannerItem> items){
        ITEMS = items;
    }

    /**
     * @param item
     */
    public static void addItem(PlannerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * @param index
     */
    public static void removeItem(int index) {
        PlannerItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    /**
     * @param index
     * @return
     */
    public static PlannerItem getItem(int index){
        PlannerItem item = ITEMS.get(index);
        return  item;
    }

    /**
     *
     */
    public static class PlannerItem {
        private String name;
        private String category;
        private String description;
        private boolean completed;
        private Date dueDate;
        private String id;

        /**
         *
         */
        public PlannerItem(){}


        /**
         * @param name
         * @param category
         * @param description
         * @param completed
         * @param dueDate
         * @param id
         */
        public PlannerItem(String name,String category,String description, boolean completed, Date dueDate, String id){
            this.name = name;
            this.description = description;
            this.category = category;
            this.completed = completed;
            this.dueDate = dueDate;
            this.id = id;
        }

        /**
         * @return
         */
        public String getName() {
            return name;
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
         * @param dueDate
         */
        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        /**
         * @return
         */
        public Date getDueDate() {
            return dueDate;
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
         * @param category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param obj
         * @return
         */
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

    /**
     *
     */
    public static class PlannerItemCategoryComparator implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return o1.category.compareTo(o2.category);
        }
    }

    /**
     *
     */
    public static class PlannerItemNameComparator implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    /**
     *
     */
    public static class PlannerItemDescriptionComparator implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return o1.description.compareTo(o2.description);
        }
    }

    /**
     *
     */
    public static class PlannerItemCompletedComparator implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            Boolean completed1 = o1.completed;
            Boolean completed2 = o2.completed;
            return completed1.compareTo(completed2);
        }
    }

    /**
     *
     */
    public static class PlannerItemDateComparator implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return -o1.dueDate.compareTo(o2.dueDate);
        }
    }

    /**
     *
     */
    public static class PlannerItemCategoryComparatorReverse implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return -o1.category.compareTo(o2.category);
        }
    }

    /**
     *
     */
    public static class PlannerItemNameComparatorReverse implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return -o1.name.compareTo(o2.name);
        }
    }

    /**
     *
     */
    public static class PlannerItemDescriptionComparatorReverse implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return -o1.description.compareTo(o2.description);
        }
    }

    /**
     *
     */
    public static class PlannerItemCompletedComparatorReverse implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            Boolean completed1 = o1.completed;
            Boolean completed2 = o2.completed;
            return -completed1.compareTo(completed2);
        }
    }

    /**
     *
     */
    public static class PlannerItemDateComparatorReverse implements Comparator<PlannerItem> {
        @Override
        public int compare(PlannerItem o1, PlannerItem o2) {
            return -o1.dueDate.compareTo(o2.dueDate);
        }
    }


}