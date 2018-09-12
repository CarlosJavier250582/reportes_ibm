package com.example.carlosje.reportes_ibm;



        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import java.util.Calendar;

public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener{
private int mNum;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mNum = getArguments().getInt("num");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (mNum==1){
            return new DatePickerDialog(getActivity(), (ATMcorrectActivity)getActivity(),  year, month, day);
        }else{
            if (mNum==2){
                return new DatePickerDialog(getActivity(), (ATMprevActivity)getActivity(),  year, month, day);
            }else{

                    return new DatePickerDialog(getActivity(), (InventarioActivity)getActivity(),  year, month, day);

            }
        }





    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }

}