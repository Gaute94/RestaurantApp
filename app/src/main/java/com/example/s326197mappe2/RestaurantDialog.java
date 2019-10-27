package com.example.s326197mappe2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDialog extends DialogFragment {

    public interface RestaurantDialogCallback {
        void onFinish(Restaurant restaurant);
    }

    private RestaurantDialogCallback restaurantDialogCallback;

    public RestaurantDialog(RestaurantDialogCallback restaurantDialogCallback) {
        this.restaurantDialogCallback = restaurantDialogCallback;
    }

    private DBHandler dbHandler;
    private Restaurant restaurant;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dbHandler = new DBHandler(this.getContext());

        restaurant = new Restaurant();
        final List<String> stringList = new ArrayList<>();

        List<Restaurant> allRestaurants = dbHandler.findAllRestaurants();
        for(Restaurant restaurant : allRestaurants){
            stringList.add(restaurant.getName());
        }

        final Restaurant[] restaurantArray = allRestaurants.toArray(new Restaurant[0]);
        final String[] items = stringList.toArray(new String[0]);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Restaurant")
                .setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                restaurant = restaurantArray[item];
                                Log.i("Restaurant Dialog", "Selected Restaurant: " + items[item]);
                            }

                        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        restaurantDialogCallback.onFinish(restaurant);
        super.onCancel(dialog);
    }
}