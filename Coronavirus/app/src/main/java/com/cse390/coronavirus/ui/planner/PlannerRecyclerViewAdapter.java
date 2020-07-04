package com.cse390.coronavirus.ui.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent.PlannerItem;
import com.cse390.coronavirus.ui.dialogs.PlanDialog;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlannerRecyclerViewAdapter extends RecyclerView.Adapter<PlannerRecyclerViewAdapter.ViewHolder> {
    private final List<PlannerContent.PlannerItem> mValues;
    private static final String DATE_FORMAT = "EEE, d MMM yyyy";
    private Context c;
    public PlannerRecyclerViewAdapter(List<PlannerContent.PlannerItem> items, Context c){
        mValues = items;
        this.c = c;
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
        holder.categoryView.setText(mValues.get(position).getCategory());
        holder.subjectView.setText(mValues.get(position).getDescription());

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
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PlanDialog planDialog = new PlanDialog();
                planDialog.setDetails(pos, holder.categoryView.getText().toString(), holder.subjectView.getText().toString());
                planDialog.givingDetails();
                planDialog.show(((FragmentActivity)c).getSupportFragmentManager(), "Edit Plan Dialog");
                return true;
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
        public PlannerItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            categoryView = view.findViewById(R.id.category_tv);
            subjectView =  view.findViewById(R.id.subject_tv);
            descriptionView = view.findViewById(R.id.desc_tv);
            dateView = view.findViewById(R.id.due_date_tv);
            completedCheckB = view.findViewById(R.id.completed_cb);

        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + subjectView.getText() + "'";
        }
    }

}
