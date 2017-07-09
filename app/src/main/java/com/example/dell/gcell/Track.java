package com.example.dell.gcell;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Track extends AppCompatActivity {

    Button submit;
    EditText input;
    int flag = 0;
    int flag1;
    Firebase mFirebase,frequents,complaints,child1;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Intent intent = getIntent();

        submit = (Button) findViewById(R.id.submit1);
        input = (EditText) findViewById(R.id.et_complain);
        Firebase.setAndroidContext(this);

        mFirebase = new Firebase("https://gcell-a31d9.firebaseio.com/");
        complaints = mFirebase.child("Complaints");
        frequents = mFirebase.child("Frequents");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = input.getText().toString();
                complaintsCalled(id);
                if(flag==1) {
                    frequentsCalled(id);
                }
                if(flag==2)
                {
                    Toast.makeText(getApplicationContext(),"Incorrect Id",Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void frequentsCalled(final String id1) {

        frequents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(id1)){

                    Firebase ref = frequents.child(id1);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Log.d("Test", dataSnapshot.getValue()+"\t"+dataSnapshot.getKey());
                            Map<String,Object> mp = (Map<String, Object>) dataSnapshot.getValue();
                            Iterator i = mp.values().iterator();
                            while (i.hasNext()) {
                                if(i.next().equals("Pending")){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Track.this).create();
                                    alertDialog.setTitle("Complaint Status");
                                    alertDialog.setMessage("Your Status is pending.");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "                       OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent intent1 = new Intent(Track.this,MainActivity.class);
                                                    startActivity(intent1);
                                                }
                                            });
                                    alertDialog.show();

                                }
                                // Log.d("Test", mp.values().iterator()+"");
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }
                else{
                    //Toast.makeText(getApplicationContext(),"The Id is not in frequents!",Toast.LENGTH_LONG).show();
                    flag = 2;
                    Log.d("Test","flagggg " + flag);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    private void complaintsCalled(final String id1) {



        complaints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(id1)){

                    Firebase ref = complaints.child(id1);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Log.d("Test", dataSnapshot.getValue()+"\t"+dataSnapshot.getKey());
                            Map<String,Object> mp = (Map<String, Object>) dataSnapshot.getValue();
                            Iterator i = mp.values().iterator();
                            while (i.hasNext()) {
                                if(i.next().equals("Pending")){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Track.this).create();
                                    alertDialog.setTitle("Complaint Status");
                                    alertDialog.setMessage("Your Status is pending.");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "                       OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent intent1 = new Intent(Track.this,MainActivity.class);
                                                    startActivity(intent1);
                                                }
                                            });
                                    alertDialog.show();

                                }
                                // Log.d("Test", mp.values().iterator()+"");
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }
                else{
                    //Toast.makeText(getApplicationContext(),"The Id is not in complaints!",Toast.LENGTH_LONG).show();
                    flag = 1;



                }
            }



            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }
}
