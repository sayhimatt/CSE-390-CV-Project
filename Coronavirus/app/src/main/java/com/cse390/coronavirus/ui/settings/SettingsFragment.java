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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cse390.coronavirus.R;
//import com.guidi.coronavirus.ui.fun.FunViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private Spinner sortCriteriaSpinner;
    private Spinner sortOrderSpinner;
    private Spinner sortListSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        Context context = root.getContext();
        sortCriteriaSpinner = root.findViewById(R.id.sort_criteria_spinner);
        sortOrderSpinner = root.findViewById(R.id.sort_order_spinner);
        sortListSpinner = root.findViewById(R.id.sort_list_spinner);

        ArrayAdapter<CharSequence> criteriaAdapter = ArrayAdapter.createFromResource(context,
                R.array.item_sort_criteria_array_planner, R.layout.spinner_item_sorting);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(context,
                R.array.item_sort_order_array, R.layout.spinner_item_sorting);
        ArrayAdapter<CharSequence> sortListAdapter = ArrayAdapter.createFromResource(context,
                R.array.list_sort_array, R.layout.spinner_item_sorting);


        criteriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortCriteriaSpinner.setAdapter(criteriaAdapter);
        sortOrderSpinner.setAdapter(orderAdapter);
        sortListSpinner.setAdapter(sortListAdapter);


        return root;
    }

}