package com.cse390.coronavirus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;


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
        }catch (Exception e){
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivityForResult(intent, SIGN_UP_ACTIVITY_CODE);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (currentUserID != null){
            System.out.println("STOP");
            generateNotification();
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

    private void createNotificationChannel(long myPlannerItemsTotal) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Khiem";
            String description = "Myself";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("100", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            if (myPlannerItemsTotal > 0){
                Intent returnToPlannerIntent = new Intent(this, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(returnToPlannerIntent);
                PendingIntent returnToPlannerPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "100")
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark) //TODO: Change Icon Here
                        .setContentTitle("Items Due Today")
                        .setContentText(String.valueOf(myPlannerItemsTotal) + " Item(s) Due Today!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentIntent(returnToPlannerPendingIntent);

                notificationManager.notify(100, builder.build());
            }else{
                notificationManager.cancel(100); // dismiss the notification , no tasks are due
            }
        }
    }

    private void generateNotification(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");
        plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long myPlannerItemsTotal = 0;
                for (DataSnapshot singleSnapShot : snapshot.getChildren()) {
                    PlannerContent.PlannerItem item = singleSnapShot.getValue(PlannerContent.PlannerItem.class);
                    Date today = new Date();
                    Date itemDate = item.getDueDate();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                    if (fmt.format(today).equals(fmt.format(itemDate))){
                        myPlannerItemsTotal++;
                    }
                }

                createNotificationChannel(myPlannerItemsTotal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}