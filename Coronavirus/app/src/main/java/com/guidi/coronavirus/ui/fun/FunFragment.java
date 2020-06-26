package com.guidi.coronavirus.ui.fun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.guidi.coronavirus.R;

public class FunFragment extends Fragment {

    private FunViewModel funViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        funViewModel =
                ViewModelProviders.of(this).get(FunViewModel.class);
        View root = inflater.inflate(R.layout.fun_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        funViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}