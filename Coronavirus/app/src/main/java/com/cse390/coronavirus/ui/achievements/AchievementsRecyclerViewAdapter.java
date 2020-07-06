package com.cse390.coronavirus.ui.achievements;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse390.coronavirus.DatabaseHelper.AchievementContent;
import com.cse390.coronavirus.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AchievementsRecyclerViewAdapter extends RecyclerView.Adapter<AchievementsRecyclerViewAdapter.ViewHolder> {

    private final List<AchievementContent.AchievementItem> mValues;

    /**
     * @param items
     */
    public AchievementsRecyclerViewAdapter(List<AchievementContent.AchievementItem> items) {
        mValues = items;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.fragment_achievement_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mIdView.setText(String.valueOf(position+1));
        holder.mContentView.setText(mValues.get(position).getName());

        // Check Achievement
        String achievementName = mValues.get(position).getName();
        switch (achievementName){
            case "Expert":
                holder.mAchievementImageView.setImageResource(R.drawable.base_award);
                break;
            case "Master" :
                holder.mAchievementImageView.setImageResource(R.drawable.high_award);
                break;
        }
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mAchievementImageView;


        /**
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.plan_tv);
            mContentView = (TextView) view.findViewById(R.id.subject_tv);
            mAchievementImageView = view.findViewById(R.id.achievement_image);
        }

        /**
         * @return
         */
        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}