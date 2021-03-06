package com.cse390.coronavirus.ui.fun;

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
import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.ui.dialogs.AddFunDialog;
import com.cse390.coronavirus.ui.dialogs.GenerateFunDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Fun Fragment displays all the fun activities for the user and allows
 * New generation based on a questionnaire
 * New activities through manual addition
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class FunFragment extends Fragment implements AddFunDialog.FunDialogListener, GenerateFunDialog.GenerateFunListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static RecyclerView recyclerView;
    private static List<FunContent.FunItem> mValues = FunContent.ITEMS;
    private FirebaseAuth mAuth;
    private String currentUserID;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FunFragment() {
    }

    /**
     *
     * @param columnCount
     * @return
     */
    public static FunFragment newInstance(int columnCount) {
        FunFragment fragment = new FunFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Default creation for the fragment
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
     * Inflates the recycler view and creates listeners for the floating action buttons to generate/add fun activities
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fun_fragment, container, false);
        initAuth();

        // Set the adapter
        if (view.findViewById(R.id.fun_list) instanceof RecyclerView) {

            retrieveFunItems();

            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.fun_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new FunRecyclerViewAdapter(mValues, getContext(), FunFragment.this ));

            FloatingActionButton addB = view.findViewById(R.id.add_fun_fab);
            addB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add stuff to the object here!
                    FragmentManager fm = getFragmentManager();
                    AddFunDialog funDialog = new AddFunDialog();
                    funDialog.setTargetFragment(FunFragment.this, 300);
                    funDialog.show(fm,"Add Fun Dialog");
                    //DummyContent.removeTopItem(); Test for seeing dynamic changes
                }
            });

            FloatingActionButton generateB = view.findViewById(R.id.generate_fun_fab);
            generateB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    GenerateFunDialog genDialog = new GenerateFunDialog();
                    genDialog.setTargetFragment(FunFragment.this, 300);
                    genDialog.show(fm,"Generate Fun Dialog");
                }
            });

            initDelete();

        }

        return view;
    }

    /**
     * Pulls in the current user
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
     *  Interface method that works with the database to add a new fun activity
     * @param fi Fun activity to be added to the list
     */
    @Override
    public void addFunToList(FunContent.FunItem fi) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("FunItems");
        String id = ref.push().getKey();
        System.out.println(id);
        fi.setId(id);
        ref.child(id).setValue(fi);
        FunContent.addItem(fi);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Works with the database to modify an existing fun activity
     * @param id Used to identify the existing activity in the database
     * @param fi The newly modified fun activity
     * @param position Used to identify the existing activity in the recycler view
     */
    @Override
    public void editItem(String id, FunContent.FunItem fi, int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("FunItems");
        DatabaseReference itemReference = ref.child(id);
        itemReference.setValue(fi);
        FunContent.ITEMS.set(position, fi);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Touch recognizer that deletes based on recycler view item movement
     */
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
                FunContent.FunItem item = FunContent.getItem(index);
                String id = item.getId(); // ID to Delete From Firebase
                FunContent.removeItem(index);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("FunItems").child(id);
                ref.removeValue();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
    }

    /**
     * Sorts the items based on end user's settings
     * @param sortFields the user's sorting preferences
     */
    private void sortFunItems(ArrayList<String> sortFields){
        if (sortFields.get(1).equals("ascending")){
            switch(sortFields.get(0)){
                case "description":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemDescriptionComparator());
                    break;
                case "category":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemCategoryComparator());
                    break;
                case "name":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemNameComparator());
                    break;
                case "completed":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemCompletedComparator());
                    break;
            }
        }else if(sortFields.get(1).equals("descending")){
            switch(sortFields.get(0)){
                case "description":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemDescriptionComparatorReverse());
                    break;
                case "category":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemCategoryComparatorReverse());
                    break;
                case "name":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemNameComparatorReverse());
                    break;
                case "completed":
                    Collections.sort(FunContent.ITEMS, new FunContent.FunItemCompletedComparatorReverse());
                    break;
            }
        }
    }

    /**
     * Pulls all fun activities from the database based on the current user and assigns it to the funItems list
     */
    private void retrieveFunItems() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference funItems = database.child("Users").child(currentUserID).child("FunItems");
        DatabaseReference sortFields = database.child("Users").child(currentUserID).child("FunItemsSort");
        funItems.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * updates the list if modifications occur
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    FunContent.FunItem item = singleSnapShot.getValue(FunContent.FunItem.class);
                    if(!FunContent.ITEMS.contains(item)){
                        FunContent.addItem(item);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
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
             * updates the list if modifications occur
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
                    sortFunItems(sortFields);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            /**
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * Takes in a list of fun activities for insertion into the recycler view
     * @param funItemsList the list of fun activities usually pulled from the database
     */
    @Override
    public void addFunItemsToList(List<FunContent.FunItem> funItemsList) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("FunItems");
        for (int i = 0; i < funItemsList.size(); i++){
            FunContent.FunItem fi = funItemsList.get(i);
            String id = ref.push().getKey();
            fi.setId(id);
            ref.child(id).setValue(fi);
            FunContent.addItem(fi);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}