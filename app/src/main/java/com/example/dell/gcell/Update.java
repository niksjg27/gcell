package com.example.dell.gcell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

public class Update extends AppCompatActivity {

    Firebase mFirebase,authority,complaints,frequents;
    String dept;
    String [] container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");

        Firebase.setAndroidContext(this);

        Log.d("Test",name + " logged in successfully");

        mFirebase = new Firebase("https://gcell-a31d9.firebaseio.com/");
        authority = mFirebase.child("Authority");
        complaints = mFirebase.child("Complaints");
        frequents = mFirebase.child("Frequents");

        authority.child(name).child("department").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dept = dataSnapshot.getValue().toString();
                compCall(dept);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });








    }

    private void compCall(final String dept1) {

        complaints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterator i = dataSnapshot.getChildren().iterator();
                    while(i.hasNext()){
                        //Log.d("Test","vlaue is " + ((DataSnapshot)i.next()).getKey().contains("CO"));

                            if(((DataSnapshot)i.next()).getKey().contains("CO")){
                                Log.d("Test","bbbbbbbbbb");
                                Log.d("Test","" + ((DataSnapshot) i).getValue());
                                Map<String,Object> mp = (Map<String, Object>) ((DataSnapshot) i).getValue();
                                Log.d("Test",""+mp.values());


                            }


                    }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
