package com.cse390.coronavirus.ui.settings;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cse390.coronavirus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.guidi.coronavirus.ui.fun.FunViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private Spinner sortCriteriaSpinner;
    private Spinner sortOrderSpinner;
    private Spinner sortListSpinner;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        Context context = root.getContext();
        sortCriteriaSpinner = root.findViewById(R.id.sort_criteria_spinner);
        sortOrderSpinner = root.findViewById(R.id.sort_order_spinner);
        sortListSpinner = root.findViewById(R.id.sort_list_spinner);
        submitButton = root.findViewById(R.id.submit_sorting);
        initAuth();

        final ArrayAdapter<CharSequence> criteriaAdapterPlanner = ArrayAdapter.createFromResource(context,
                R.array.item_sort_criteria_array_planner, R.layout.spinner_item_sorting);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(context,
                R.array.item_sort_order_array, R.layout.spinner_item_sorting);
        ArrayAdapter<CharSequence> sortListAdapter = ArrayAdapter.createFromResource(context,
                R.array.list_sort_array, R.layout.spinner_item_sorting);
        final ArrayAdapter<CharSequence> criteriaAdapterFun = ArrayAdapter.createFromResource(context,
                R.array.item_sort_criteria_array_fun, R.layout.spinner_item_sorting);

        criteriaAdapterPlanner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        criteriaAdapterFun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortCriteriaSpinner.setAdapter(criteriaAdapterPlanner);
        sortOrderSpinner.setAdapter(orderAdapter);
        sortListSpinner.setAdapter(sortListAdapter);
        

        sortListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    sortCriteriaSpinner.setAdapter(criteriaAdapterFun);
                }else if (position == 0){
                    sortCriteriaSpinner.setAdapter(criteriaAdapterPlanner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sortList = sortListSpinner.getSelectedItem().toString() + "ItemsSort";
                String sortCriteria = sortCriteriaSpinner.getSelectedItem().toString();
                String sortOrder = sortOrderSpinner.getSelectedItem().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(sortList);
                ref.child("sortCriteria").setValue(sortCriteria);
                ref.child("sortOrder").setValue(sortOrder);
            }
        });


        return root;
    }

    private void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
        try {
            currentUserID = mAuth.getCurrentUser().getUid();

        } catch (Exception e) {

        }
    }

}