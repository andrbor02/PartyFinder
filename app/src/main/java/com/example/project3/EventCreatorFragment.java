package com.example.project3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EventCreatorFragment extends Fragment {

    public static final String TAG = "TAG";
    EditText eName, ePlace, eDescription, eCost;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Button createNewEvent;
    TextView backToEventList;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_creator, container, false);

        backToEventList = root.findViewById(R.id.edit_event_info_back);
        eName = root.findViewById(R.id.event_creator_name);
        ePlace = root.findViewById(R.id.event_creator_place);
        eDescription = root.findViewById(R.id.edit_event_info_description);
        eCost = root.findViewById(R.id.event_creator_cost);
        createNewEvent = root.findViewById(R.id.event_creator_createbtn);
        progressBar = root.findViewById(R.id.edit_event_info_progressBar);



        createNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = eName.getText().toString().trim();
                final String place = ePlace.getText().toString().trim();
                final String description = eDescription.getText().toString();
                final String cost = eCost.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    eName.setError("Name is Required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // update events
                DocumentReference futureEvents = fStore.collection("events").document("future");
                Map<String, Object> eventInEvents = new HashMap<>();
                eventInEvents.put(name, "");
                futureEvents.update(eventInEvents).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: new Event " + name + " is created");
                        progressBar.setVisibility(View.GONE);
                        EventManagerFragment eventManagerFragment = new EventManagerFragment();
                        FragmentManager fm = getFragmentManager();
                        Toast.makeText(getActivity(), "refresh complete", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction().replace(R.id.main_container, eventManagerFragment).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });


                //update single event info
                DocumentReference documentReference = fStore.collection(name).document("info");
                Map<String, Object> event = new HashMap<>();
                event.put("Имя", name);
                event.put("Место", place);
                event.put("Описание", description);
                event.put("Стоимость", cost);
                documentReference.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: " + name + " info was updated");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });


            }
        });



        backToEventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                EventManagerFragment eventManagerFragment = new EventManagerFragment();
                FragmentManager fm = getFragmentManager();
                Toast.makeText(getActivity(), "refresh complete", Toast.LENGTH_SHORT).show();
                fm.beginTransaction().replace(R.id.main_container, eventManagerFragment).commit();
            }
        });


        return root;
    }
}