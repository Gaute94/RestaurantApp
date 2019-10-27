package com.example.s326197mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    protected int currentDestination;
    private DBHandler db;


//    @Override
//    public void onButtonClick() {
//        Log.d("MainActivity", "Er i onButtonClick");
//        for(Friend friend : db.findAllFriends()){
//            Log.d("MainActivity: ", "Friend: " + friend.getName());
//        }
////        loadFragment(new AddOrderFragment());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "Created MainActivity");
        db = new DBHandler(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_friends)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Log.d("MainActivity:", "IDs: " + R.id.navigation_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d("MainActivity", "Created onDestinationChangedListener");
                Log.d("MainActivity", "CurrentDestination ID: " + navController.getCurrentDestination().getId());
                MainActivity.this.currentDestination = navController.getCurrentDestination().getId();
                Log.d("MainActivity", "Current Destination: " + navController.getCurrentDestination().getLabel().toString());
            }
        });
        startService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            addNewActivity();
        }

        if (id == R.id.settings_button){
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewActivity(){
        switch (currentDestination){
            case R.id.navigation_home: startNewActivity(AddBookingActivity.class);
                break;
            case R.id.navigation_friends: startNewActivity(AddFriendActivity.class);
                break;
            case R.id.navigation_dashboard: startNewActivity(AddRestaurantActivity.class);
        }
    }

    private void getCurrentDestinationName(){

        switch (currentDestination){
            case R.id.navigation_home: Log.d("MainActivity", "Current Destination: Home");
                break;
            case R.id.navigation_friends: Log.d("MainActivity", "Current Destination: Friends");
                break;
            case R.id.navigation_dashboard: Log.d("MainActivity", "Current Destination: Restaurant");
        }
    }

    private void startNewActivity(Class myclass){
        Intent myIntent = new Intent(MainActivity.this, myclass);
        MainActivity.this.startActivity(myIntent);
    }




    private void loadFragment(Fragment fragment){
        Log.d("MainActivity", "Inside loadFragment");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_up_in, R.anim.slide_up_out);
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void startService() {
//        Intent intent= new Intent(this, NotificationService.class);
//        this.startService(intent);
        Intent intent = new Intent();
        intent.setAction("com.example.s326197mappe2.mittbroadcast");
        sendBroadcast(intent);
    }


    public void stoppPeriodisk() {
        Intent i = new Intent(this, NotificationService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm!= null) {
            alarm.cancel(pintent);
        }
    }

}
