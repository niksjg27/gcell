package com.example.dell.gcell;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by dell on 10/9/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText e1;
    int year,month,day;

    public DateDialog(EditText view) {
        e1 = (EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(getActivity(),this,year,month,day);

    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = i2+"-"+(i1+1)+"-"+i;

        e1.setText(date);
    }
}
