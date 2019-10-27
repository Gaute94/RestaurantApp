package com.example.s326197mappe2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendsDialog extends DialogFragment {

    public interface FriendsDialogCallback {
        void onFinish(List<Friend> friends);
    }

    private FriendsDialogCallback friendsDialogCallback;

    public FriendsDialog(FriendsDialogCallback friendsDialogCallback) {
        this.friendsDialogCallback = friendsDialogCallback;
    }

    private DBHandler dbHandler;
    private List<Friend> friends;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dbHandler = new DBHandler(this.getContext());

        friends = new ArrayList<>();
        final List<String> stringList = new ArrayList<>();

        List<Friend> allFriends = dbHandler.findAllFriends();
        for(Friend friend : allFriends){
            stringList.add(friend.getName());
        }

        final Friend[] friendsArray = allFriends.toArray(new Friend[0]);
        final String[] items = stringList.toArray(new String[0]);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Friends")
                .setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                if(isChecked) {
                                    Log.i("Friend Dialog", "Selected: " + items[item]);
                                    friends.add(friendsArray[item]);
                                    Log.i("Friend Dialog", "Added a friend. Selected Friends: " + friends);
                                }else {
                                    Log.i("Friend Dialog", "Unselected: " + items[item]);
                                    friends.remove(friendsArray[item]);
                                    Log.i("Friend Dialog", "Removed a friend. Selected Friends: " + friends);
                                }
                            }
                        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        friendsDialogCallback.onFinish(friends);
        super.onCancel(dialog);
    }
}