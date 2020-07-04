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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;

public class AddFunDialog extends DialogFragment{
    private EditText funCategory, funName, funDescription;
    private FunDialogListener funDialogListener;
    private boolean isDetails = false;
    private String category, name, desc;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_fun, null);

        funCategory = view.findViewById(R.id.fun_category);
        funName = view.findViewById(R.id.fun_name);
        funDescription = view.findViewById(R.id.fun_description);

        /*
        Button dateB = view.findViewById(R.id.date_pickf_b);
        dateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePicker();
                datePicker.show(getChildFragmentManager(), "Date Picker");
            }
        });
        */


        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = funCategory.getText().toString();
                String name = funName.getText().toString();
                String description = funDescription.getText().toString();
                FunContent.FunItem fi = new FunContent.FunItem(category, name, description, false, "1");
                funDialogListener = (FunDialogListener)getTargetFragment();
                funDialogListener.addFunToList(fi);
            }
        });
        if(isDetails){
            funCategory.setText(category);
            funName.setText(name);
            funDescription.setText(desc);
        }
        return builder.create();
    }

    public void setDetails(int pos, String category, String name, String desc) {
        isDetails = true;
        this.category = category;
        this.name = name;
        this.desc = desc;
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
        void addFunToList(FunContent.FunItem fi);
    }
}


