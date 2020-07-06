package com.cse390.coronavirus.DatabaseHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementContent {

    public static List<AchievementContent.AchievementItem> ITEMS = new ArrayList<>();

    public static final Map<String, AchievementContent.AchievementItem> ITEM_MAP = new HashMap<>();

    public static void setItems(List<AchievementContent.AchievementItem> items){
        ITEMS = items;
    }

    public static void addItem(AchievementContent.AchievementItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int index) {
        AchievementContent.AchievementItem item = ITEMS.get(index);
        ITEMS.remove(index);
    }

    public static void editItem(int index, AchievementContent.AchievementItem item){
        ITEMS.add(index,item);
        ITEM_MAP.put(item.id, item);
    }

    public static AchievementContent.AchievementItem getItem(int index){
        AchievementContent.AchievementItem item = ITEMS.get(index);
        return  item;
    }

    public static class AchievementItem {
        private String name;
        private String id;

        public AchievementItem(){}


        public AchievementItem(String name, String id){
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
