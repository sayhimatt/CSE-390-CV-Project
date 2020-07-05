package com.cse390.coronavirus.ui.fun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.ui.dialogs.AddFunDialog;

import java.util.List;

public class FunRecyclerViewAdapter extends RecyclerView.Adapter<FunRecyclerViewAdapter.ViewHolder> {

    private final List<FunContent.FunItem> mValues;
    private Context c;
    public FunRecyclerViewAdapter(List<FunContent.FunItem> items, Context c) {
        mValues = items;
        this.c = c;
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
        holder.mItem = mValues.get(position);
        holder.mCategoryView.setText(mValues.get(position).getCategory());
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescView.setText(mValues.get(position).getDescription());
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AddFunDialog funDialog = new AddFunDialog();
                funDialog.setDetails(position,
                        holder.mCategoryView.getText().toString(), holder.mNameView.getText().toString(),holder.mDescView.getText().toString());
                funDialog.show(((FragmentActivity)c).getSupportFragmentManager(), "Edit Plan Dialog");
                return false;
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategoryView = view.findViewById(R.id.category_tv);
            mNameView = view.findViewById(R.id.name_tv);
            mDescView = view.findViewById(R.id.descf_tv);
            completedCB = view.findViewById(R.id.completedf_cb);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}