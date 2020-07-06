package com.cse390.coronavirus.ui.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent.PlannerItem;
import com.cse390.coronavirus.ui.dialogs.PlanDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PlannerRecyclerViewAdapter extends RecyclerView.Adapter<PlannerRecyclerViewAdapter.ViewHolder> {
    private final List<PlannerContent.PlannerItem> mValues;
    private static final String DATE_FORMAT = "EEE, d MMM yyyy";
    private PlannerFragment plannerFragment;
    private Context c;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public PlannerRecyclerViewAdapter(List<PlannerContent.PlannerItem> items, PlannerFragment plannerFragment, Context context){
        mValues = items;
        this.plannerFragment = plannerFragment;
        this.c = context;
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

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_planner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.categoryView.setText(mValues.get(position).getName());
        holder.subjectView.setText(mValues.get(position).getCategory());
        holder.descriptionView.setText(mValues.get(position).getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dueTime = "";
        try{
            dueTime = dateFormat.format(mValues.get(position).getDueDate());
        }catch(NullPointerException e){
            dueTime = dateFormat.format(Calendar.getInstance().getTime());
        }
        holder.dateView.setText(
            dueTime
        );
        final int pos = position;
        initAuth();
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String id = PlannerContent.ITEMS.get(pos).getId();
                PlanDialog planDialog = new PlanDialog();
                planDialog.setDetails(pos, holder.categoryView.getText().toString(), holder.subjectView.getText().toString(), holder.descriptionView.getText().toString());
                planDialog.givingDetails(id, pos);
                planDialog.setTargetFragment(plannerFragment, 100);
                planDialog.show( plannerFragment.getParentFragmentManager(), "Edit Plan Dialog");
                return true;
            }
        });

        holder.completedCheckB.setChecked(PlannerContent.ITEMS.get(pos).isCompleted());

        holder.completedCheckB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   String id = PlannerContent.ITEMS.get(pos).getId();
                   DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                   DatabaseReference plannerItemsCompleted = database.child("Users").child(currentUserID).child("PlannerItems").child(id).child("completed");
                   PlannerContent.ITEMS.get(pos).setCompleted(isChecked);
                   plannerItemsCompleted.setValue(isChecked);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = PlannerContent.ITEMS.get(pos).getId();
                PlanDialog planDialog = new PlanDialog();
                planDialog.setDetails(pos, holder.categoryView.getText().toString(), holder.subjectView.getText().toString(), holder.descriptionView.getText().toString());
                planDialog.givingDetails(id, pos);
                planDialog.setTargetFragment(plannerFragment, 100);
                planDialog.show( plannerFragment.getParentFragmentManager(), "Edit Plan Dialog");
            }
        });

        holder.hideDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.showDetails){
                    holder.hideDescriptionButton.setText("Show\n Details");
                    holder.descriptionView.setVisibility(View.INVISIBLE);
                    holder.showDetails = false;
                }else{
                    holder.hideDescriptionButton.setText("Hide\n Details");
                    holder.descriptionView.setVisibility(View.VISIBLE);
                    holder.showDetails = true;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView categoryView;
        public final TextView subjectView;
        public final TextView descriptionView;
        public final TextView dateView;
        public final CheckBox completedCheckB;
        public final Button hideDescriptionButton;
        public final Button editButton;
        public boolean showDetails;

        public PlannerItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            categoryView = view.findViewById(R.id.category_tv);
            subjectView =  view.findViewById(R.id.name_tv);
            descriptionView = view.findViewById(R.id.desc_tv);
            dateView = view.findViewById(R.id.due_date_tv);
            completedCheckB = view.findViewById(R.id.completed_cb);
            hideDescriptionButton = view.findViewById(R.id.hide_description_button_planner);
            editButton = view.findViewById(R.id.edit_item_button_planner);
            showDetails = true;
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + subjectView.getText() + "'";
        }
    }

}
