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

public class AddFunDialog extends DialogFragment{
    private EditText planNameET, planSubjectET, planDescET;
    private FunDialogListener funDialogListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_fun, null);

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

                DummyContent.DummyItem di = new DummyContent.DummyItem(subject,name,desc);
                funDialogListener = (FunDialogListener)getTargetFragment();
                funDialogListener.addFunToList(di);
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            funDialogListener = (FunDialogListener) c;
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement FunDialogListener");
        }
    }
    public interface FunDialogListener{
        void addFunToList(DummyContent.DummyItem di);
    }
}


