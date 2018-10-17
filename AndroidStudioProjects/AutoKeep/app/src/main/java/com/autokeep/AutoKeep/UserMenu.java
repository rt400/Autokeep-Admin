package com.autokeep.AutoKeep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.autokeep.AutoKeep.UserMode.ProtocolMessage;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.autokeep.AutoKeep.LoginActivity.client;

public class UserMenu extends AppCompatActivity {
    private static final String TAG = "UserMenu";
    public ProtocolMessage prtMsg=ProtocolMessage.USER_MODEL;

    @BindView(R.id._new_order) Button new_order;
    @BindView(R.id._change_password) Button change_password;
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);
        ButterKnife.bind(this);


        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserNewOrders.class);
                startActivity(intent);
                //finish();
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserChangePassword.class);
                startActivity(intent);
                //finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are already connected..");
        builder.setMessage("Do you want to disconnect? ");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LoginActivity.setIsLogged(false);
                client.close();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
        // Disable going back to the MainActivity
        if(LoginActivity.isIsLogged()) {

        }
       // moveTaskToBack(true);
    }


}