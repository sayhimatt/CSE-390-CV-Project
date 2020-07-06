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
import com.cse390.coronavirus.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
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

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AchievementsFragment newInstance(int columnCount) {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

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
        }
        return view;
    }

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

    private void retrieveAchievementItems() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");
        DatabaseReference funItems = database.child("Users").child(currentUserID).child("FunItems");
        DatabaseReference achievementItems = database.child("Users").child(currentUserID).child("AchievementItems");


        plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    int countFinished = 0;
                    PlannerContent.PlannerItem item = singleSnapShot.getValue(PlannerContent.PlannerItem.class);
                    if(item.isCompleted()){
                      countFinished++;
                    }
                    if (countFinished == 3){
                        String id = "1";
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Good Start", id);
                    }
                    if (countFinished == 5){
                        String id = "1";
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Expert", id);
                    }
                    if (countFinished == 10){
                        String id = "1";
                        AchievementContent.AchievementItem achievementItem = new AchievementContent.AchievementItem("Master", id);

                    }
                }
                achievementList.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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