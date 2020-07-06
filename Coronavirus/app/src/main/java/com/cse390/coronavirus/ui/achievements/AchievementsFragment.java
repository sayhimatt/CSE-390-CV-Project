package com.cse390.coronavirus.ui.achievements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.AchievementContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Achievement Fragment displays what achievements user has made through their completion of plans using the app
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class AchievementsFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private static RecyclerView achievementList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AchievementsFragment() {
    }


    public static AchievementsFragment newInstance(int columnCount) {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Helps bring in the recycler view to be displayed inside the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement_list, container, false);
        initAuth();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            achievementList = (RecyclerView) view;

            if (mColumnCount <= 1) {
                achievementList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                achievementList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            achievementList.setAdapter(new AchievementsRecyclerViewAdapter(AchievementContent.ITEMS));
            checkOutAchievementItems();
            checkPlanBasedAchievementItems();
            achievementList.getAdapter().notifyDataSetChanged();
        }
        return view;
    }

    /**
     * Pulls the current user for correct and updated data pulls from Firebase
     */
    private void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
        try {
            currentUserID = mAuth.getCurrentUser().getUid();

        } catch (Exception e) {

        }
    }

    /**
     * Queries firebase for the list of achievements the user has and adds any to the list
     */
    private void checkOutAchievementItems(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference achievementItems = database.child("Users").child(currentUserID).child("AchievementItems");
        achievementItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    AchievementContent.AchievementItem item = singleSnapShot.getValue(AchievementContent.AchievementItem.class);
                    if(!AchievementContent.ITEMS.contains(item)){
                        AchievementContent.addItem(item);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     *Checks to see the count of completed plans and takes appropriate action if it meets an achievement for the user
     */
    private void checkPlanBasedAchievementItems(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");
        final DatabaseReference achievementItems = database.child("Users").child(currentUserID).child("AchievementItems");

        plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countFinished = 0;
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {

                    PlannerContent.PlannerItem item = singleSnapShot.getValue(PlannerContent.PlannerItem.class);
                    if(item.isCompleted()){
                        countFinished++;
                    }
                    System.out.println(countFinished);
                    if (countFinished == 3){
                        String id = achievementItems.push().getKey();
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Good Start", id);
                        if(!AchievementContent.ITEMS.contains(achievementItem)){
                            achievementItems.child(id).setValue(achievementItem);
                            AchievementContent.addItem(achievementItem);
                        }
                    }
                    if (countFinished == 5){
                        String id = achievementItems.push().getKey();
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Expert", id);
                        if(!AchievementContent.ITEMS.contains(achievementItem)){
                            achievementItems.child(id).setValue(achievementItem);
                            AchievementContent.addItem(achievementItem);
                        }
                    }
                    if (countFinished == 10){
                        String id = achievementItems.push().getKey();
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Master", id);
                        if(!AchievementContent.ITEMS.contains(achievementItem)){
                            achievementItems.child(id).setValue(achievementItem);
                            AchievementContent.addItem(achievementItem);
                        }
                    }
                }
                achievementList.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * Not implemented not enough time
     */
    private void checkFunBasedAchievementItems() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference funItems = database.child("Users").child(currentUserID).child("FunItems");
        final DatabaseReference achievementItems = database.child("Users").child(currentUserID).child("AchievementItems");

        funItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}