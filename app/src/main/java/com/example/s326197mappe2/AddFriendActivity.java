package com.example.s326197mappe2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddFriendActivity extends AppCompatActivity {

    DBHandler dbHandler;
    Friend friend;
    EditText firstName;
    EditText lastName;
    EditText telephone;
    Button addFriendButton;
    Button editFriendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        Log.d("AddFriendActivity", "Created AddFriendActivity");
        dbHandler = new DBHandler(this);
        addFriendButton = (Button)findViewById(R.id.add_friend_button);
        editFriendButton = (Button)findViewById(R.id.edit_friend_button);
        editFriendButton.setVisibility(View.GONE);
        firstName = (EditText)findViewById(R.id.first_name);
        lastName = (EditText)findViewById(R.id.last_name);
        telephone = (EditText)findViewById(R.id.telephone);
        String friendId = getIntent().getStringExtra("FriendID");
        if(friendId != null){
            addFriendButton.setVisibility(View.GONE);
            editFriendButton.setVisibility(View.VISIBLE);
            friend = dbHandler.findFriend(Long.parseLong(friendId));
            firstName.setText(friend.getFirstName());
            lastName.setText(friend.getLastName());
            telephone.setText(friend.getTelephone());
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

    public void addFriend(View view){
        String name = firstName.getText().toString() + " " + lastName.getText().toString();
        String telephoneString = telephone.getText().toString();

        Friend friend = new Friend(name,telephoneString);
        dbHandler.addFriend(friend);

        finish();
        Log.d("AddFriendActivity", "Added friend: " + friend);
    }

    public void editFriend(View view){
        String name = firstName.getText().toString() + " " + lastName.getText().toString();
        String telephoneString = telephone.getText().toString();
        friend.setName(name);
        friend.setTelephone(telephoneString);
        dbHandler.editFriend(friend);
        finish();
        Log.d("AddFriendActivity", "Edited friend: " + friend);
    }

}