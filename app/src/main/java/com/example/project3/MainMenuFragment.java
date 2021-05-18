package com.example.project3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuFragment extends Fragment {

    Button events;
    Button myProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        events = root.findViewById(R.id.main_menu_btn_events);
        myProfile = root.findViewById(R.id.main_menu_btn_my_profile);

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManagerFragment eventManagerFragment = new EventManagerFragment();
                FragmentManager fm = getFragmentManager();
                Toast.makeText(getActivity(), "Choose your event", Toast.LENGTH_SHORT).show();
                fm.beginTransaction().replace(R.id.main_container, eventManagerFragment).addToBackStack(null).commit();
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                FragmentManager fm = getFragmentManager();
                Toast.makeText(getActivity(), "Change your profile", Toast.LENGTH_SHORT).show();
                fm.beginTransaction().replace(R.id.main_container, mainFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }
}