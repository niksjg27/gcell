package com.example.dell.gcell;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class Register extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    DatePicker pick;
    Firebase mFirebase, users,erpp;
    EditText erp;
    EditText e2;
    Button next;
   String dept,erp_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();

        Firebase.setAndroidContext(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        erp =(EditText) findViewById(R.id.erpno);
        next = (Button) findViewById(R.id.next);
        mFirebase= new Firebase("https://gcell-a31d9.firebaseio.com/");
        users = mFirebase.child("Users");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dept_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        erp_no = erp.getText().toString();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test","on clickin next "+dept + " dt "+ e2.getText().toString() + " " + erp.getText().toString());
                if(dept.equals("SELECT") || e2.getText().toString().equals("") || erp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill all the details!",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    erpp = users.child(erp.getText().toString());
                    Map<String,Object> map1 = new HashMap<String, Object>();
                    map1.put("DOB",e2.getText().toString() );
                    erpp.updateChildren(map1);
                    Intent intent1 = new Intent(Register.this,Frequent.class);
                    intent1.putExtra("Department",dept);
                    intent1.putExtra("ERP",erp_no);
                    startActivity(intent1);
                }

            }
        });











    }

    public void onStart(){
        super.onStart();
        e2 = (EditText) findViewById(R.id.dateView);
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DateDialog dialog = new DateDialog(e2);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                    Log.d("Test","datee "+e2.getText().toString());

        }

    });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("Test","selected item "+ adapterView.getItemAtPosition(i));
        dept = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("Test","nothing selected");
    }
}
