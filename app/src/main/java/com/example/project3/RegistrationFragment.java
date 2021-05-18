package com.example.project3;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationFragment extends Fragment {
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        mFullName = root.findViewById(R.id.fullName);
        mEmail = root.findViewById(R.id.Email);
        mPassword = root.findViewById(R.id.password);
        mPhone = root.findViewById(R.id.phone);
        mRegisterBtn = root.findViewById(R.id.registerBtn);
        mLoginBtn = root.findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = root.findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {
//            BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
//            FragmentManager fm = getFragmentManager();
//            Toast.makeText(getActivity(), "Already enetered", Toast.LENGTH_SHORT).show();
//            fm.beginTransaction().replace(R.id.main_container, bottomNavigationFragment).commit();

//            EventManagerFragment eventManagerFragment = new EventManagerFragment();
//            FragmentManager fm = getFragmentManager();
//            Toast.makeText(getActivity(), "Already enetered", Toast.LENGTH_SHORT).show();
//            fm.beginTransaction().replace(R.id.main_container, eventManagerFragment).commit();

            MainMenuFragment mainMenuFragment = new MainMenuFragment();
            FragmentManager fm = getFragmentManager();
            Toast.makeText(getActivity(), "Already enetered", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.main_container, mainMenuFragment).commit();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getActivity(), "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            MainFragment mainFragment = new MainFragment();
                            FragmentManager fm = getFragmentManager();
                            fm.beginTransaction().replace(R.id.main_container, mainFragment).commit();

                        } else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.main_container, loginFragment).commit();
            }
        });


        return root;
    }
}