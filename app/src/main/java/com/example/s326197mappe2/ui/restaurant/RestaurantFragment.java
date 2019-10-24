package com.example.s326197mappe2.ui.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.s326197mappe2.DBHandler;
import com.example.s326197mappe2.Friend;
import com.example.s326197mappe2.FriendAdapter;
import com.example.s326197mappe2.R;
import com.example.s326197mappe2.Restaurant;
import com.example.s326197mappe2.RestaurantAdapter;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    private DBHandler db;
    private ListView restaurantListView;
    private RestaurantAdapter restaurantAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = new DBHandler(this.getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantListView = (ListView) getView().findViewById(R.id.list_view_restaurants);
        ArrayList<Restaurant> restaurantList = new ArrayList<>(db.findAllRestaurants());
        restaurantAdapter = new RestaurantAdapter(this.getContext(), 0, restaurantList);
        restaurantListView.setAdapter(restaurantAdapter);
        restaurantAdapter.loadLists();
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurantAdapter.loadLists();
    }





}