package com.example.project3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EventInfoFragment extends Fragment {

    public static final String TAG = "TAG";
    Button iWillGo;
    Button iWontGo;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore;
    TextView name;
    TextView description;
    TextView place;
    TextView cost;
    HashMap<String, Object> eventInfo = new HashMap<>();
    String userID;
    String fullName;
    boolean personContains;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_info, container, false);

        iWillGo = root.findViewById(R.id.i_will_go);
        iWontGo = root.findViewById(R.id.i_wont_go);
        name = root.findViewById(R.id.event_name);
        description = root.findViewById(R.id.event_describtion);
        place = root.findViewById(R.id.event_place);
        cost = root.findViewById(R.id.event_cost);
        fStore = FirebaseFirestore.getInstance();



        if (isPersonListContains(getUserName())) {
            Toast.makeText(getActivity(), "Contains", Toast.LENGTH_SHORT).show();
            iWontGo.setVisibility(View.VISIBLE);
            iWillGo.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(getActivity(), "Not Contains", Toast.LENGTH_SHORT).show();
            iWontGo.setVisibility(View.INVISIBLE);
            iWillGo.setVisibility(View.VISIBLE);
        }


        //fillEventInfo();

        DocumentReference docRef = fStore.collection("event").document("info");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        eventInfo.put("Имя", String.valueOf(documentData.get("Имя")));
                        eventInfo.put("Описание", String.valueOf(documentData.get("Описание")));
                        eventInfo.put("Место", String.valueOf(documentData.get("Место")));
                        eventInfo.put("Стоимость", String.valueOf(documentData.get("Стоимость")));

                        name.setText(String.valueOf(eventInfo.get("Имя")));
                        description.setText(String.valueOf(eventInfo.get("Описание")));
                        place.setText(String.valueOf(eventInfo.get("Место")));
                        cost.setText(String.valueOf(eventInfo.get("Стоимость")));
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No personList found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });




        // ЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭэ
        // ЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭэ
        // ЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭЭэ

//        ProfileFragment profileFragment = new ProfileFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("fullName", fullName.getText().toString());
//        bundle.putString("email",email.getText().toString());
//        bundle.putString("phone",phone.getText().toString());
//        profileFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.main_container, profileFragment).addToBackStack(null).commit();


        iWillGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iWillGo.setVisibility(View.INVISIBLE);
                iWontGo.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), getUserName() + ", You will go!", Toast.LENGTH_SHORT).show();

//                DocumentReference documentReference = fStore.collection("event").document("people");
//                Map<String, Object> person = new HashMap<>();
//                person.put("fName", fullName);
//                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: " + e.toString());
//                    }
//                });
            }
        });


        return root;
    }


    public String getUserName() {

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference docRef = fStore.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        fullName = String.valueOf(documentData.get("fName"));
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No personList found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return fullName;
    }

    public boolean isPersonListContains(String fullName) {

        DocumentReference docRef = fStore.collection("event").document("people");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> documentData = document.getData();

                        personContains = documentData.containsKey(fullName);
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No personList found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return personContains;
    }

    //public void fillEventInfo(){}
}