package com.example.premierleaguepredictions.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleaguepredictions.R;
import com.google.firebase.auth.FirebaseAuth;

public class AlreadyLoggedFragment extends Fragment {

    private TextView tvHelloUser;
    private Button btnLogOut;


    public AlreadyLoggedFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_already_logged, container, false);

        tvHelloUser = view.findViewById(R.id.tvHelloUser);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        initializeMessage();
        clickLogOut();
        return view;
    }

    private void clickLogOut() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });
    }

    private void logOutUser() {
        FirebaseAuth.getInstance().signOut();
        resetLocalStorage();
    }

    private void resetLocalStorage() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loggedUser",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fullname","").apply();
        editor.putString("email","").apply();
        editor.putString("nickname","").apply();
        editor.putString("imageURL","").apply();

        sharedPreferences=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("flag","false").apply();

        startLoginFragment();
    }

    private void startLoginFragment() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loggedUser",Context.MODE_PRIVATE);
        Toast.makeText(getActivity(), "nickname is "+sharedPreferences.getString("nickname","DEFVALUE"),
                Toast.LENGTH_LONG).show();
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame,loginFragment)
                .addToBackStack(null)
                .commit();


        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,loginFragment);
        fragmentTransaction.commit();
    }

    private void initializeMessage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loggedUser", Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname","defaultnavrijednost");

        String message = "Hello ";
        message+=nickname;
        message+=", you already logged.";

        tvHelloUser.setText(message);
    }

}
