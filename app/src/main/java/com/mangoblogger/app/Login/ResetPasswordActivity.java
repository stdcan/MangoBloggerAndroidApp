package com.mangoblogger.app.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mangoblogger.app.AppUtils;
import com.mangoblogger.app.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResetPasswordActivity extends BaseAuthActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetPassword(email);
            }
        });
    }

    private void resetPassword(String username) {
        if(!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else  {
            initAdapter().resetPassword(username, new Callback<ResetPasswordResponse>() {
                @Override
                public void success(ResetPasswordResponse resetPasswordResponse, Response response) {
                    if(resetPasswordResponse.getStatus().equals("ok")) {
                        setResponseMessage(R.color.global_color_green_primary, "Password reset has been sent on your registered email. Check your Inbox");
                    } else {
                        setResponseMessage(R.color.bg_screen1, resetPasswordResponse.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    private void setResponseMessage(int colorId, String message) {
        TextView textView = (TextView) findViewById(R.id.response_text);
        textView.setTextColor(getResources().getColor(colorId));
        textView.setText(message);
    }

}
