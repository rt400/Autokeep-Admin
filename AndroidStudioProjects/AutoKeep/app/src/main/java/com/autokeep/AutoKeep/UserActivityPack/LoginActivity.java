package com.autokeep.AutoKeep.UserActivityPack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autokeep.AutoKeep.Communication.clientOB;
import com.autokeep.AutoKeep.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static clientOB client;
    private static boolean isLogged = false;
    private static String password = null;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
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
                login();
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        boolean run = false;
        // TODO: Implement your own authentication logic here.
        try {
            client = new clientOB("shahak18.ddns.net", 40501);
            client.SendLogin(_emailText.getText().toString(), _passwordText.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.readFromServer();
        if (client.getStatusData().equals("OK")) {
            run = true;
        }
        final boolean finalRun = run;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (finalRun) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        setIsLogged(true);
        Intent intent = new Intent(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        if (client != null) {
            client.close();
        }
        client = null;
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
