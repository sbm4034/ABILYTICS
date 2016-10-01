package com.example.abilytics.zol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abilytics.zol.Model.ServerRequest;
import com.example.abilytics.zol.Model.ServerResponse;
import com.example.abilytics.zol.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shubham on 10/1/2016.
 */
public class VerifyOtpFragment extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btn_otp;
    EditText otp;
    String o,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_otp);
        btn_otp = (AppCompatButton)findViewById(R.id.btn_otp);
        otp=(EditText)findViewById(R.id.otp);

        btn_otp.setOnClickListener(this);
        email=getIntent().getStringExtra("email");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_otp:
                 o=otp.getText().toString();
                otpProcess();
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
                    goToLogin();
                }
                else {
                    Toast.makeText(getApplicationContext(),"please enter the valid otp or click on resend ",Toast.LENGTH_SHORT).show();
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


                Log.d(Constants.TAG,"failed");
                Snackbar.make(getCurrentFocus(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin(){

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("fragment",1);
        startActivity(intent);
    }

}


