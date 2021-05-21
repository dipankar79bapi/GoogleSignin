package com.example.tubbr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginWithPhoneNumber extends AppCompatActivity {
    @BindView(R.id.edt_phone_number) EditText edt_phone_number;
    @BindView(R.id.tip_layout_mobile_number) TextInputLayout tip_layout_mobile_number;
    @BindView(R.id.lnr_signup) LinearLayout lnr_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone_number);
        ButterKnife.bind(this);


        lnr_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edt_phone_number.getText().toString().trim();

                if (phone.length() < 10){
                    tip_layout_mobile_number.setError("Min 10 characters required");
                    Toast.makeText(LoginWithPhoneNumber.this, "Please enter the phone number properly", Toast.LENGTH_SHORT).show();

                }else{
                    tip_layout_mobile_number.setError("");
                    startActivity(new Intent(LoginWithPhoneNumber.this,OtpActivity.class).putExtra("number",phone));
                    finish();
                }


            }
        });
    }
}