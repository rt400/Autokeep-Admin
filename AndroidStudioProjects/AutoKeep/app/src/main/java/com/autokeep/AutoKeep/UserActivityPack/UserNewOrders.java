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

import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserNewOrders extends AppCompatActivity {
    private static final String TAG = "UserNewOrders";
    public String start_Date, end_Date, car_type, sit_car;
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
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener_Start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker_start, int year, int month, int day) {
                Date sDate = new java.util.Date(), now = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                start_Date = year + "-" + month + "-" + day;
                startDate.setError(null);
                if (!startDate.getText().toString().equals("Start Date")) {
                    try {
                        sDate = sdf.parse(startDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (now.after(sDate)) {
                    startDate.setError("");
                    startDate.setText("Date can not be in the past !! ");
                } else {
                    startDate.setText(day + "/" + month + "/" + year);
                }
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
                if (!endDate.getText().toString().equals("End Date")) {
                    try {
                        sDate = sdf.parse(startDate.getText().toString());
                        eDate = sdf.parse(day + "/" + month + "/" + year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (sDate.after(eDate)) {
                    endDate.setError("");
                    endDate.setText("End date can't be earlier! ");
                } else {
                    endDate.setText(day + "/" + month + "/" + year);
                }
            }

        };
        final Spinner carType = findViewById(R.id._cartype);
        ArrayAdapter <String> carTypeAdaptor = new ArrayAdapter <>(UserNewOrders.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.cartype));
        carTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carType.setPrompt("Select A Car Category:");
        carType.setAdapter(carTypeAdaptor);
        carType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        carTypeAdaptor,
                        R.layout.car_type_spinner,
                        this));
        carType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {
            }
        });
        final Spinner carSit = findViewById(R.id._carsit);
        ArrayAdapter <String> carSitAdaptor = new ArrayAdapter <String>(UserNewOrders.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.carsit));
        carSitAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSit.setPrompt("Select Number Of Seats:");
        carSit.setAdapter(carSitAdaptor);
        carSit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        carSitAdaptor,
                        R.layout.car_sit_spinner,
                        this));
        carSit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidate()) {
                    final ProgressDialog progressDialog = new ProgressDialog(UserNewOrders.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Searching...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    try {
                                        clientSocket.getInstance().SendSearch(start_Date, end_Date
                                                , car_type, sit_car);
                                        if (clientSocket.getStatusData().equals("OK")) {
                                            Intent intent = new Intent(getApplicationContext(), UserSearchResult.class);
                                            intent.putExtra("StartDate", start_Date);
                                            intent.putExtra("EndDate", end_Date);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getBaseContext(), clientSocket.getServerMSG(), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
            }
        });

    }

    public boolean checkValidate() {
        if (startDate.getText().toString().equals("Start Date")) {
            Toast.makeText(getBaseContext(), "Start Date is not set Please Fill The Date", Toast.LENGTH_LONG).show();
            return false;
        } else if (endDate.getText().toString().equals("End Date")) {
            Toast.makeText(getBaseContext(), "End Date is not set Please Fill The Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (carType.getSelectedItem() == null) {
            car_type = "\"\"";
        } else {
            car_type = carType.getSelectedItem().toString();
        }
        if (carSit.getSelectedItem() == null) {
            sit_car = "-1";
        } else {
            sit_car = carSit.getSelectedItem().toString();
        }
        return true;
    }
}

