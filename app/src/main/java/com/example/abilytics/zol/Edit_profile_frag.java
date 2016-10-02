package com.example.abilytics.zol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abilytics.zol.Model.ServerRequest;
import com.example.abilytics.zol.Model.ServerResponse;
import com.example.abilytics.zol.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gautam on 10/2/16.
 */

public class Edit_profile_frag extends Fragment implements View.OnClickListener{
    private AppCompatButton btn_submit;
    private EditText et_password,et_name,et_dob,et_place;
    SharedPreferences pref;
    private ProgressBar progress;
    private String email;
    public View onCreateView(LayoutInflater inflater, Bundle savedInstanceState, ViewGroup viewgroup)
    {
        View view = inflater.inflate(R.layout.edit_profile_fragment, viewgroup, false);
        ((Profile) getActivity()).setActionBarTitle("Edit Profile");
        initViews(view);
        return view;
    }
    public void initViews(View view)
    {

        btn_submit = (AppCompatButton)view.findViewById(R.id.btn_register);
        et_name = (EditText)view.findViewById(R.id.et_name);

        et_password = (EditText)view.findViewById(R.id.et_password);
        et_dob = (EditText)view.findViewById(R.id.et_dob);
        et_place = (EditText)view.findViewById(R.id.et_place);
        pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        et_name.setText(pref.getString(Constants.NAME,null));
        email=pref.getString(Constants.EMAIL,null);
        et_dob.setText(pref.getString(Constants.DOB,null));
        et_place.setText(pref.getString(Constants.PLACE,null));

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_submit.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_submit:

                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String dob=et_dob.getText().toString();
                String place=et_place.getText().toString();
                submit(name,password,dob,place,email);
                break;

        }
    }
    public void submit(String name,String password,String dob,String place, String email)

    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setDob(dob);
        user.setPlace(place);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
                goToProfile();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }
    public  void goToProfile()
    {
        Fragment fragment;
        fragment = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();
    }





}
