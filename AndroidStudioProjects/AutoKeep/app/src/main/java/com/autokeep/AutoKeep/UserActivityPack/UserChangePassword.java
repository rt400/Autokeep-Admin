package com.autokeep.AutoKeep.UserActivityPack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autokeep.AutoKeep.Communication.LogOutTimerUtil;
import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.autokeep.AutoKeep.Communication.LogOutTimerUtil.startLogoutTimer;
import static com.autokeep.AutoKeep.Communication.LogOutTimerUtil.stopLogoutTimer;
import static com.autokeep.AutoKeep.UserActivityPack.LoginActivity.passwordValidation;

public class UserChangePassword extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {
    private static final String TAG = "UserChangePassword";

    @BindView(R.id._old_password)
    TextView oldPassword;
    @BindView(R.id._new_password)
    TextView newPassword;
    @BindView(R.id._retype_password)
    TextView retypePassword;
    @BindView(R.id._change)
    Button changePassword;
    private String old_Password = null;
    private String new_Password = null;
    private String retype = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_change_password);
        ButterKnife.bind(this);

        changePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                old_Password = oldPassword.getText().toString();
                new_Password = newPassword.getText().toString();
                retype = retypePassword.getText().toString();
                if (Validate()) {
                    try {
                        clientSocket.getInstance().SendNewPassword(new_Password);
                        if (clientSocket.getStatusData().equals("OK")) {
                            Toast.makeText(getBaseContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                            clientSocket.getUser().setPassword(new_Password);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), clientSocket.getServerMSG(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private boolean Validate() {
        if (checkOldPassword()) {
            if (checkValidateNewPassword()) {
                return checkMatchNewPassword();
            }
        }
        return false;
    }

    private boolean checkOldPassword() {
        if (old_Password.equals(clientSocket.getUser().getPassword())) {
            oldPassword.setError(null);
            return true;
        }
        oldPassword.setError("Password didn't match");
        return false;
    }

    private boolean checkValidateNewPassword() {
        if (new_Password.isEmpty() || new_Password.length() < 6 || passwordValidation(new_Password)) {
            newPassword.setError("wrong password conditions");
            return false;
        }
        newPassword.setError(null);
        return true;
    }

    private boolean checkMatchNewPassword() {
        if (new_Password.equals(retype)) {
            retypePassword.setError(null);
            return true;
        }
        retypePassword.setError("Password not match");
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLogoutTimer(this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        startLogoutTimer(this, this);
        Log.e(TAG, "User interacting with screen , Timer Start");
    }


    @Override
    protected void onPause() {
        super.onPause();
        startLogoutTimer(this, this);
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopLogoutTimer();
        Log.e(TAG, "onResume()");
    }

    /**
     * Performing idle time logout
     */
    @Override
    public void doLogout() {
    }
}
