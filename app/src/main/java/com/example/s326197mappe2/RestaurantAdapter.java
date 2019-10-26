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

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    private List<Restaurant> restaurantList;
    private DBHandler dbHandler;

    public RestaurantAdapter(Context context, int resource, ArrayList<Restaurant> restaurants){
        super(context, resource, restaurants);
        this.context = context;
        this.restaurantList = restaurants;
        dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Restaurant restaurant = restaurantList.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView telephone = (TextView) convertView.findViewById(R.id.telephone);
        TextView address = (TextView) convertView.findViewById(R.id.address);
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
                            case R.id.item_edit: editRestaurant(restaurant); break;
                            case R.id.item_delete: deleteRestaurant(restaurant);

                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.pop_up_menu);
                menu.show();
            }
        });

        name.setText(restaurant.getName());
        telephone.setText(restaurant.getTelephone());
        address.setText(restaurant.getAddress());
        type.setText(String.format("(%s)", restaurant.getType()));
        Log.d("RestaurantAdapter", "Restaurant: " + restaurant.getName() + ", Type: " + restaurant.getType());
        return convertView;
    }

    private void deleteRestaurant(Restaurant restaurant){
        dbHandler.deleteRestaurant(restaurant.getId());
        loadLists();
    }

    private void editRestaurant(Restaurant restaurant){
        Intent intent = new Intent(context, AddRestaurantActivity.class);
        intent.putExtra("RestaurantID", Long.toString(restaurant.getId()));
        context.startActivity(intent);
    }

    public void loadLists(){
        ArrayList<Restaurant> restaurantList = new ArrayList<>(dbHandler.findAllRestaurants());
        clear();
        addAll(restaurantList);
        notifyDataSetChanged();
    }


}
