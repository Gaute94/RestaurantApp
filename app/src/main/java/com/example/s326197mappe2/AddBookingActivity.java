package com.example.s326197mappe2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddBookingActivity extends AppCompatActivity implements
        View.OnClickListener{

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Restaurant restaurant = new Restaurant();

    private RestaurantDialog restaurantDialog;

    private FriendsDialog friendsDialog;

    private List<Friend> friends = new ArrayList<>();

    private TextView friendNum;

    private Date date;

    private TextView restaurantText;

    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        btnDatePicker=(Button)findViewById(R.id.date_button);
        btnTimePicker=(Button)findViewById(R.id.time_button);
        txtDate=(EditText)findViewById(R.id.date);
        txtTime=(EditText)findViewById(R.id.time);
        dbHandler = new DBHandler(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        String bookingId = getIntent().getStringExtra("BookingID");
        if(bookingId != null){


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            date = dbHandler.getBooking(Long.parseLong(bookingId)).getDate();

            Log.d("BookingAdapter", "DATE! Format: " + dateFormat.format(date));
            String dateString = dateFormat.format(date);
            String[] parts = dateString.split(" ", 2);

            txtDate.setText(parts[0]);
            txtTime.setText(parts[1]);

            restaurant = dbHandler.getRestaurant(Long.parseLong(bookingId));
            friends = dbHandler.getBooking(Long.parseLong(bookingId)).getFriendList();
            restaurantText = (TextView)findViewById(R.id.chosen_restaurant);
            friendNum = (TextView)findViewById(R.id.chosen_friends);
            restaurantText.setText(restaurant.getName());
            friendNum.setText(String.valueOf(friends.size()));

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void addBooking(View view){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mMonth);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);

        Booking booking = new Booking(restaurant,friends, calendar.getTime());
        dbHandler.addBooking(booking);

        finish();
    }

    // Hentet fra https://www.journaldev.com/9976/android-date-time-picker-dialog
    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void selectRestaurant(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        restaurantDialog = new RestaurantDialog(new RestaurantDialog.RestaurantDialogCallback() {
            @Override
            public void onFinish(Restaurant restaurant) {
                AddBookingActivity.this.restaurant = restaurant;
                setValues();
            }
        });
        restaurantDialog.show(fragmentManager, "tagSeleccion");
    }

    public void selectFriends(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        friendsDialog = new FriendsDialog(new FriendsDialog.FriendsDialogCallback() {
            @Override
            public void onFinish(List<Friend> friends) {
                AddBookingActivity.this.friends = friends;
                setValues();
            }
        });
        friendsDialog.show(fragmentManager, "tagSeleccion");
    }



    public void setValues(){
        friendNum = (TextView)findViewById(R.id.chosen_friends);
        restaurantText = (TextView)findViewById(R.id.chosen_restaurant);
        String friendStr = "Antall venner valgt: " + friends.size();
        friendNum.setText(friendStr);
        String restaurantStr = "Restaurant: " + restaurant.getName();
        restaurantText.setText(restaurantStr);
    }

    public void editRestaurant(View view){

        Booking booking = new Booking(restaurant,friends, date);

        dbHandler.editBooking(booking);
        finish();
        Log.d("AddRestaurantActivity", "Edited restaurant: " + restaurant);
    }
}
