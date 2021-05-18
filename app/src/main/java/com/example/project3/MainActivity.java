package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegistrationFragment registrationFragment = new RegistrationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, registrationFragment).commit();
    }
}