package com.guidi.coronavirus.ui.planner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guidi.coronavirus.R;
import com.guidi.coronavirus.dummy.DummyContent;
import com.guidi.coronavirus.ui.achievements.AchievementsFragment;
import com.guidi.coronavirus.DatabaseHelper.PlannerItemDatabaseHelper;
import com.guidi.coronavirus.DatabaseHelper.PlannerItem;

public class PlannerFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlannerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_planner_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new PlannerRecyclerViewAdapter(DummyContent.ITEMS));
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("FunkTasks");
            DummyContent.DummyItem item = new DummyContent.DummyItem("4", "World", "Build");

            mDatabase.push().setValue(item);
        }
        return view;
    }
}