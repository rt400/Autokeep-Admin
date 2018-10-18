package com.autokeep.AutoKeep.UserActivityPack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autokeep.AutoKeep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.autokeep.AutoKeep.UserActivityPack.LoginActivity.getPassword;
import static com.autokeep.AutoKeep.UserActivityPack.LoginActivity.passwordValidation;

public class UserChangePassword extends AppCompatActivity {
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
                Boolean check[] = new Boolean[3];
                if (!old_Password.equals(getPassword())) {
                    oldPassword.setError("Password didn't match");
                    check[0] = false;
                } else {
                    oldPassword.setError(null);
                    check[0] = true;
                }
                if (new_Password.isEmpty() || new_Password.length() < 8 || passwordValidation(new_Password)) {
                    newPassword.setError("wrong password conditions");
                    check[1] = false;
                } else {
                    newPassword.setError(null);
                    check[1] = true;
                }
                if (!new_Password.equals(retype)) {
                    retypePassword.setError("Password not match");
                    check[2] = false;
                } else {
                    retypePassword.setError(null);
                    check[2] = true;
                }

            }

        });

    }
}
