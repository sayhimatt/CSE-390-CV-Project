package com.guidi.coronavirus.DatabaseHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlannerItemDatabaseHelper {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PlannerItems");

    public PlannerItemDatabaseHelper(){

    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void addPlannerItem(PlannerItem item){
        mDatabase.push().setValue(item);
    }

}
