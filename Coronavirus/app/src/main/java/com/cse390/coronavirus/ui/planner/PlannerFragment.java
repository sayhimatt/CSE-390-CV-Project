package com.cse390.coronavirus.ui.planner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.MainActivity;
import com.cse390.coronavirus.SignUpActivity;
import com.cse390.coronavirus.ui.dialogs.AddPlanDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.achievements.AchievementsFragment;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;

import java.util.List;

public class PlannerFragment extends Fragment implements AddPlanDialog.PlanDialogListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static RecyclerView planList;
    private static List<PlannerContent.PlannerItem> mValues = PlannerContent.ITEMS;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String currentUserID;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlannerFragment() {
    }

    // TODO: Customize parameter initialization
    public static PlannerFragment newInstance(int columnCount) {
        PlannerFragment fragment = new PlannerFragment();
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
        View view = inflater.inflate(R.layout.planner_fragment, container, false);
        initAuth();
        retrievePlannerItems();

        // Set the adapter
        if (view.findViewById(R.id.planner_list) instanceof RecyclerView) {

            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.planner_list);
            planList = recyclerView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new PlannerRecyclerViewAdapter(mValues));

            FloatingActionButton addB = view.findViewById(R.id.add_planner_fab);
            addB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initDialogFab();
                }
            });
            initDelete();
        }
        return view;
    }

    @Override
    public void addPlanToList(PlannerContent.PlannerItem pi) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("PlannerItems");
        String id =  ref.push().getKey();
        pi.setId(id);
        ref.child(id).setValue(pi);
        PlannerContent.addItem(pi);
        planList.getAdapter().notifyDataSetChanged();

    }

    private void initAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
        try {
            currentUserID = mAuth.getCurrentUser().getUid();

        }catch (Exception e){
            // Load Dummy Data Here
        }
    }

    private void initDelete(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                PlannerContent.PlannerItem item = PlannerContent.getItem(index);
                String id = item.getId(); // ID to Delete From Firebase
                PlannerContent.removeItem(index);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("PlannerItems").child(id);
                ref.removeValue();

                planList.getAdapter().notifyDataSetChanged();
            }
        }).attachToRecyclerView(planList);
    }

    private void initDialogFab(){
        // Add stuff to the object here!
        FragmentManager fm = getFragmentManager();
        AddPlanDialog planDialog = new AddPlanDialog();
        planDialog.setTargetFragment(PlannerFragment.this, 300);
        planDialog.show(fm,"Add Plan Dialog");
        //DummyContent.removeTopItem(); Test for seeing dynamic changes
    }

    private void retrievePlannerItems(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");

        plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapShot : snapshot.getChildren()){
                    PlannerContent.PlannerItem item = singleSnapShot.getValue(PlannerContent.PlannerItem.class);
                    PlannerContent.addItem(item);
                    planList.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}