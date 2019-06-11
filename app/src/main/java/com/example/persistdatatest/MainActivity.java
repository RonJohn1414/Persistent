package com.example.persistdatatest;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.persistdatatest.model.DatabaseHelper;
import com.example.persistdatatest.model.UserTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_address, et_age, et_dob;
    Button btn_save, btn_read;

    private DatePickerDialog mDatePickerDialog;
    private EditText edDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        edDate = findViewById(R.id.et_dob);

        setDateTimeField();

        edDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String fdate = et_dob.getText ().toString ();

                et_dob.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());





        initUI();

        btn_save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                saveUserData ();
            }
        });
        btn_read.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                readData ();
            }
        });

    }

    private void initUI() {
        et_address =findViewById (R.id.et_address);
        et_age =findViewById (R.id.et_age);
        et_dob=findViewById (R.id.et_dob);
        et_name=findViewById (R.id.et_name);
        btn_read=findViewById (R.id.btn_read);
        btn_save=findViewById (R.id.btn_save);
    }

    public void saveUserData(){
        ContentValues contentValues = new ContentValues ();
        contentValues.put (UserTable.COLUMN_NAME,
                et_name.getText ().toString ());
        contentValues.put (UserTable.COLUMN_ADDRESS,
                et_address.getText ().toString ());
        contentValues.put (UserTable.COLUMN_AGE,
                et_age.getText ().toString ());
        contentValues.put (UserTable.COLUMN_AGE,
                Integer.valueOf (
                        et_age.getText ().toString ()));

        contentValues.put (UserTable.COLUMN_DOB,et_dob.getText ().toString ());

        DatabaseHelper databaseHelper =
                new DatabaseHelper (this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase ();
        long result =
        database.insert (UserTable.TABLE_NAME,
                null,
                contentValues);
        if (result > 0){
            Toast.makeText (this, "Values Saved!",
                    Toast.LENGTH_SHORT).show ();
            cleanUI ();
        }else {
            Toast.makeText (this, "Values not saved!",
                    Toast.LENGTH_SHORT).show ();
            cleanUI();

    }
}

    private void cleanUI() {
        et_dob.setText ("");
        et_age.setText ("");
        et_name.setText ("");
        et_address.setText ("");
    }

    public void readData(){
        DatabaseHelper databaseHelper = new DatabaseHelper (this);
        SQLiteDatabase sqLiteDatabase =
                databaseHelper.getReadableDatabase ();

        String [] columns = {
                UserTable.COLUMN_NAME,
                UserTable.COLUMN_AGE,
        };

        String selection = UserTable.COLUMN_NAME+
                " = ?";

        String[] selectionArg = {"Antonino"};

        Cursor cursor =
        sqLiteDatabase.query (
                UserTable.TABLE_NAME,
                null,
               null,
               null,
                null,
                null,
                null
        );
        while (cursor.moveToNext ()){
            et_name.setText (
                    cursor.getString (
                            cursor.getColumnIndexOrThrow (
                                    UserTable.COLUMN_NAME
                            )
                    )
            );
            et_address.setText (
                    cursor.getString (

                    cursor.getColumnIndexOrThrow (
                            UserTable.COLUMN_ADDRESS
                    )
                    )
            );
            et_age.setText (
                    cursor.getString (
                    cursor.getColumnIndexOrThrow (
                            UserTable.COLUMN_AGE
                    )
                    )
            );
            et_dob.setText (
                    cursor.getString (
                    cursor.getColumnIndexOrThrow (
                            UserTable.COLUMN_DOB
                    )
                    )
            );
        }
      //  cursor.moveToFirst ();
       //  do {
        //
       //  }while (cursor.moveToNext ());

        cursor.close ();
    }

    }
