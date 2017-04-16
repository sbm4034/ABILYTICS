package com.Wipocab.abilytics.application;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Edit_profile_frag extends Fragment implements View.OnClickListener{
    private AppCompatButton btn_submit;
    private EditText et_name,et_dob,et_place;
    SharedPreferences pref;
    private ProgressBar progress;
    private String email;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        //((Profile) getActivity()).setActionBarTitle("Abilytics India Ltd.");
        TextView abs =(TextView)getActivity().findViewById(R.id.abs);
        abs.setText("Edit Profile");
        initViews(view);
        return view;
    }
    public void initViews(View view)
    {

        btn_submit = (AppCompatButton)view.findViewById(R.id.btn_submit);
        et_name = (EditText)view.findViewById(R.id.et_name);


        et_dob = (EditText)view.findViewById(R.id.et_dob);
        et_place = (EditText)view.findViewById(R.id.et_place);


        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_submit.setOnClickListener(this);

        materialDialog=new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        dialog=materialDialog.build();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView texProfile=(TextView)view.findViewById(R.id.textProfile);
        Typeface font= Typeface.createFromAsset(getActivity().getAssets(),"Asiago.ttf");
        texProfile.setTypeface(font);

        pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        et_name.setText(pref.getString(Constants.NAME,""));
        email=pref.getString(Constants.EMAIL,"");
        et_dob.setText(pref.getString(Constants.DOB,""));
        et_place.setText(pref.getString(Constants.PLACE,""));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_submit:

                String name = et_name.getText().toString();
                String dob=et_dob.getText().toString();
                String place=et_place.getText().toString();
               // progress.setVisibility(View.VISIBLE);
                dialog.show();
                submit(name,dob,place,email);
                break;

        }
    }
    public void submit(String name,String dob,String place, String email)

    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDob(dob);
        user.setPlace(place);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.DOB,resp.getUser().getDob());
                    editor.putString(Constants.PLACE,resp.getUser().getPlace());


                    editor.apply();
                    goToProfile();

                }
               // progress.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

              //  progress.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();

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
