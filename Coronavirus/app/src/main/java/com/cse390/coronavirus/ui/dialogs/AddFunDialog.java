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

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.fun.FunFragment;

public class AddFunDialog extends DialogFragment{
    private EditText funCategory, funName, funDescription;
    private FunDialogListener funDialogListener;
    private boolean isDetails = false;
    private String category, name, desc;
    private int position;
    private String itemToEditID;
    private boolean checked;
    private TextView titleDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_fun, null);

        funCategory = view.findViewById(R.id.fun_category);
        funName = view.findViewById(R.id.fun_name);
        funDescription = view.findViewById(R.id.fun_description);
        titleDialog = view.findViewById(R.id.add_item_header_tv);

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        if (isDetails){
            builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String category = funCategory.getText().toString();
                    String name = funName.getText().toString();
                    String description = funDescription.getText().toString();
                    FunContent.FunItem fi = new FunContent.FunItem(category, name, description, checked, itemToEditID);
                    funDialogListener = (FunDialogListener)getTargetFragment();
                    funDialogListener.editItem(itemToEditID, fi, position);
                }
            });
        }else{
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
        }


        if(isDetails){
            funCategory.setText(category);
            funName.setText(name);
            funDescription.setText(desc);
            titleDialog.setText(R.string.edit_fun_activity);
        }
        return builder.create();
    }

    public void setDetails(int pos, String category, String name, String desc, String id, boolean checked) {
        isDetails = true;
        this.category = category;
        this.name = name;
        this.desc = desc;
        this.position = pos;
        this.itemToEditID = id;
        this.checked = checked;
    }

    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            funDialogListener = (FunDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement FunDialogListener");
        }
    }
    public interface FunDialogListener{
        void addFunToList(FunContent.FunItem fi);
        void editItem(String id, FunContent.FunItem fi, int position);
    }
}


