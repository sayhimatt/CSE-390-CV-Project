package com.cse390.coronavirus.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 * Date picker does exactly what the name implies
 */
public class DatePicker extends DialogFragment {

    /**
     * Forms a calendar picker view with the current date as the initially selected instance
     * @param savedInstanceState
     * @return Returns the newly created calendar with the current date selected
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        return new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener)getActivity(),

                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
    }


}
