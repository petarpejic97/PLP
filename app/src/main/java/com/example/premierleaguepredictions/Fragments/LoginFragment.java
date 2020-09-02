package com.example.premierleaguepredictions.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleaguepredictions.R;
import com.example.premierleaguepredictions.Registration;
import com.example.premierleaguepredictions.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {

    private ProgressDialog progressDialog;
    private BottomNavigationView bottomNavigationView;

    private EditText edEmail;
    private EditText edPassword;
    private Button btnLogin;
    private TextView tvRegistration;

    private FirebaseAuth firebaseAuth;

    CommentsFragment commentsFragment;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        commentsFragment = new CommentsFragment();
        edEmail = view.findViewById(R.id.edEmail);
        edPassword = view.findViewById(R.id.edPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegistration=view.findViewById(R.id.tvRegistration);

        bottomNavigationView=view.findViewById(R.id.nav_view);

        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        onTvRegistration();
        buttonClick();
        return view;
    }

    private void buttonClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void onTvRegistration() {
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Registration.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {

        final String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (edEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Email isn't entered.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(edPassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password isn't entered.",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Waiting...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("flag","true").apply();
                                    editor.putString("loggedEmail",email).apply();
                                    findUserInDatabase();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Please verify your email address.",
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                            else{
                                Toast.makeText(getActivity(),"Authentification failed",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }

    }

    private void resetFields() {
        edEmail.setText("");
        edPassword.setText("");
    }

    private void findUserInDatabase() {

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);

        Query nickname = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("email")
                .equalTo(sharedPreferences.getString("loggedEmail","defEmail"));

        nickname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                        setFragment(commentsFragment, user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setFragment(Fragment fragment,User user) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame,fragment)
                .addToBackStack(null)
                .commit();

        bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);


        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Bundle bundle = new Bundle();
        bundle.putSerializable("loggedUser",user);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        progressDialog.dismiss();

        resetFields();
    }
}
