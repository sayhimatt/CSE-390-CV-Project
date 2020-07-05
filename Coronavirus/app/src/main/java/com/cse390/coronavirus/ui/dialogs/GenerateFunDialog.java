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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.MainActivity;
import com.cse390.coronavirus.R;
import com.cse390.coronavirus.SignUpActivity;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.fun.FunFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateFunDialog extends DialogFragment {
    private RadioGroup answersRG;
    private TextView questionTV;
    private Button submitAnsB;
    private boolean lastQuestion;
    private GenerateFunListener generateFunListener;
    String[] questions = new String[]{"Do you like movies?", "Do you like the outdoors?", "Do you like games?"};
    String[] movies = new String[]{"Star Wars", "The Godfather", "Kill Bill"};
    String[] outdoors = new String[]{"Hiking" , "Camping", "Park Walk"};
    String[] games = new String[]{"Cards Against Humanity","Monopoly","Game Of Life"};
    String[][] activities = new String[][]{movies, outdoors, games};
    String[] categoriesGenerated = new String[]{"Movie", "Outdoor", "Game"};
    private int optionsSelected;
    private List<FunContent.FunItem> generatedItems = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_gen_fun, null);

        answersRG = (RadioGroup)view.findViewById(R.id.answers_rg);
        questionTV = (TextView)view.findViewById(R.id.question_fun_tv);
        submitAnsB = (Button) view.findViewById(R.id.submit_b);
        lastQuestion = false;
        optionsSelected = 0;
        setQuestion(optionsSelected);

        submitAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answersRG.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getActivity() , "Please Select An Option", Toast.LENGTH_SHORT).show();
                }else{
                    optionsSelected++;
                    switch (optionsSelected){
                        case 1:
                            getItems(0);
                            break;
                        case 2:
                            getItems(1);
                            break;
                        case 3:
                            getItems(2);
                            generateFunListener = (GenerateFunDialog.GenerateFunListener)getTargetFragment();
                            generateFunListener.addFunItemsToList(generatedItems);
                            dismiss();
                            break;
                        default:
                            break;
                    }
                    setQuestion(optionsSelected);
                }
            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void getItems(int activitiesListIndex){

        String category = categoriesGenerated[activitiesListIndex];
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < activities[activitiesListIndex].length; i++){
            indices.add(new Integer(i));
        }
        Collections.shuffle(indices);
        FunContent.FunItem funItem1 = new FunContent.FunItem(category, "", activities[activitiesListIndex][indices.get(0)], false, "1");
        FunContent.FunItem funItem2 = new FunContent.FunItem(category, "", activities[activitiesListIndex][indices.get(1)], false, "1");
        FunContent.FunItem funItem3 = new FunContent.FunItem(category, "", activities[activitiesListIndex][indices.get(2)], false, "1");

        switch (answersRG.getCheckedRadioButtonId()){
            case R.id.ans_1_rb:
                generatedItems.add(funItem1);
                generatedItems.add(funItem2);
                generatedItems.add(funItem3);
                break;
            case R.id.ans_2_rb:
                generatedItems.add(funItem1);
                generatedItems.add(funItem2);
                break;
            case R.id.ans_3_rb:
                generatedItems.add(funItem1);
                break;
            default:
                break;
        }
    }

    private void setQuestion(int questionIndex){
        if (questionIndex <= 2){
            questionTV.setText(questions[questionIndex]);
        }
    }

    @Override
    public void onAttach(Context c){
        super.onAttach(c);
        try {
            generateFunListener = (GenerateFunDialog.GenerateFunListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " does not implement FunDialogListener");
        }
    }
    public interface GenerateFunListener{
        void addFunItemsToList(List<FunContent.FunItem> funItemsList);
    }
}
