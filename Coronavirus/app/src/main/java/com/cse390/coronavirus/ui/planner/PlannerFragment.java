package com.cse390.coronavirus.ui.planner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.MainActivity;
import com.cse390.coronavirus.ui.dialogs.AddPlanDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.achievements.AchievementsFragment;

import java.util.List;

public class PlannerFragment extends Fragment implements AddPlanDialog.PlanDialogListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static RecyclerView planList;
    private static List<DummyContent.DummyItem> mValues = DummyContent.ITEMS;

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


            //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("FunkTasks");
            //DummyContent.DummyItem item = new DummyContent.DummyItem("4", "World", "Build");
            //mDatabase.push().setValue(item);

            FloatingActionButton addB = view.findViewById(R.id.add_planner_fab);
            addB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add stuff to the object here!
                    FragmentManager fm = getFragmentManager();
                    AddPlanDialog planDialog = new AddPlanDialog();
                    planDialog.setTargetFragment(PlannerFragment.this, 300);
                    planDialog.show(fm,"Add Plan Dialog");
                    //DummyContent.removeTopItem(); Test for seeing dynamic changes


                }
            });
        }
        return view;
    }

    @Override
    public void addPlanToList(DummyContent.DummyItem di) {
        DummyContent.addItem(di);
        planList.getAdapter().notifyDataSetChanged();
    }
}