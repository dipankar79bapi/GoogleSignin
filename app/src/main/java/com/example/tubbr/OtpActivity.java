package com.example.tubbr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpActivity extends AppCompatActivity {
    @BindView(R.id.txt_otp_header) TextView txt_otp_header_email;
    @BindView(R.id.txt_resend_otp) TextView txt_resend_otp;
    @BindView(R.id.otp_view) OtpView otp_view;
    @BindView(R.id.lnr_back) LinearLayout lnr_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);

        Intent i = getIntent();
        String number = i.getStringExtra("number");
        String number2 = i.getStringExtra("number");


        txt_otp_header_email.setText("Enter OTP sent to \n"+number);

        otp_view.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted=>", otp);
                startActivity(new Intent(OtpActivity.this,MainActivity.class));
                finish();
            }
        });

        txt_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txt_status_header.setText("OTP has been resent");
                otp_view.setText("");
                Toast.makeText(OtpActivity.this, "Otp Sent to your registered number", Toast.LENGTH_SHORT).show();
            }
        });

        lnr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


}