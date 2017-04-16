package com.Wipocab.abilytics.application;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Wipocab.abilytics.application.Model.ServerRequest;
import com.Wipocab.abilytics.application.Model.ServerResponse;
import com.Wipocab.abilytics.application.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener{

    private AppCompatButton btn_reset;
    private EditText et_email,et_code,et_password;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_password_reset,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        btn_reset = (AppCompatButton)view.findViewById(R.id.btn_reset);
        et_code = (EditText)view.findViewById(R.id.et_code);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        TextView textReset=(TextView)view.findViewById(R.id.textReset);
        Typeface font2= Typeface.createFromAsset(getActivity().getAssets(),"billabong.ttf");
        textReset.setTypeface(font2);


        materialDialog=new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        dialog=materialDialog.build();
        keyBoard();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_reset:

                if(!isResetInitiated) {

                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                       // progress.setVisibility(View.VISIBLE);
                        dialog.show();
                        initiateResetPasswordProcess(email);
                      //  et_email.setVisibility(View.INVISIBLE);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                } else {

                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();

                    if(!code.isEmpty() && !password.isEmpty()){

                        dialog.show();
                       // progress.setVisibility(View.VISIBLE);
                        finishResetPasswordProcess(email,code,password);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;
        }
    }

    private void initiateResetPasswordProcess(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_INITIATE);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    //tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    isResetInitiated = true;
                   // startCountdownTimer();

                } else {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                   // Toast.makeText(getActivity(),resp.getMessage(),Toast.LENGTH_SHORT).show();

                }
               // progress.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

               // progress.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), "Internet not Connected", Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void finishResetPasswordProcess(String email,String code, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setEmail(email);
        user.setOtp(code);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_FINISH);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    //countDownTimer.cancel();
                    isResetInitiated = false;
                    goToLogin();

                } else {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }
               // progress.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Log.d(Constants.TAG, resp.getMessage());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.dismiss();
               // progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    /*private void startCountdownTimer(){
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Snackbar.make(getView(), "Time Out ! Request again to reset password.", Snackbar.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }
    */

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fade_in,R.animator.fade_out);
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    //to handle click automatically
    private void keyBoard() {
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    btn_reset.performClick();
                    return  true;
                }
                return false;
            }
        });
        et_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    btn_reset.performClick();
                    return  true;
                }
                return false;
            }
        });

    }
}