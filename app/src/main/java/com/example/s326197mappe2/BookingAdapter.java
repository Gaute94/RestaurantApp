package com.example.s326197mappe2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends ArrayAdapter<Booking> {

    private Context context;
    private List<Booking> bookingList;
    private DBHandler dbHandler;

    public BookingAdapter(Context context, int resource, ArrayList<Booking> bookings){
        super(context, resource, bookings);
        this.context = context;
        this.bookingList = bookings;
        dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Booking booking = bookingList.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.booking_list_item, null);
        }

        TextView restaurant_name = (TextView) convertView.findViewById(R.id.restaurant_name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView amount_of_friends = (TextView) convertView.findViewById(R.id.amount_of_friends);
        TextView type = (TextView) convertView.findViewById(R.id.type);

        Log.d("RestaurantAdapter:", "TypeText: " + type.getText());
        Button button = (Button) convertView.findViewById(R.id.delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu (context, view);
                menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
                {
                    @Override
                    public boolean onMenuItemClick (MenuItem item)
                    {
                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.item_edit: editBooking(booking); break;
                            case R.id.item_delete: deleteBooking(booking);

                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.pop_up_menu);
                menu.show();
            }
        });

        Log.d("BookingAdapter", "getView, booking: " + booking);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

        Log.d("BookingAdapter", "DATE: " + booking.getDate());
        Log.d("BookingAdapter", "FORMATTED DATE: " + dateFormat.format(booking.getDate()));
        if(booking.getRestaurant()!=null) {
            restaurant_name.setText(booking.getRestaurant().getName());
            type.setText(String.format("(%s)", booking.getRestaurant().getType()));
            Log.d("BookingAdapter", "Booking Restaurant: " + booking.getRestaurant().getName() + ", Booking Type: " + booking.getRestaurant().getType());

        }
        date.setText(dateFormat.format(booking.getDate()));
        String str = "Antall venner: " + booking.getFriendList().size();

        amount_of_friends.setText(str);


        Log.d("BookingAdapter", "Amount of friends: " + booking.getFriendList().size());
        return convertView;
    }


    private void deleteBooking(Booking booking){
        dbHandler.deleteBooking(booking.getId());
        loadLists();
    }

    private void editBooking(Booking booking){
        Intent intent = new Intent(context, AddBookingActivity.class);
        intent.putExtra("BookingID", Long.toString(booking.getId()));
        context.startActivity(intent);
    }

    public void loadLists(){
        Log.d("BookingAdapter", "Inside loadList");

        ArrayList<Booking> bookingList = new ArrayList<>(dbHandler.findAllBookings());
//        for(Booking booking : bookingList){
//            Log.d("BookingAdapter", "Booking restaurant: " + booking.getRestaurant().getName());
//            for(Friend friend : booking.getFriendList()){
//                Log.d("BookingAdapter", "Booking Friends: " + friend.getName());
//            }
//        }
        clear();
        addAll(bookingList);
        notifyDataSetChanged();
    }


}
