package com.Wipocab.abilytics.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;
import com.Wipocab.abilytics.app.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btn_otp,btn_resend;
    EditText otp;
    String o,email;
    ProgressBar progressBar;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btn_otp = (AppCompatButton)findViewById(R.id.btn_otp);
        otp=(EditText)findViewById(R.id.otp);
        progressBar=(ProgressBar)findViewById(R.id.progress_otp);
        btn_resend=(AppCompatButton)findViewById(R.id.btn_resend);
        btn_otp.setOnClickListener(this);
        email=getIntent().getStringExtra("email");
        btn_resend.setOnClickListener(this);

        materialDialog=new MaterialDialog.Builder(this)
                .content(R.string.loading)
                .progress(true, 0);
        dialog=materialDialog.build();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_otp:
               // progressBar.setVisibility(View.VISIBLE);
                dialog.show();
                o=otp.getText().toString();
                otpProcess();
                break;
            case R.id.btn_resend:
               // progressBar.setVisibility(View.VISIBLE);
                dialog.show();
                resendProcess();
                break;

        }
    }
    public void otpProcess()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setOtp(o.toString());
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.VERIFYOTP_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if(resp.getResult().equals(Constants.SUCCESS)) {
                    Snackbar.make(getCurrentFocus(),resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Verified,Login with your password ",Toast.LENGTH_SHORT).show();
                   // progressBar.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                    goToLogin();
                }
                else {
                    Toast.makeText(getApplicationContext(),"please enter the valid otp or click on resend ",Toast.LENGTH_SHORT).show();
                  //  progressBar.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                    View view=getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    onClick(view);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getCurrentFocus(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }


    //resend otp
    public void resendProcess()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESENDOTP_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
              //  progressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                if(resp.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(),"Click on verify button",Toast.LENGTH_SHORT).show();
                    onClick(getCurrentFocus());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Service unavailable,Try again after some time",Toast.LENGTH_SHORT).show();
                    View view=getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    onClick(view);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
              //  progressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getCurrentFocus(), "Connection Problem", Snackbar.LENGTH_LONG).show();


            }
        });
    }


    private void goToLogin(){

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("fragment",1);
        startActivity(intent);
    }

}


