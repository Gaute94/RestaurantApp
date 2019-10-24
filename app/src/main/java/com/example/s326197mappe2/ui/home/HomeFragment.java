package com.example.s326197mappe2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.s326197mappe2.R;

public class HomeFragment extends Fragment {

    public interface HomeFragmentListener{

        void onButtonClick();
    }

    HomeFragmentListener homeFragmentListener;

    Button addOrderFragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("HomeFragment", "Created HomeFragment");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addOrderFragment = (Button) root.findViewById(R.id.add_order_button);

        addOrderFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // load First Fragment
                homeFragmentListener.onButtonClick();
            }
        });
        return root;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeFragmentListener){
            homeFragmentListener = (HomeFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement HomeFragmentListener");
        }

    }
}