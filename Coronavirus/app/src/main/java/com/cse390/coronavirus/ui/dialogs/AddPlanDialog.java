package com.cse390.coronavirus.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;

import java.util.Date;

public class AddPlanDialog extends DialogFragment{

    private EditText planNameET, planSubjectET, planDescET;
    private PlanDialogListener planDialogListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_plan, null);

        planNameET = view.findViewById(R.id.plan_et);
        planSubjectET = view.findViewById(R.id.plan_subject_et);
        planDescET = view.findViewById(R.id.description_et);

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

                PlannerContent.PlannerItem pi = new PlannerContent.PlannerItem(name, subject, desc, false, new Date(), "1");
                planDialogListener = (PlanDialogListener)getTargetFragment();
                planDialogListener.addPlanToList(pi);
            }
        });



        return builder.create();
    }
    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            planDialogListener = (PlanDialogListener) c;
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement PlanDialogListener");
        }
    }
    public interface PlanDialogListener{
        void addPlanToList(PlannerContent.PlannerItem pi);
    }
}