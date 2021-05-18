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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DebtSplitterFragment extends Fragment {

    public static final String TAG = "TAG";
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    List<ItemOfList> peopleList = new ArrayList<>();
    SimpleListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_debt_splitter, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.debt_list);
        adapter = new SimpleListAdapter(getActivity(), peopleListCreator());
        recyclerView.setAdapter(adapter);
        return root;
    }


    public static TreeMap<Integer, String> moneyBringer (TreeMap<Integer, String> peopleChip) {
        int startSum = 0;
        for (Integer key : peopleChip.keySet())
            startSum += key;
        int splittedSum = startSum / peopleChip.size();

        TreeMap<Integer, String> peopleDebt = new TreeMap<>();

        for (Map.Entry entry : peopleChip.entrySet()) {
            peopleDebt.put((int)entry.getKey() - splittedSum, (String)entry.getValue());
        }
        return peopleDebt;
    }

    public List<ItemOfList> peopleListCreator(){
        if (peopleList.isEmpty()) {
            DocumentReference docRef = fStore.collection("event").document("people");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> documentData = document.getData();
                            for (Map.Entry<String, Object> elem : documentData.entrySet()) {
                                peopleList.add(new ItemOfList(elem.getKey(), elem.getValue().toString() + " p"));
                            }
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "DocumentSnapshot data: " + peopleList.toString());
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
        return peopleList;
    }

    public static void debtSplitter(TreeMap<Integer, String> people) {
        TreeMap<Integer, String> peopleDebt = moneyBringer(people);


        HashMap<String, Integer> debt = new HashMap<>();

        while (peopleDebt.size() > 1) {
            int fk = peopleDebt.firstKey();
            int lk = peopleDebt.lastKey();
            String fn = peopleDebt.firstEntry().getValue();
            String ln = peopleDebt.lastEntry().getValue();

            if (fk + lk < 0) {
                debt.put(peopleDebt.pollFirstEntry().getValue() + " to " + peopleDebt.pollLastEntry().getValue(),
                        lk);
                peopleDebt.put(fk + lk, fn);
            } else {
                debt.put(peopleDebt.pollFirstEntry().getValue() + " to " + peopleDebt.pollLastEntry().getValue(),
                        fk * -1);
                peopleDebt.put(fk + lk, ln);
            }
        }
        System.out.println(debt.toString());
    }
}