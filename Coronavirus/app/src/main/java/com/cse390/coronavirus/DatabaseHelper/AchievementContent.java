package com.cse390.coronavirus.DatabaseHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for achievements
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class AchievementContent {

    public static List<AchievementContent.AchievementItem> ITEMS = new ArrayList<>();

    public static final Map<String, AchievementContent.AchievementItem> ITEM_MAP = new HashMap<>();

    /**
     * Sets achievement list
     * @param items
     */
    public static void setItems(List<AchievementContent.AchievementItem> items){
        ITEMS = items;
    }

    /**
     * Adds an achievement to the list
     * @param item
     */
    public static void addItem(AchievementContent.AchievementItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     *
     */
    public static class AchievementItem {
        private String name;
        private String id;

        /**
         *
         */
        public AchievementItem(){}


        /**
         * Declares a new achievement item with a name and unique id
         * @param name
         * @param id used to identify achievements from one another
         */
        public AchievementItem(String name, String id){
            this.name = name;
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
         * @param obj
         * @return
         */
        @Override
        public boolean equals(@Nullable Object obj) {
            final AchievementContent.AchievementItem other = (AchievementContent.AchievementItem) obj;
            if (this.name.equals(other.name)
            ){
                return true;
            }else{
                return false;
            }
        }

    }



}
