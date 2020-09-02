package com.example.premierleaguepredictions;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CheckRegistration {

    private String fullname;
    private String nickname;
    private String email;
    private String password;
    private String confPassword;
    private boolean isNickExistInDatabase;

    CheckRegistration(String fullname,String nickname,String email,String password,String confPassword){
        this.fullname=fullname;
        this.nickname=nickname;
        this.email=email;
        this.password=password;
        this.confPassword=confPassword;
        isNickExistInDatabase=true;
    }

    public boolean isNickExistInDatabase() {
        return isNickExistInDatabase;
    }

    boolean checkIsEmpty(){
        if(fullname.isEmpty() || nickname.isEmpty()|| email.isEmpty() || password.isEmpty() ||confPassword.isEmpty()){
            return false;
        }
        else return true;
    }

    public boolean checkPassword(){
        if(password.isEmpty() ||confPassword.isEmpty()){
            return false;
        }
        if(!password.equals(confPassword)){
            return false;
        }
        else if (password.length()<6){
            return false;
        }
        return true;
    }

    public void setNicknameExist(String name){
        Query nick = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("nickname")
                .equalTo(name);

        nick.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    isNickExistInDatabase = false;

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
