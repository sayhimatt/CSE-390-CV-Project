package com.cse390.coronavirus.ui.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.cse390.coronavirus.MainActivity;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.ui.planner.PlannerFragment;

import java.util.Date;

public class PlanDialog extends DialogFragment {
    private String name, subject;
    private EditText planNameET, planSubjectET, planDescET;
    private PlanDialogListener planDialogListener;
    private int pos;
    private boolean isDetails = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_plan, null);

        planNameET = view.findViewById(R.id.plan_et);
        planSubjectET = view.findViewById(R.id.plan_subject_et);
        planDescET = view.findViewById(R.id.description_et);



        Button dateB = view.findViewById(R.id.date_pick_b);
        dateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePicker();
                datePicker.show(getChildFragmentManager(), "Date Picker");

            }
        });




        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = planNameET.getText().toString();
                String subject = planSubjectET.getText().toString();
                String desc = planDescET.getText().toString();
                MainActivity activity = (MainActivity) getActivity();
                Date userDate = activity.getDateSet();

                PlannerContent.PlannerItem pi = new PlannerContent.PlannerItem(name, subject, desc, false, userDate, "1");

                if(isDetails){
                    PlannerContent.editItem(pos, pi);
                }else {
                    planDialogListener = (PlanDialogListener) getTargetFragment();
                    planDialogListener.addPlanToList(pi);
                }
            }
        });
        if(isDetails){
            planNameET.setText(name);
            planSubjectET.setText(subject);
        }



        return builder.create();
    }
    public void givingDetails(){
        isDetails = true;
    }
    public void setDetails(int pos, String name, String subject){
        this.pos = pos;
        this.name = name;
        this.subject = subject;
    }
    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            planDialogListener = (PlanDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement PlanDialogListener");
        }
    }



    public interface PlanDialogListener{
        void addPlanToList(PlannerContent.PlannerItem pi);
    }
}
