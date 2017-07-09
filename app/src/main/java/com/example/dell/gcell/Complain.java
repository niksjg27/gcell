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

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Complain extends AppCompatActivity {

    private EditText complainn;
    private Button submit;
    Firebase mFirebase,complaints,complainId;
    String fullComplain, dept,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        Intent intent = getIntent();
        dept = intent.getStringExtra("Dept");


        Firebase.setAndroidContext(this);

        mFirebase = new Firebase("https://gcell-a31d9.firebaseio.com/");
        complaints = mFirebase.child("Complaints");

        complainn = (EditText) findViewById(R.id.complainn);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullComplain = complainn.getText().toString();
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                String date = day + "-" + month + "-" + year;
                Random r = new Random();
                int i1 = r.nextInt(80000 - 00006) + 00006;
                value = dept.substring(0,2) + i1;
                complainId = complaints.child(value);
                Map<String,Object> map1 = new HashMap<String, Object>();
                map1.put("Complain: ",fullComplain);
                map1.put("Date: ",date);
                map1.put("Status: ", "Pending");
                complainId.updateChildren(map1);
                Log.d("Test","random value is: " + value);
                AlertDialog alertDialog = new AlertDialog.Builder(Complain.this).create();
                alertDialog.setTitle("New Complaint");
                alertDialog.setMessage("Your Complain Id is : " + value + ".Use it for tracking status of your complaint.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "                       OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent1 = new Intent(Complain.this,MainActivity.class);
                                startActivity(intent1);
                            }
                        });
                alertDialog.show();


            }
        });
    }
}
