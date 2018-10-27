package com.autokeep.AutoKeep.UserActivityPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class LoginActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LoginActivity";
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String password = null;
    private int retryLogin = 0;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.message)
    TextView _msg;
    @BindView(R.id.checkBox)
    CheckBox remember;
    @BindView(R.id.btn_login)
    Button _loginButton;

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            remember.setChecked(true);
        else
            remember.setChecked(false);

        _emailText.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        _passwordText.setText(sharedPreferences.getString(KEY_PASS, ""));

        _emailText.addTextChangedListener(this);
        _passwordText.addTextChangedListener(this);
        remember.setOnCheckedChangeListener(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        managePrefs();
    }

    private void managePrefs() {
        if (remember.isChecked()) {
            editor.putString(KEY_USERNAME, _emailText.getText().toString().trim());
            editor.putString(KEY_PASS, _passwordText.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
}
