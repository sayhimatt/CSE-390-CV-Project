package com.cse390.coronavirus;

import android.content.Intent;
import android.os.Bundle;

import com.cse390.coronavirus.DatabaseHelper.FunContent;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.cse390.coronavirus.dummy.DummyContent;
import com.cse390.coronavirus.ui.dialogs.AddFunDialog;
import com.cse390.coronavirus.ui.dialogs.AddPlanDialog;
import com.cse390.coronavirus.ui.dialogs.GenerateFunDialog;
import com.cse390.coronavirus.ui.fun.FunFragment;
import com.cse390.coronavirus.ui.planner.PlannerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements AddPlanDialog.PlanDialogListener, AddFunDialog.FunDialogListener{
    private static final int SIGN_UP_ACTIVITY_CODE = 123;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Testing on New Machine Successful
            }
        });


        try {
            currentUserID = mAuth.getCurrentUser().getUid();
            setContentView(R.layout.activity_main);
            // Creating Bottom Navigator View
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_planner, R.id.navigation_fun, R.id.navigation_achievements, R.id.navigation_settings)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
            // Setting the user to the database
            navController.navigate(R.id.navigation_planner);



        }catch (Exception e){
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivityForResult(intent, SIGN_UP_ACTIVITY_CODE);
        }
    }

    @Override
    public void addPlanToList(PlannerContent.PlannerItem di) {
            // Add the plan to the list

    }

    @Override
    public void addFunToList(FunContent.FunItem fi) {
            /// Add the fun to the list
    }



}