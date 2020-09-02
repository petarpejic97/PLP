package com.example.premierleaguepredictions.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.premierleaguepredictions.Comments.Comment;
import com.example.premierleaguepredictions.R;
import com.example.premierleaguepredictions.Comments.RecyclerAdapter;
import com.example.premierleaguepredictions.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsFragment extends Fragment {

    private RecyclerAdapter recyclerAdapter;
    private EditText edAddComment;
    private Button btnPost;
    private User user;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private List<Comment> commentList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private DatabaseReference myRef;

    public CommentsFragment() {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        edAddComment = view.findViewById(R.id.edAddComment);
        btnPost = view.findViewById(R.id.btnPost);
        progressDialog = new ProgressDialog(getActivity());
        BottomNavigationView navigationView = view.findViewById(R.id.nav_view);
        floatingActionButton = view.findViewById(R.id.fab);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef= mFirebaseDatabase.getReference();

        Bundle bundle = getArguments();
        if(bundle != null)
            user=(User)bundle.getSerializable("loggedUser");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        if(sharedPreferences.getString("loggedEmail","defvalue").equals("petar.pejic@outlook.com"))
            floatingActionButton.setVisibility(View.VISIBLE);

        setScrollerAtComment();
        setupRecycler(view);
        setupRecyclerData();
        onBtnClick();
        onFloatingActionClick();
        return view;
    }

    private void onFloatingActionClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeComments();
            }
        });
    }

    private void removeComments() {
         myRef.child("comments").removeValue();
    }

    private void setScrollerAtComment() {
        edAddComment.setScroller(new Scroller(getActivity()));
        edAddComment.setMaxLines(3);
        edAddComment.setVerticalScrollBarEnabled(true);
        edAddComment.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onBtnClick() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edAddComment.getText().toString().isEmpty()){
                    checkIsUserLogged();
                }
                else{
                    Toast.makeText(getActivity(), "Please, write the comment.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkIsUserLogged() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("flag", "DefValue").equals("true")){
            postComment();
        }
        else {
            Toast.makeText(getActivity(), "To comment, you must be logged in.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void postComment(){
        String key = FirebaseDatabase.getInstance().getReference("id").push().getKey();

        String commentText = edAddComment.getText().toString();
        String nickname = user.getNickname();
        String imageURL = user.getFilePath();
        String time = DateFormat.getDateTimeInstance().format(new Date());

        Comment comment = new Comment(nickname, time, commentText, imageURL);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("comments").child(key).setValue(comment);

        resetField();
    }

    private void resetField() {
        edAddComment.setText("");
    }

    private void setupRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.smoothScrollToPosition(0);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setupRecyclerData() {
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("comments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                List<String> keys = new ArrayList<>();

                for( DataSnapshot ds : dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    Comment comment = ds.getValue(Comment.class);
                    commentList.add(comment);
                }
                recyclerAdapter.addComments(commentList);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
