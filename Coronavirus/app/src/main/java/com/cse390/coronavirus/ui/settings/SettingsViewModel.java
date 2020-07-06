package com.cse390.coronavirus.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Not used
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    /**
     *
     */
    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the settings fragment");
    }

    /**
     * @return
     */
    public LiveData<String> getText() {
        return mText;
    }
}
