package com.example.s326197mappe2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddFriendActivity extends AppCompatActivity {

    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        Log.d("AddFriendActivity", "Created AddFriendActivity");
        dbHandler = new DBHandler(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void addFriend(View view){
        EditText firstName = (EditText)findViewById(R.id.first_name);
        EditText lastName = (EditText)findViewById(R.id.last_name);
        EditText telephone = (EditText)findViewById(R.id.telephone);

        String name = firstName.getText().toString() + " " + lastName.getText().toString();
        String telephoneString = telephone.getText().toString();

        Friend friend = new Friend(name,telephoneString);
        dbHandler.addFriend(friend);

        finish();
        Log.d("AddFriendActivity", "Added friend: " + friend);
    }

}