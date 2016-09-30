package com.example.abilytics.zol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

public class LoginFragment  extends Fragment implements View.OnClickListener{
    private AppCompatButton btn_login;

    private EditText et_email,et_password;
    private TextView tv_register;
    private TextView tv_reset;
    private TextInputLayout textInputEmail,textInputpassword;
    private ProgressBar progress;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

//        pref = getActivity().getPreferences(0);
        pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);

        btn_login = (AppCompatButton)view.findViewById(R.id.btn_login);
        tv_register = (TextView)view.findViewById(R.id.tv_register);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
       textInputEmail=(TextInputLayout)view.findViewById(R.id.textInputEmail);
        textInputpassword=(TextInputLayout)view.findViewById(R.id.textInputPassword);
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));

        progress = (ProgressBar)view.findViewById(R.id.progress);
        tv_reset = (TextView)view.findViewById(R.id.tv_reset);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_login:
                if(submitform()) {
                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();

                    if (!email.isEmpty() && !password.isEmpty()) {

                        progress.setVisibility(View.VISIBLE);
                        loginProcess(email, password);

                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.tv_reset:
                goToResetPassword();
                break;
        }
    }
    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.putString(Constants.WALLET,resp.getUser().getMoney());
                    editor.apply();
                    goToProfile();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void goToRegister(){

        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,register);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void goToResetPassword(){

        Fragment reset = new ResetPasswordFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,reset);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void goToProfile(){

        Intent intent=new Intent(getActivity(),Profile.class);
        startActivity(intent);
    }











    // validate form

    private boolean submitform() {

        if (!validateEmail()) {
            return true;
        }

        if (!validatePassword()) {
            return true;
        }

        return true;
    }



    private boolean validateEmail() {
        String emails = et_email.getText().toString().trim();

        if (emails.isEmpty() || !isValidEmail(emails)) {
            textInputEmail.setError(getString(R.string.error_email));
            requestFocus(et_email);
            return false;
        } else {
            textInputEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            textInputpassword.setError(getString(R.string.error_pass));
            requestFocus(et_password);
            return false;
        } else {
            textInputpassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_email:
                    validateEmail();
                    break;
                case R.id.et_password:
                    validatePassword();
                    break;
            }
        }
    }


}
