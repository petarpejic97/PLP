package com.example.premierleaguepredictions.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.premierleaguepredictions.Fixtures.ClubData;
import com.example.premierleaguepredictions.Fixtures.Fixture;
import com.example.premierleaguepredictions.Fixtures.FixturesRecyclerAdapter;
import com.example.premierleaguepredictions.Clubs.OnItemClickListener;
import com.example.premierleaguepredictions.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FixturesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FixturesRecyclerAdapter recyclerAdapter;
    private Button btnAddMatch;
    private Button btnBestValue;
    private Button btnEnter;
    private Button btnAdd;
    private EditText edUpdate;
    private FloatingActionButton fabRemove,fabUpdateBestValue;
    private List<Fixture> fixtureList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow,pwUpdateBestValue;
    private RelativeLayout relativeLayout;
    private TextView tvResult;
    private String[] clubs={"Arsenal","Aston Villa","Bournemouth","Brighton","Burnley","Chelsea","Crystal Palace",
            "Everton","Leicester","Liverpool","Manchester City","Manchester United","Newcastle United","",
            "Sheffield United","Southampton","Tottenham","Watford","West Ham United","Wolverhampton","Norwich"};
    ArrayAdapter<String> adapter;
    private AutoCompleteTextView homeName;
    private EditText homeCoeff;
    private AutoCompleteTextView awayName;
    private EditText awayCoeff;
    private Fixture fixture;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase2;
    private Task<Void> myRef2;

    public FixturesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);

        recyclerView = view.findViewById(R.id.fixturesRecyclerView);
        btnAddMatch = view.findViewById(R.id.btnAddMatch);
        fabRemove = view.findViewById(R.id.fabRemove);
        btnBestValue = view.findViewById(R.id.btnBestValue);
        relativeLayout = view.findViewById(R.id.relative);
        fabUpdateBestValue = view.findViewById(R.id.fabUpdateBestValue);

        btnEnter = view.findViewById(R.id.btnEnterValue);
        edUpdate = view.findViewById(R.id.edBestValue);

        setupRecycler();
        setupRecyclerData();
        onBtnBestValue();
        onFabUpdateBestValue();
        isAdmin();
        onBtnAddMatch();
        onFabRemove();

        return view;
    }

    private void onFabRemove() {
        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("fixtures").removeValue();
            }
        });

    }

    private void onBtnAddMatch() {
        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                final ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pw_add_match,null);

                homeName = container.findViewById(R.id.edHomeName);
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice, clubs);
                homeName.setAdapter(adapter);
                homeCoeff = container.findViewById(R.id.edBetCoef);

                awayName = container.findViewById(R.id.edHomeNamesec);
                awayName.setAdapter(adapter);
                awayCoeff = container.findViewById(R.id.edBetCoefsec);
                btnAdd = container.findViewById(R.id.btnAdd);

                pwUpdateBestValue = new PopupWindow(container,1000, 700,true);
                pwUpdateBestValue.showAtLocation(relativeLayout, Gravity.CENTER,1,1);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = FirebaseDatabase.getInstance().getReference("id").push().getKey();

                        String homeClub = homeName.getText().toString();
                        String homeCoef = homeCoeff.getText().toString();

                        String awayClub = awayName.getText().toString();
                        String awayCoef = awayCoeff.getText().toString();

                        ClubData clubData1 = new ClubData(homeClub,homeCoef);
                        ClubData clubData2 = new ClubData(awayClub,awayCoef);
                        fixture = new Fixture(clubData1,clubData2);

                        mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                        myRef2 = mFirebaseDatabase2.getReference().child("fixtures").child(key).setValue(fixture);

                        resetFields();
                    }
                });

            }
        });
    }

    private void resetFields() {
        homeName.setText("");
        homeCoeff.setText("");
        awayName.setText("");
        awayCoeff.setText("");
    }

    @SuppressLint("RestrictedApi")
    private void isAdmin() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("loggedEmail","defvalue").equals("petar.pejic@outlook.com")) {
            fabRemove.setVisibility(View.VISIBLE);
            btnAddMatch.setVisibility(View.VISIBLE);
            fabUpdateBestValue.setVisibility(View.VISIBLE);
        }
    }

    private void onFabUpdateBestValue() {
        fabUpdateBestValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                final ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_update_value,null);

                pwUpdateBestValue = new PopupWindow(container,700,600,true);
                pwUpdateBestValue.showAtLocation(relativeLayout, Gravity.CENTER,1,1);
                edUpdate = container.findViewById(R.id.edBestValue);
                btnEnter = container.findViewById(R.id.btnEnterValue);
                btnEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseDatabase = FirebaseDatabase.getInstance();
                        myRef= mFirebaseDatabase.getReference();
                        myRef.child("bestvalue").setValue(edUpdate.getText().toString());
                        edUpdate.setText("");
                    }
                });
            }
        });

    }

    private void onBtnBestValue() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef= mFirebaseDatabase.getReference();

        btnBestValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_bestvalue,null);

                popupWindow = new PopupWindow(container,600,200,true);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER,1,1);
                tvResult = container.findViewById(R.id.tvResult);

                myRef.child("bestvalue").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String result = (String)dataSnapshot.getValue();
                        tvResult.setText(result);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new FixturesRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setupRecyclerData() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef= mFirebaseDatabase.getReference().child("fixtures");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fixtureList.clear();

                for( DataSnapshot ds : dataSnapshot.getChildren()){
                    Fixture fixture =ds.getValue(Fixture.class);

                    fixtureList.add(fixture);
                }
                recyclerAdapter.addFixture(fixtureList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
