package com.Wipocab.abilytics.application;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.Wipocab.abilytics.application.Model.ServerRequest;
import com.Wipocab.abilytics.application.Model.ServerResponse;
import com.Wipocab.abilytics.application.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class changepasswordfrag  extends Fragment implements View.OnClickListener{
    EditText et_old_password,et_new_password;
    AppCompatButton btn_pswd;
    SharedPreferences pref;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.frag_change_password,container,false);
      //  ((Profile) getActivity()).setActionBarTitle("Wipocab");
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        btn_pswd=(AppCompatButton)view.findViewById(R.id.btn_psd);
        btn_pswd.setOnClickListener(this);

        materialDialog=new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        dialog=materialDialog.build();

    }

    private void showDialog(){

        String old_password = et_old_password.getText().toString();
        String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){
                    changePasswordProcess(pref.getString(Constants.EMAIL,""),old_password,new_password);
                    dialog.show();

                }else {
                    Snackbar.make(getView(),"Fields are empty",Snackbar.LENGTH_LONG).show();
                }
            }

    private void changePasswordProcess(String email,String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();



                }else {
                    Snackbar.make(getView(),resp.getMessage(),Snackbar.LENGTH_LONG).show();

                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(),"Connection Problem",Snackbar.LENGTH_LONG).show();
                dialog.dismiss();

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_psd:
                showDialog();
                break;
        }

    }
}
