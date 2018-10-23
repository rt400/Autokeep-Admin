package com.autokeep.AutoKeep.UserActivityPack;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autokeep.AutoKeep.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.autokeep.AutoKeep.UserActivityPack.LoginActivity.client;

public class UserNewOrders extends AppCompatActivity {
    private static final String TAG = "UserNewOrders";
    public String start_Date, end_Date;
    @BindView(R.id._start_date)
    TextView startDate;
    @BindView(R.id._end_date)
    TextView endDate;
    @BindView(R.id._cartype)
    Spinner carType;
    @BindView(R.id._carsit)
    Spinner carSit;
    @BindView(R.id._search)
    TextView searchButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener_Start, mDateSetListener_End;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_new_orders);
        ButterKnife.bind(this);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        UserNewOrders.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener_Start,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UserNewOrders.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener_End,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener_Start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker_start, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                start_Date = year + "-" + month + "-" + day;
                startDate.setText(day + "/" + month + "/" + year);
            }
        };
        mDateSetListener_End = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker_end, int year, int month, int day) {
                Date sDate = new java.util.Date(), eDate = new java.util.Date();
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                end_Date = year + "-" + month + "-" + day;
                endDate.setError(null);
                if (!startDate.getText().toString().equals("Start Date")) {
                    try {
                        sDate = sdf.parse(startDate.getText().toString());
                        eDate = sdf.parse(day + "/" + month + "/" + year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (sDate.after(eDate)) {
                    endDate.setError("");
                    endDate.setText("Date can not be early !! ");
                } else if (sDate.equals(eDate)) {
                    endDate.setError("");
                    endDate.setText("Date cannot be the same !!");
                } else {
                    endDate.setText(day + "/" + month + "/" + year);
                }
            }

        };
        final Spinner carType = findViewById(R.id._cartype);
        ArrayAdapter <String> carTypeAdaptor = new ArrayAdapter <>(UserNewOrders.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.cartype));
        carTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carType.setPrompt("Select Car Type :");
        carType.setAdapter(carTypeAdaptor);
        carType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        carTypeAdaptor,
                        R.layout.car_type_spinner,
                        this));
        carType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //Toast.makeText(getBaseContext(),carType.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (i == 1) {
                    // Toast.makeText(getBaseContext(),carType.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (i == 2) {
                    // Toast.makeText(getBaseContext(),carType.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });
        final Spinner carSit = findViewById(R.id._carsit);
        ArrayAdapter <String> carSitAdaptor = new ArrayAdapter <String>(UserNewOrders.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.carsit));
        carSitAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSit.setPrompt("Select How Many Car Sits :");
        carSit.setAdapter(carSitAdaptor);
        carSit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        carSitAdaptor,
                        R.layout.car_sit_spinner,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        carSit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //Toast.makeText(getBaseContext(),carSit.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (i == 1) {
                    // Toast.makeText(getBaseContext(),carSit.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (i == 2) {
                    //Toast.makeText(getBaseContext(),carSit.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (i == 3) {
                    //Toast.makeText(getBaseContext(),carSit.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(UserNewOrders.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Searching...");
                progressDialog.show();
                boolean run = false;
                // TODO: Implement your own authentication logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                if (checkValidate()) {
                                    try {
                                        client.SendSearch(start_Date, end_Date
                                                , carType.getSelectedItem().toString(), carSit.getSelectedItem().toString());
                                        Intent intent = new Intent(getApplicationContext(), UserSearchResult.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                }
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

        });

    }

    public boolean checkValidate() {
        if (startDate.getText().toString().equals("Start Date")) {
            Toast.makeText(getBaseContext(), "Start date not set ! \nPlease fix it", Toast.LENGTH_LONG).show();
            return false;
        } else if (endDate.getText().toString().equals("End Date")) {
            Toast.makeText(getBaseContext(), "End date not set ! \nPlease fix it", Toast.LENGTH_LONG).show();
            return false;
        } else if (carType.getSelectedItem() == null) {
            Toast.makeText(getBaseContext(), "Car type not selected ! \nPlease fix it", Toast.LENGTH_LONG).show();
            return false;
        } else if (carSit.getSelectedItem() == null) {
            Toast.makeText(getBaseContext(), "Sit car not selected ! \nPlease fix it", Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }
}

