package com.example.carlosje.reportes_ibm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;


import java.util.Calendar;

import static java.lang.String.valueOf;

/**
 * Created by carlosje on 1/12/2018.
 */

public class TimePick extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private int mNum;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        mNum = getArguments().getInt("num");
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute){

        String hora;
        String minuto;

        hora = valueOf(hourOfDay);
        minuto = valueOf(minute);

        if (hora.length() == 1) {
            hora = "0" + hora;
        }

        if (minuto.length() == 1) {
            minuto = "0" + minuto;
        }

        if (mNum==1){
        TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_ini);
        tv1.setText(hora+ ":"+ minuto);
        }
        if (mNum==2){
            TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_fin);
            tv1.setText(hora+ ":"+ minuto);
        }
        if (mNum==3){
            TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_acceso);
            tv1.setText(hora+ ":"+ minuto);
        }
        if (mNum==4){
            TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_LL_ETV);
            tv1.setText(hora+ ":"+ minuto);
        }
        if (mNum==5){
            TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_atencion_MV);
            tv1.setText(hora+ ":"+ minuto);
        }
        if (mNum==6){
            TextView tv1=(TextView) getActivity().findViewById(R.id.TV_hr_ATM_Validad);
            tv1.setText(hora+ ":"+ minuto);
        }





    }
}