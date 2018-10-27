package com.autokeep.AutoKeep.UserActivityPack;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static boolean isLogged = false;
    private static String password = null;
    private int retryLogin = 0;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.message)
    TextView _msg;
    @BindView(R.id.btn_login)
    Button _loginButton;

    public static boolean isIsLogged() {
        return isLogged;
    }

    public static void setIsLogged(boolean isLogged) {
        LoginActivity.isLogged = isLogged;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoginActivity.password = password;
    }

    public static boolean passwordValidation(String passwordEd) {
        String PASSWORD_PATTERN = "^(?=.*[A-z])(?=.*[@_.]).*$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(passwordEd);
        if (!passwordEd.matches(".*\\d.*") || !matcher.matches()) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //new CheckServerConnection().execute();
                if (validate()) {
                        login();
                    } else {
                    Toast.makeText(getBaseContext(), "Wrong email or address", Toast.LENGTH_LONG).show();
                }
                retryLogin++;
                if (retryLogin >= 5) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Failed Login");
                    builder.setMessage("");
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.setCancelable(false);
                    alert.setCanceledOnTouchOutside(false);
                    new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long l) {
                            alert.setMessage("To many retry to logging to server \nwith wrong email or password\n\n" +
                                    "The Server block you\nto login for 30s !\n\n" +
                                    "Time Left : " + l / 1000);
                        }
                        @Override
                        public void onFinish() {
                            retryLogin = 0;
                            alert.cancel();
                        }
                    }.start();
                }
            }

        });

    }

    public void login() {
        Log.d(TAG, "Login");
/*
        if (!validate()) {
            onLoginFailed();
            return;
        }*/
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        boolean run = false;
                        if (clientSocket.getInstance().connection()) {
                            try {
                                clientSocket.getInstance().SendLogin(_emailText.getText().toString(), _passwordText.getText().toString());
                            } catch (IOException e) {
                                onLoginFailed();
                            }
                            clientSocket.getInstance().readFromServer();
                            if (clientSocket.getInstance().getStatusData().equals("OK")) {
                                onLoginSuccess();
                            } else {
                                onLoginFailed();
                            }
                        }

                        progressDialog.dismiss();
                    }
                }, 5000);
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
                ActivityCompat.finishAffinity(LoginActivity.this);
            }
        });
        builder.show();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        setIsLogged(true);
        Intent intent = new Intent(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        if (clientSocket.getInstance().getServerMSG().equals("")) {
            Toast.makeText(getBaseContext(), "Failed to login...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), clientSocket.getInstance().getServerMSG(), Toast.LENGTH_LONG).show();
        }
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        setPassword(_passwordText.getText().toString());

        if (_emailText.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.getText().toString()).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (getPassword().isEmpty() || getPassword().length() < 6 || passwordValidation(getPassword())) {
            _passwordText.setError("wrong password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


}
