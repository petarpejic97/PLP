package com.example.premierleaguepredictions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.premierleaguepredictions.Fragments.AlreadyLoggedFragment;
import com.example.premierleaguepredictions.Fragments.CommentsFragment;
import com.example.premierleaguepredictions.Fragments.FixturesFragment;
import com.example.premierleaguepredictions.Fragments.LoginFragment;
import com.example.premierleaguepredictions.Fragments.TableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView navView;
    private FrameLayout mainFrame;
    private FixturesFragment fixturesFragment;
    private TableFragment tableFragment;
    private CommentsFragment commentsFragment;
    private LoginFragment loginFragment;
    private AlreadyLoggedFragment alreadyLoggedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navView = findViewById(R.id.nav_view);
        mainFrame = findViewById(R.id.main_frame);
        fixturesFragment = new FixturesFragment();
        tableFragment = new TableFragment();
        commentsFragment = new CommentsFragment();
        loginFragment = new LoginFragment();
        alreadyLoggedFragment = new AlreadyLoggedFragment();

        setFragment(fixturesFragment);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_fixtures:
                        setFragment(fixturesFragment);
                        return true;

                    case R.id.navigation_table:
                        setFragment(tableFragment);
                        return true;

                    case R.id.navigation_comments:
                        setFragment(commentsFragment);
                        return true;

                    case R.id.navigation_login:
                        SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
                        if(login.getString("flag","login").equals("false")){
                            setFragment(loginFragment);
                        }
                        else if(login.getString("flag","login").equals("true")){
                        setFragment(alreadyLoggedFragment);
                    }
                        return true;

                    default:
                        return false;
                }
            }

        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }


}
