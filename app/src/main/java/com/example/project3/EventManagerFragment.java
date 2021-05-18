package com.example.project3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventManagerFragment extends Fragment {

    public static final String TAG = "TAG";
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    List<String> eventList = new ArrayList<>();
    EventManagerAdapter adapter;
    Button addEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillEventList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_manager, container, false);

        addEvent = root.findViewById(R.id.event_manager_add_btn);

        EventManagerAdapter.OnEventClickListener eventClickListener = new EventManagerAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(String event, int position) {
                Toast.makeText(getActivity(), "Был выбран пункт " + event, Toast.LENGTH_SHORT).show();
            }
        };

        RecyclerView recyclerView = root.findViewById(R.id.event_manager_list);
        adapter = new EventManagerAdapter(getActivity(), eventList, eventClickListener);
        recyclerView.setAdapter(adapter);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventCreatorFragment eventCreatorFragment = new EventCreatorFragment();
                FragmentManager fm = getFragmentManager();
                Toast.makeText(getActivity(), "SUDA", Toast.LENGTH_SHORT).show();
                fm.beginTransaction().replace(R.id.main_container, eventCreatorFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }

    public void fillEventList() {
        if (eventList.isEmpty()) {
            DocumentReference docRef = fStore.collection("events").document("future");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> documentData = document.getData();
                            Set<String> setKeys = documentData.keySet();
                            eventList.addAll(setKeys);
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "DocumentSnapshot data: " + eventList.toString());
                        } else {
                            Log.d(TAG, "No such document");
                            Toast.makeText(getActivity(), "No personList found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
}