package com.example.dell.gcell;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Frequent extends AppCompatActivity {

    private Button newComplain, freqComplain;
    String dept1, freq_value,erpno,complainId;
    Firebase mFirebase, frequent,child1,child2;
    TextView tv_dept;
    ListView mListView;
    String [] items;
    ArrayAdapter<String> mArrayAdapter;
    View layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequent);

        Intent intent = getIntent();
        dept1 = intent.getStringExtra("Department");
        erpno = intent.getStringExtra("ERP");

        Firebase.setAndroidContext(this);

        mFirebase = new Firebase("https://gcell-a31d9.firebaseio.com/");
        frequent = mFirebase.child("Frequents");
        child1 = frequent.child(dept1);



        newComplain = (Button) findViewById(R.id.new_complain);
        tv_dept = (TextView) findViewById(R.id.txt_dept);
        freqComplain = (Button) findViewById(R.id.freq);
        LayoutInflater inflater1 = this.getLayoutInflater();
        layout1 = inflater1.inflate(R.layout.list_freq,null);
        mListView = (ListView) layout1.findViewById(R.id.list_view);

        tv_dept.setText(dept1 + " DEPARTMENT");
        tv_dept.setTextSize(25);
        items = getResources().getStringArray(R.array.comp1);
       Log.d("Test","Items are: " + items);
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);

        mListView.setAdapter(mArrayAdapter);
        Log.d("Test", "length of array: " + items.length);






        newComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Frequent.this,Complain.class);
                intent1.putExtra("Dept",dept1);
                startActivity(intent1);
            }
        });




        freqComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Frequent.this);
                builder.setView(layout1);
                builder.show();

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            freq_value = adapterView.getItemAtPosition(i).toString();
                            Log.d("Test","Selected frequent complaint is : " + freq_value);

                            child1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    complainId = dataSnapshot.child(freq_value).child("ID").getValue().toString();
                                    Log.d("Test","id for "+ freq_value + "is " + complainId);
                                    showMessage(complainId);
                                    Log.d("Test","It comes back here");


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });










                    }
                });
            }
        });


    }

    public void showMessage(String id){
        Log.d("Test","id for next "+ freq_value + "is " + complainId);
        AlertDialog alertDialog = new AlertDialog.Builder(Frequent.this).create();
        alertDialog.setTitle("ACKNOWLEDGEMENT");
        alertDialog.setMessage("Your Complaint has been recorded! You can track status using complain id: " + complainId);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "                       OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent2 = new Intent(Frequent.this,MainActivity.class);
                        startActivity(intent2);


                    }
                });
        alertDialog.show();


    }
}
