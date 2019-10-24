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

public class FriendAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private List<Friend> friendList;
    private DBHandler dbHandler;

    public FriendAdapter(Context context, int resource, ArrayList<Friend> friends){
        super(context, resource, friends);
        this.context = context;
        this.friendList = friends;
        dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Friend friend = friendList.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView telephone = (TextView) convertView.findViewById(R.id.telephone);

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
                            case R.id.item_edit: Log.i ("FriendsFragment", "edit"); break;
                            case R.id.item_delete: deleteFriend(friend);

                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.pop_up_menu);
                menu.show();
            }
        });

        name.setText(friend.getName());
        telephone.setText(friend.getTelephone());

        return convertView;
    }

    private void deleteFriend(Friend friend){
        dbHandler.delete(friend.getId());
        loadLists();
    }

    public void loadLists(){
        ArrayList<Friend> friendList = new ArrayList<>(dbHandler.findAllFriends());
        clear();
        addAll(friendList);
        notifyDataSetChanged();
    }


}
