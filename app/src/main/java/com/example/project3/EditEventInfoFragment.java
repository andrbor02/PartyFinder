package com.example.project3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditEventInfoFragment extends Fragment {

    public static final String TAG = "TAG";
    TextView eventName;
    EditText eventDescription, eventPlace, eventCost;
    Button updateBtn;
    TextView backToEventInfo;
    ProgressBar progressBar;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_event_info, container, false);

        eventName = root.findViewById(R.id.edit_event_info_name);
        eventDescription = root.findViewById(R.id.edit_event_info_description);
        eventPlace = root.findViewById(R.id.edit_event_info_place);
        eventCost = root.findViewById(R.id.edit_event_info_cost);
        updateBtn = root.findViewById(R.id.edit_event_info_btn_update);
        backToEventInfo = root.findViewById(R.id.edit_event_info_back);
        progressBar = root.findViewById(R.id.edit_event_info_progressBar);


        String name = "bundle error";
        String description = "bundle error";
        String place = "bundle error";
        String cost = "bundle error";

        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            description = bundle.getString("description");
            place = bundle.getString("place");
            cost = bundle.getString("cost");
        }

        eventName.setText(name);
        eventDescription.setText(description);
        eventPlace.setText(place);
        eventCost.setText(cost);


//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (eventName.getText().toString().isEmpty() || eventDescription.getText().toString().isEmpty() || eventPlace.getText().toString().isEmpty()) {
//                    Toast.makeText(getActivity(), "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (eventCost.getText().toString().isEmpty()) {
//                    eventCost.setText("не указано");
//                }
////Todo
//                DocumentReference docRef = fStore.collection("users").document(user.getUid());
//                Map<String, Object> edited = new HashMap<>();
//                edited.put("description", eventDescription.getText().toString());
//                edited.put("place", eventPlace.getText().toString());
//                edited.put("cost", eventCost.getText().toString());
//                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
////                                MainFragment mainFragment = new MainFragment();
////                                FragmentManager fm = getFragmentManager();
////                                fm.beginTransaction().replace(R.id.main_container, mainFragment);
//                        //getActivity().onBackPressed();
//                        Toast.makeText(getActivity(), "Event Updated", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        return root;
    }
}