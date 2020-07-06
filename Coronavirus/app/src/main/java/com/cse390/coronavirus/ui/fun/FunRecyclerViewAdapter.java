package com.cse390.coronavirus.ui.fun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.ui.dialogs.AddFunDialog;
import com.cse390.coronavirus.ui.planner.PlannerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FunRecyclerViewAdapter extends RecyclerView.Adapter<FunRecyclerViewAdapter.ViewHolder> {

    private final List<FunContent.FunItem> mValues;
    private Context c;
    private FunFragment funFragment;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public FunRecyclerViewAdapter(List<FunContent.FunItem> items, Context c, FunFragment funFragment) {
        mValues = items;
        this.c = c;
        this.funFragment = funFragment;
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
                .inflate(R.layout.fragment_fun_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        initAuth();
        holder.mItem = mValues.get(position);
        holder.mCategoryView.setText(mValues.get(position).getCategory());
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescView.setText(mValues.get(position).getDescription());

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AddFunDialog funDialog = new AddFunDialog();
                String id = FunContent.ITEMS.get(position).getId();
                boolean checked = holder.completedCB.isChecked();
                funDialog.setDetails(position,
                        holder.mCategoryView.getText().toString(), holder.mNameView.getText().toString(),holder.mDescView.getText().toString(), id, checked);
                funDialog.setTargetFragment(funFragment, 200);
                funDialog.show( funFragment.getParentFragmentManager(), "Edit Plan Dialog");
                return false;
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFunDialog funDialog = new AddFunDialog();
                String id = FunContent.ITEMS.get(position).getId();
                boolean checked = holder.completedCB.isChecked();
                funDialog.setDetails(position,
                        holder.mCategoryView.getText().toString(), holder.mNameView.getText().toString(),holder.mDescView.getText().toString(), id, checked);
                funDialog.setTargetFragment(funFragment, 200);
                funDialog.show( funFragment.getParentFragmentManager(), "Edit Plan Dialog");
            }
        });

        holder.completedCB.setChecked(FunContent.ITEMS.get(position).isCompleted());

        holder.completedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String id = FunContent.ITEMS.get(position).getId();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference funItemsCompleted = database.child("Users").child(currentUserID).child("FunItems").child(id).child("completed");
                FunContent.ITEMS.get(position).setCompleted(isChecked);
                funItemsCompleted.setValue(isChecked);
            }
        });

        holder.hideDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.showDetails){
                    holder.hideDescriptionButton.setText("Show\n Details");
                    holder.mDescView.setVisibility(View.INVISIBLE);
                    holder.showDetails = false;
                }else{
                    holder.hideDescriptionButton.setText("Hide\n Details");
                    holder.mDescView.setVisibility(View.VISIBLE);
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
        public final TextView mCategoryView;
        public final TextView mNameView;
        public final TextView mDescView;
        public final CheckBox completedCB;
        public FunContent.FunItem mItem;
        public final Button hideDescriptionButton;
        public final Button editButton;
        public boolean showDetails;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategoryView = view.findViewById(R.id.plan_tv);
            mNameView = view.findViewById(R.id.subject_tv);
            mDescView = view.findViewById(R.id.descf_tv);
            completedCB = view.findViewById(R.id.completedf_cb);
            hideDescriptionButton = view.findViewById(R.id.hide_description_button_fun);
            editButton = view.findViewById(R.id.edit_item_button_fun);
            showDetails = true;
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}