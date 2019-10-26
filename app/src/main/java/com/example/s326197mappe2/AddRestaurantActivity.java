package com.example.s326197mappe2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddRestaurantActivity extends AppCompatActivity {

    DBHandler dbHandler;
    Restaurant restaurant;
    EditText name;
    EditText telephone;
    EditText address;
    EditText type;
    Button addRestaurantButton;
    Button editRestaurantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        dbHandler = new DBHandler(this);
        addRestaurantButton = (Button)findViewById(R.id.add_restaurant_button);
        editRestaurantButton = (Button)findViewById(R.id.edit_restaurant_button);
        editRestaurantButton.setVisibility(View.GONE);
        name = (EditText)findViewById(R.id.name);
        address = (EditText)findViewById(R.id.address);
        telephone = (EditText)findViewById(R.id.telephone);
        type = (EditText)findViewById(R.id.type);
        String restaurantId = getIntent().getStringExtra("RestaurantID");
        if(restaurantId != null){
            addRestaurantButton.setVisibility(View.GONE);
            editRestaurantButton.setVisibility(View.VISIBLE);
            restaurant = dbHandler.getRestaurant(Long.parseLong(restaurantId));
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            telephone.setText(restaurant.getTelephone());
            type.setText(restaurant.getType());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void addRestaurant(View view){
        String restaurantName = name.getText().toString();
        String telephoneString = telephone.getText().toString();
        String addressString = address.getText().toString();
        String typeString = type.getText().toString();

        Restaurant restaurant = new Restaurant(restaurantName, addressString, telephoneString, typeString);
        dbHandler.addRestaurant(restaurant);

        finish();
        Log.d("AddRestaurantActivity", "Added restaurant: " + restaurant);
    }

    public void editRestaurant(View view){
        String restaurantName = name.getText().toString();
        String telephoneString = telephone.getText().toString();
        String addressString = address.getText().toString();
        String typeString = type.getText().toString();
        restaurant.setName(restaurantName);
        restaurant.setTelephone(telephoneString);
        restaurant.setAddress(addressString);
        restaurant.setType(typeString);
        dbHandler.editRestaurant(restaurant);
        finish();
        Log.d("AddRestaurantActivity", "Edited restaurant: " + restaurant);
    }
}
