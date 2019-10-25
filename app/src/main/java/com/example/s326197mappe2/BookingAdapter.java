package com.example.s326197mappe2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        TextView restaurant_name = (TextView) convertView.findViewById(R.id.name);
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
                            case R.id.item_edit: Log.i ("BookingFragment", "edit"); break;
                            case R.id.item_delete: ;

                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.pop_up_menu);
                menu.show();
            }
        });

        restaurant_name.setText(booking.getRestaurant().getName());
        date.setText(booking.getDate().toString());
        amount_of_friends.setText(booking.getFriendList().size());
        type.setText(String.format("(%s)", booking.getRestaurant().getType()));
        Log.d("RestaurantAdapter", "Booking Restaurant: " + booking.getRestaurant().getName() + ", Booking Type: " + booking.getRestaurant().getType());

        Log.d("BookingAdapter", "Amount of friends: " + booking.getFriendList().size());
        return convertView;
    }

//    private void deleteBooking(Booking booking){
//        dbHandler.deleteBooking(booking.getId());
//        loadLists();
//    }

    public void loadLists(){
        ArrayList<Booking> bookingList = new ArrayList<>(dbHandler.findAllRestaurants());
        clear();
        addAll(bookingList);
        notifyDataSetChanged();
    }


}
