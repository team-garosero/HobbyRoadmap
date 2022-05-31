package com.garosero.android.hobbyroadmap.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.garosero.android.hobbyroadmap.data.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServerHelper {

    static public void initServer(){
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DataSnapshot snap = snapshot.child("Users/"+uid);
                    String name = snap.child("name").getValue().toString();
                    String nickname = snap.child("nickname").getValue().toString();
                    int life = Integer.parseInt(snap.child("life").getValue().toString());
                    int xp = Integer.parseInt(snap.child("xp").getValue().toString());

                    UserInfo userInfo = new UserInfo(name, nickname, life, xp);
                    //todo 개인 roadmap, course 부분 userinfo class에 넣기 / 뷰모델에서 set

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.e("ServerManager", "onCancelled", error.toException());
            }
        };
        ref.addValueEventListener(postListener);
    }

}
