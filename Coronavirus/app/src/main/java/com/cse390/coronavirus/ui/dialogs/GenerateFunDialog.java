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
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cse390.coronavirus.R;
import com.cse390.coronavirus.dummy.DummyContent;

public class GenerateFunDialog extends DialogFragment {
    private RadioGroup answersRG;
    private TextView questionTV;
    private Button submitAnsB;
    private boolean lastQuestion;
    private GenerateFunListener generateFunListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_gen_fun, null);

        answersRG = (RadioGroup)view.findViewById(R.id.answers_rg);
        questionTV = (TextView)view.findViewById(R.id.question_fun_tv);
        submitAnsB = (Button) view.findViewById(R.id.submit_b);
        lastQuestion = false;


        submitAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO STUFF HERE FOR NEXT
                if(!lastQuestion){

                    nextQuestion();
                }else{
                    dismiss();
                }
            }
        });



        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
/*        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DummyContent.DummyItem di = new DummyContent.DummyItem("Matt","can't","code");

                generateFunListener = (GenerateFunListener)getTargetFragment();

                generateFunListener.addFunToList(di);
            }
        });*/
        return builder.create();
    }

    public void nextQuestion(){
        lastQuestion = true;
    }


    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        // disabled for now
/*        try {
            generateFunListener = (GenerateFunListener) c;
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement FunDialogListener");
        }*/
    }
    public interface GenerateFunListener{
        void addFunToList(DummyContent.DummyItem di);
    }
}
