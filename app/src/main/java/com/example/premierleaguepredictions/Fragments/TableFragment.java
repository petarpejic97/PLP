package com.example.premierleaguepredictions.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleaguepredictions.Clubs.Club;
import com.example.premierleaguepredictions.Clubs.ClubRecyclerAdapter;
import com.example.premierleaguepredictions.Clubs.OnItemClickListener;
import com.example.premierleaguepredictions.Comments.Comment;
import com.example.premierleaguepredictions.Comments.RecyclerAdapter;
import com.example.premierleaguepredictions.R;
import com.example.premierleaguepredictions.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TableFragment extends Fragment implements OnItemClickListener {

    private RecyclerView recyclerView;
    private ClubRecyclerAdapter recyclerAdapter;

    BottomNavigationView navigationView;
    private DatabaseReference databaseReference;
    private List<Club> clubList = new ArrayList<>();
    private ProgressDialog progressDialog;

    public TableFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        progressDialog = new ProgressDialog(getActivity());
        navigationView = view.findViewById(R.id.nav_view);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setupRecycler(view);
        setupRecyclerData();

        return view;
    }

    private void setupRecycler(View view){
        recyclerView = view.findViewById(R.id.ClubRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new ClubRecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setupRecyclerData(){
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("clubs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubList.clear();
                List<String> keys = new ArrayList<>();

                for( DataSnapshot ds : dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    Club club = ds.getValue(Club.class);
                    clubList.add(club);
                }
                sortList(clubList);
                recyclerAdapter.addClub(clubList);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    List<Club> sortList(List<Club> unorderList){
        Collections.sort(unorderList, new Comparator<Club>() {
            @Override
            public int compare(Club o1, Club o2) {
                return Integer.valueOf(o2.getPoints()).compareTo(Integer.valueOf(o1.getPoints()));
            }
        });
        return unorderList;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(),"Position: " + ++position,Toast.LENGTH_LONG).show();
    }
}
