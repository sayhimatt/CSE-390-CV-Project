package com.cse390.coronavirus;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.PendingIntent;
import android.app.Service ;
import android.app.TaskStackBuilder;
import android.content.Intent ;
import android.os.Handler ;
import android.os.IBinder ;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.cse390.coronavirus.DatabaseHelper.PlannerContent;
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

/**
 * Notification service that gets set in the background to remind the user about plans due today
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;
    private FirebaseAuth mAuth;
    private String currentUserID;

    /**
     * @param arg0
     * @return
     */
    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }

    /**
     * the start to get the timer and notifications going
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
        return START_STICKY ;
    }

    /**
     * destroys the notifications timer and schedule
     */
    @Override
    public void onDestroy () {

        stopTimerTask() ;
        super.onDestroy() ;
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;

    /**
     * the notifications go on every hour (delay is set on that schedule)
     */
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        // 36000000
        timer.schedule( timerTask , 3600000  , Your_X_SECS * 1000 ) ;
    }

    /**
     * destroys the timer task
     */
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }

    /**
     * initializes the timer task to alert the user
     */
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

    /**
     * Creates the notification to tell the end user based on the number of plans that have been assigned on the current date
     * Then informs the user in a push notification as long as the count is 1 or more
     * @param numberOfItemsDueToday
     */
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

    /**
     *  Pulls the signed in user due items and checks their dates in correlation with the current date
     *  If they match the current date it is added to the count and at the end createNotification is called with that count
     */
    private void alertUserOfDueItems(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            /**
             * @param firebaseAuth
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });

        try {
            currentUserID = mAuth.getCurrentUser().getUid();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference plannerItems = database.child("Users").child(currentUserID).child("PlannerItems");

            plannerItems.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Goes through the list of plans and calls createNotification on any change
                 * @param snapshot
                 */
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

                /**
                 * @param error
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }
    }
}
