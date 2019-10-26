package com.example.s326197mappe2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.s326197mappe2.Booking;
import com.example.s326197mappe2.BookingAdapter;
import com.example.s326197mappe2.DBHandler;
import com.example.s326197mappe2.Friend;
import com.example.s326197mappe2.FriendAdapter;
import com.example.s326197mappe2.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

//    public interface HomeFragmentListener{
//
//        void onButtonClick();
//}
//
//    HomeFragmentListener homeFragmentListener;

    private DBHandler db;
    Button addOrderFragment;
    private ListView bookingListView;

    BookingAdapter bookingAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("HomeFragment", "Created HomeFragment");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addOrderFragment = (Button) root.findViewById(R.id.add_order_button);
        db = new DBHandler(this.getContext());
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingListView = (ListView) getView().findViewById(R.id.list_view_1);
        ArrayList<Booking> bookingList = new ArrayList<>(db.findAllBookings());
        bookingAdapter = new BookingAdapter(this.getContext(), 0, bookingList);
        bookingListView.setAdapter(bookingAdapter);
        bookingAdapter.loadLists();
    }
    @Override
    public void onResume() {
        super.onResume();
        bookingAdapter.loadLists();
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof HomeFragmentListener){
//            homeFragmentListener = (HomeFragmentListener) context;
//        }else{
//            throw new RuntimeException(context.toString() + " must implement HomeFragmentListener");
//        }
//
//    }
}