package com.cse390.coronavirus;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.firebase.auth.FirebaseAuth;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main activity normally displayed when a user signs in or is already signed in
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int SIGN_UP_ACTIVITY_CODE = 123;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private Date dateSet;

    /**
     * Initializes the main view if there is a saved user signed in
     * If not the activity transitions over to the sign up activity
     * @param savedInstanceState
     */
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

    /**
     * Notifies the notification service to run in the background for the user
     */
    @Override
    protected void onStop() {
        super.onStop();

        if (currentUserID != null){
            System.out.println("Notify");
            startService( new Intent( this, NotificationService. class )) ;
        }
    }

    /**
     * Notifies the notification service to run in the background for the user
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (currentUserID != null){
            startService( new Intent( this, NotificationService. class )) ;
        }
    }

    /**
     * Inflates the custom account menu bar option (for signing out)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Listener that signs the current user out and returns them to sign in/up activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.sign_out_mi) {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivityForResult(intent, SIGN_UP_ACTIVITY_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Takes in the date passed by the user by the date picker dialog and parses it through as a date it through to be used by the plan dialog
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String input = String.valueOf(dayOfMonth) + "-" + String.valueOf(month+1) + "-" + String.valueOf(year);
        try {
            Date inputDate = format.parse(input);
            setDateSet(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the date set for use in the plan dialog
     * @return
     */
    public Date getDateSet() {
        return dateSet;
    }

    /**
     * Sets the date parsed for use in the plan dialog
     * @param dateSet
     */
    public void setDateSet(Date dateSet) {
        this.dateSet = dateSet;
    }
}