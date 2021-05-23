package com.example.tubbr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {
    @BindView(R.id.edt_user_name) EditText edt_user_name;
    @BindView(R.id.edt_password) EditText edt_password;

    @BindView(R.id.lnr_signup) LinearLayout lnr_signup;

    @BindView(R.id.google_signin) GoogleSignInButton google_signin;
    @BindView(R.id.ll_login_with_phone_no) LinearLayout ll_login_with_phone_no;


    @BindView(R.id.tip_layout_first_name) TextInputLayout tip_layout_first_name;
    @BindView(R.id.tip_password) TextInputLayout tip_password;
    @BindView(R.id.txt_signup) TextView txt_signup;


    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        lnr_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edt_user_name.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (userName.isEmpty() && userName.equals("")) {
                    Toast.makeText(Login.this, "Please enter the user name properly", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty() && password.equals("")){
                    Toast.makeText(Login.this, "Please enter the Password properly", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }

            }
        });

        ll_login_with_phone_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,LoginWithPhoneNumber.class));
                finish();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_clientid))
                .requestProfile()
                .requestEmail()
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
                progressDialog.setCancelable(false);

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Signup.class));
                finish();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String idToken = acct.getIdToken();
            Log.d("DD", idToken);
            progressDialog.dismiss();
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
            //signout.setVisibility(View.VISIBLE);
           // signin.setVisibility(View.GONE);

        }else{
            progressDialog.dismiss();
        }
    }

}