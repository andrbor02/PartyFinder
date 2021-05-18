package com.example.project3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingListFragment extends Fragment {

    public static final String TAG = "TAG";
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    List<ItemOfList> shoppingList = new ArrayList<>();
    SimpleListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.shopping_list);
        adapter = new SimpleListAdapter(getActivity(), shoppingListCreator());
        recyclerView.setAdapter(adapter);

        return root;
    }


    public List<ItemOfList> shoppingListCreator(){
        if (shoppingList.isEmpty()) {
            DocumentReference docRef = fStore.collection("event").document("products");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> documentData = document.getData();
                            for (Map.Entry<String, Object> elem : documentData.entrySet()) {
                                shoppingList.add(new ItemOfList(elem.getKey(), elem.getValue().toString()));
                            }
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "DocumentSnapshot data: " + shoppingList.toString());
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
        return shoppingList;
    }

}