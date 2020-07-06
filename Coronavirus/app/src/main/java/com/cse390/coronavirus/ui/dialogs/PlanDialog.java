package com.cse390.coronavirus.ui.dialogs;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cse390.coronavirus.MainActivity;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;


import java.util.Date;

/**
 * Plan dialog allows the user to add/modify plans
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class PlanDialog extends DialogFragment {
    private String name, subject, description;
    private EditText planNameET, planSubjectET, planDescET;
    private PlanDialogListener planDialogListener;
    private int pos;
    private boolean isDetails = false;
    private TextView titleDialog;
    private String itemToEditID;
    private int positionToEdit;
    private boolean checked;

    /**
     * Generates the dialog and assigns click listeners for the date picker dialog button
     * Also creates dialog options to save and cancel the current plan item
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_plan, null);

        planNameET = view.findViewById(R.id.plan_et);
        planSubjectET = view.findViewById(R.id.plan_subject_et);
        planDescET = view.findViewById(R.id.description_et);
        titleDialog = view.findViewById(R.id.add_item_header_tv);



        Button dateB = view.findViewById(R.id.date_pick_b);
        dateB.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePicker();
                datePicker.show(getChildFragmentManager(), "Date Picker");

            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        if (isDetails){
            builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                /**
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = planNameET.getText().toString();
                    String category = planSubjectET.getText().toString();
                    String desc = planDescET.getText().toString();
                    MainActivity activity = (MainActivity) getActivity();
                    Date userDate = activity.getDateSet();
                    if (userDate == null){
                        userDate = new Date();
                    }
                    PlannerContent.PlannerItem pi = new PlannerContent.PlannerItem(name, category, desc, checked, userDate, itemToEditID);
                    planDialogListener = (PlanDialogListener) getTargetFragment();
                    planDialogListener.editItem(itemToEditID, pi, positionToEdit);
                }
            });
        }else{
            builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                /**
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = planNameET.getText().toString();
                    String category = planSubjectET.getText().toString();
                    String desc = planDescET.getText().toString();
                    MainActivity activity = (MainActivity) getActivity();
                    Date userDate = activity.getDateSet();
                    if (userDate == null){
                        userDate = new Date();
                    }
                    PlannerContent.PlannerItem pi = new PlannerContent.PlannerItem(name, category, desc, false, userDate, "1");
                    planDialogListener.addPlanToList(pi);
                }
            });
        }

        if(isDetails){
            planNameET.setText(name);
            planSubjectET.setText(subject);
            planDescET.setText(description);
            titleDialog.setText(R.string.edit_plan_item_text);
        }
        return builder.create();
    }

    /**
     * Tells the plan dialog that we're editing a selected plan
     * @param id unique id to be referenced when saved
     * @param position position in the list to be referenced
     * @param checked
     */
    public void givingDetails(String id, int position, boolean checked){
        isDetails = true;
        this.itemToEditID = id;
        this.positionToEdit = position;
        this.checked = checked;
    }

    /**
     * Gives details to prefill the edit texts in the dialog (if applicable)
     * @param pos position in the list to be referenced
     * @param name
     * @param subject
     * @param description
     */
    public void setDetails(int pos, String name, String subject, String description){
        this.pos = pos;
        this.name = name;
        this.subject = subject;
        this.description = description;
    }

    /**
     * Confirms that the calling fragment implements the interface
     * @param c
     */
    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            planDialogListener = (PlanDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement PlanDialogListener");
        }
    }

    /**
     *Allows the dialog to work with the database and list when saving/editing
     */
    public interface PlanDialogListener{
        void addPlanToList(PlannerContent.PlannerItem pi);
        void editItem(String id, PlannerContent.PlannerItem pi, int position);
    }
}
