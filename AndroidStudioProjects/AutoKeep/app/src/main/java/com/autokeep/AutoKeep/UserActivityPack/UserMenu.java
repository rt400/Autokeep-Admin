package com.autokeep.AutoKeep.UserActivityPack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autokeep.AutoKeep.Communication.ProtocolMessage;
import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserMenu extends AppCompatActivity {
    private static final String TAG = "UserMenu";
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    public ProtocolMessage prtMsg = ProtocolMessage.USER_MODEL;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @BindView(R.id._new_order)
    Button new_order;
    @BindView(R.id._orders)
    Button orders;
    @BindView(R.id._change_password)
    Button change_password;
    @BindView(R.id._logooff_btn)
    Button logoff;
    @BindView(R.id._exit_btn)
    Button exit;
    @BindView(R.id.welcome)
    TextView welcome_msg;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);
        if (clientSocket.getUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ButterKnife.bind(this);
        welcome_msg.setText("Yor are connect as : " + clientSocket.getUser().getFullName());
        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserNewOrders.class);
                startActivity(intent);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderResult.class);
                startActivity(intent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserChangePassword.class);
                startActivity(intent);
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("You are still connected..");
                builder.setMessage("Do you want to logoff and return to login page? \n\n All login data will be erase...");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            clientSocket.getInstance().close();
                        } catch (NullPointerException e) {
                            finish();
                        }
                        editor.putBoolean(KEY_REMEMBER, false);
                        editor.remove(KEY_PASS);
                        editor.remove(KEY_USERNAME);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Closing KeepCar ");
        builder.setMessage("Are you sure you want to exit ? ");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (clientSocket.getInstance() != null) {
                    clientSocket.getInstance().close();
                }
                finish();
                ActivityCompat.finishAffinity(UserMenu.this);
            }
        });
        builder.show();
    }


}