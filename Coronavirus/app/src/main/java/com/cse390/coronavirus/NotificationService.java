package com.cse390.coronavirus;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.PendingIntent;
import android.app.Service ;
import android.app.TaskStackBuilder;
import android.content.Intent ;
import android.os.Handler ;
import android.os.IBinder ;
import android.util.Log ;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer ;
import java.util.TimerTask ;

public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
        return START_STICKY ;
    }

    @Override
    public void onDestroy () {

        stopTimerTask() ;
        super.onDestroy() ;
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        // 36000000
        timer.schedule( timerTask , 5000 , Your_X_SECS * 1000 ) ;
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {

                        alertUserOfDueItems();
                    }
                }) ;
            }
        } ;
    }
    private void createNotification (long numberOfItemsDueToday) {
        String notificationContentText;
        switch ( (int) numberOfItemsDueToday ){
            case 1:
                notificationContentText = "You Are Doing Amazing! Only " + String.valueOf(numberOfItemsDueToday) + " Item Due Today!";
                break;
            default:
                notificationContentText = "Keep Going! You have " + String.valueOf(numberOfItemsDueToday) + " Items Due Today!";
                break;
        }

        if (numberOfItemsDueToday > 0){
            NotificationManager mNotificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE ) ;
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
            mBuilder.setContentTitle( "To Cheer You On!" ) ;
            mBuilder.setContentText(notificationContentText) ;
            mBuilder.setTicker( "Notification Listener Service Example" ) ;
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground ) ;
            mBuilder.setAutoCancel( true ) ;

            Intent returnToPlannerIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(returnToPlannerIntent);
            PendingIntent returnToPlannerPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(returnToPlannerPendingIntent);

            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel) ;
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        }
    }

    private void alertUserOfDueItems(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });

        try {
            currentUserID = mAuth.getCurrentUser().getUid();
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
                        createNotification(myPlannerItemsTotal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }
    }
}
