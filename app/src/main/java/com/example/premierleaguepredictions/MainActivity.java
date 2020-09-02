package com.example.premierleaguepredictions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.premierleaguepredictions.Clubs.Club;
import com.example.premierleaguepredictions.Comments.Comment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView tvPremierLeague;
    TextView tvPredictions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPremierLeague = findViewById(R.id.tvPremierLeague);
        tvPredictions = findViewById(R.id.tvPredictions);

        startAnimation();
        setLoggedEmail();
    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.anim2);

        tvPremierLeague.startAnimation(animation);
        tvPredictions.startAnimation(animation2);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNewActivity();
            }
        }, 5000);
    }

    private void startNewActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("flag","false").apply();
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }
    private void setLoggedEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loggedEmail","Noone logged").apply();
    }
}
