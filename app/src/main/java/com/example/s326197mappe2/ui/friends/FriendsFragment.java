package com.example.s326197mappe2.ui.friends;

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

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    private DBHandler db;
    private ListView friendListView;
    private FriendAdapter friendAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        db = new DBHandler(this.getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendListView = (ListView) getView().findViewById(R.id.list_view_friends);
        friendListView.setEmptyView(getView().findViewById(R.id.empty));
        ArrayList<Friend> friendList = new ArrayList<>(db.findAllFriends());
        friendAdapter = new FriendAdapter(this.getContext(), 0, friendList);
        friendListView.setAdapter(friendAdapter);
        friendAdapter.loadLists();
    }

    @Override
    public void onResume() {
        super.onResume();
        friendAdapter.loadLists();
    }





}