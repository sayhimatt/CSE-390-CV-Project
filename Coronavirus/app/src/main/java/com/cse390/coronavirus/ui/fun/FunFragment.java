package com.cse390.coronavirus.ui.fun;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.dialogs.AddFunDialog;
import com.cse390.coronavirus.ui.dialogs.AddPlanDialog;
import com.cse390.coronavirus.ui.dialogs.GenerateFunDialog;
import com.cse390.coronavirus.ui.planner.PlannerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FunFragment extends Fragment implements AddFunDialog.FunDialogListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static RecyclerView recyclerView;
    private static List<FunContent.FunItem> mValues = FunContent.ITEMS;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FunFragment() {
    }

    // TODO: Customize parameter initialization
    public static FunFragment newInstance(int columnCount) {
        FunFragment fragment = new FunFragment();
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
        View view = inflater.inflate(R.layout.fun_fragment, container, false);


        // Set the adapter
        if (view.findViewById(R.id.fun_list) instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.fun_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new FunRecyclerViewAdapter(mValues));

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


            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int index = viewHolder.getAdapterPosition();
                    DummyContent.removeItem(index);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }).attachToRecyclerView(recyclerView);
        }

        return view;
    }
    @Override
    public void addFunToList(DummyContent.DummyItem di) {
        DummyContent.addItem(di);
        recyclerView.getAdapter().notifyDataSetChanged();
    }


}