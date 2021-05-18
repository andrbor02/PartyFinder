package com.example.project3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class BottomNavigationFragment extends Fragment {

    ImageButton buttonEventInfo;
    ImageButton buttonPersonList;
    ImageButton buttonShoppingList;
    ImageButton buttonGeoLocation;
    ImageButton buttonDebtSplitter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        buttonEventInfo = root.findViewById(R.id.btEI);
        buttonPersonList = root.findViewById(R.id.btPL);
        buttonShoppingList = root.findViewById(R.id.btSL);
        buttonGeoLocation = root.findViewById(R.id.btGL);
        buttonDebtSplitter = root.findViewById(R.id.btDS);

        EventInfoFragment eventInfo = new EventInfoFragment();
        PersonListFragment personList = new PersonListFragment();
        ShoppingListFragment shoppingList = new ShoppingListFragment();
        GeoLocationFragment geoLocation = new GeoLocationFragment();
        DebtSplitterFragment debtSplitter = new DebtSplitterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.bn_container, eventInfo).commit();

        buttonEventInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.bn_container, eventInfo).commit();
            }
        });

        buttonPersonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.bn_container, personList).commit();
            }
        });

        buttonShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.bn_container, shoppingList).commit();
            }
        });

        buttonGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.bn_container, geoLocation).commit();
            }
        });

        buttonDebtSplitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.bn_container, debtSplitter).commit();
            }
        });

        return root;
    }
}