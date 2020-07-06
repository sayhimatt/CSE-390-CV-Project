package com.cse390.coronavirus.ui.planner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.ui.dialogs.PlanDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.cse390.coronavirus.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Planner fragment is the overall view for plans that the user has set
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class PlannerFragment extends Fragment implements PlanDialog.PlanDialogListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static RecyclerView planList;
    private static List<PlannerContent.PlannerItem> mValues = PlannerContent.ITEMS;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private PlannerFragment plannerFragment = PlannerFragment.this;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlannerFragment() {
    }

    /**
     * Not in use
     * @param columnCount
     * @return
     */
    // TODO: Customize parameter initialization
    public static PlannerFragment newInstance(int columnCount) {
        PlannerFragment fragment = new PlannerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the fragment must be called
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
     * Inflate the layout and instantiates the recycler view list
     * Assigns click listeners for the floating action buttons
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.planner_fragment, container, false);
        initAuth();
        // Set the adapter
        if (view.findViewById(R.id.planner_list) instanceof RecyclerView) {

            Context context = view.getContext();
            planList = (RecyclerView) view.findViewById(R.id.planner_list);

            retrievePlannerItems();

            if (mColumnCount <= 1) {
                planList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                planList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            planList.setAdapter(new PlannerRecyclerViewAdapter(mValues,plannerFragment, getContext()));

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

    /**
     *  Adds a plan to the database and list and finally updating the recycler view to visibly show the change
     * @param pi
     */
    public void addPlanToList(PlannerContent.PlannerItem pi) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("PlannerItems");
        String id = ref.push().getKey();
        pi.setId(id);
        ref.child(id).setValue(pi);
        PlannerContent.addItem(pi);
        planList.getAdapter().notifyDataSetChanged();

    }

    /**
     *  Edits an existing plan to the database and list and finally updating the recycler view to visibly show the change
     * @param id unique id of the plan
     * @param pi the new plan to be inserted
     * @param position the position in the list of the plan
     */
    @Override
    public void editItem(String id, PlannerContent.PlannerItem pi, int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("PlannerItems");
        DatabaseReference itemReference = ref.child(id);
        itemReference.setValue(pi);
        PlannerContent.ITEMS.set(position, pi);
        planList.getAdapter().notifyDataSetChanged();
    }

    /**
     * Grabs the current user for use in pulling firebase data
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
     * Assigns touch listeners to delete existing plans through swiping
     * Alerts the recycler view and database when appropriate
     */
    private void initDelete() {
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

    /**
     * Creates the dialog for adding a new dialog
     */
    private void initDialogFab() {
        // Add stuff to the object here!
        FragmentManager fm = getFragmentManager();
        PlanDialog planDialog = new PlanDialog();
        planDialog.setTargetFragment(PlannerFragment.this, 300);
        planDialog.show(fm, "Add Plan Dialog");

    }


    /**
     * Pulls in the sort fields preferences and adjusts the list accordingly
     * @param sortFields the list of preferences setup by the user in the settings
     */
    private void sortPlannerItems(ArrayList<String> sortFields){
        // Get the sortCriteria
        if (sortFields.get(1).equals("ascending")){
            switch(sortFields.get(0)){
                case "description":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemDescriptionComparator());
                    break;
                case "category":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemCategoryComparator());
                    break;
                case "name":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemNameComparator());
                    break;
                case "due date":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemDateComparator());
                    break;
                case "completed":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemCompletedComparator());
                    break;
            }
        }else if(sortFields.get(1).equals("descending")){
            switch(sortFields.get(0)){
                case "description":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemDescriptionComparatorReverse());
                    break;
                case "category":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemCategoryComparatorReverse());
                    break;
                case "name":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemNameComparatorReverse());
                    break;
                case "due date":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemDateComparatorReverse());
                    break;
                case "completed":
                    Collections.sort(PlannerContent.ITEMS, new PlannerContent.PlannerItemCompletedComparatorReverse());
                    break;
            }
        }
    }

    /**
     * Retrieves the current list of plans and sort preferences set by the current user
     * A
     */
    private void retrievePlannerItems() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");
        DatabaseReference sortFields = database.child("Users").child(currentUserID).child("PlannerItemsSort");
        plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Modifies the list based on any data changes detected in the database and updates the recycler view accordingly
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    PlannerContent.PlannerItem item = singleSnapShot.getValue(PlannerContent.PlannerItem.class);
                    if(!PlannerContent.ITEMS.contains(item)){
                        PlannerContent.addItem(item);
                    }
                }
                planList.getAdapter().notifyDataSetChanged();

            }

            /**
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sortFields.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Retrieves any changes to the sort preferences and adjusts accordingly
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> sortFields = new ArrayList<>();
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    String item = singleSnapShot.getValue(String.class);
                    sortFields.add(item.toLowerCase());
                }
                if (sortFields.size() > 0){
                    sortPlannerItems(sortFields);
                }
                planList.getAdapter().notifyDataSetChanged();
            }

            /**
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}