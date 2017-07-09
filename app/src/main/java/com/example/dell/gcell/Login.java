package com.example.dell.gcell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class Login extends AppCompatActivity {

    EditText user, pass;
    Firebase mFirebase,authority;
    Button login;
    String e1,e2;
    int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        Firebase.setAndroidContext(this);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password1);
        login = (Button) findViewById(R.id.login);

        //Log.d("Test","e1:"+ e1 + " e2: " + e2);

        mFirebase = new Firebase("https://gcell-a31d9.firebaseio.com/");

        authority = mFirebase.child("Authority");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e1 = user.getText().toString();
                e2 = pass.getText().toString();

                authority.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterator i = dataSnapshot.getChildren().iterator();
                        while (i.hasNext()){
                            HashMap<String, Object> fetch_user = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(fetch_user.containsKey(e1)){
                                if(e2.equals( dataSnapshot.child(e1).child("password").getValue().toString()))
                                {
                                    flag =1;
                                    break;
                                }

                            }
                        }

                        if(flag==1){
                            Intent intent1 = new Intent(Login.this,Update.class);
                            intent1.putExtra("username",e1);
                            startActivity(intent1);
                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Invalid username",Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });

    }
}
