package com.Wipocab.abilytics.app;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;
import com.Wipocab.abilytics.app.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transfer_frag extends Fragment  implements View.OnClickListener{
    SharedPreferences pref;
    String email,no_to_send;
    MaterialDialog.Builder materialDialog; MaterialDialog mdialog;
    EditText rec_no,points;
    AppCompatButton send_pts;
    String points_to_send;
    TextView tv_wallet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transfer_frag, container, false);
        initViews(view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pref = getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        email= pref.getString(Constants.EMAIL,"");



    }

    private void initViews(View view) {
        rec_no = (EditText) view.findViewById(R.id.rec_no);
        send_pts=(AppCompatButton) view.findViewById(R.id.send_pts);
        points=(EditText)view.findViewById(R.id.points);
        tv_wallet=(TextView)view.findViewById(R.id.tv_wallet);

        send_pts.setOnClickListener(this);

        materialDialog=new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        mdialog=materialDialog.build();
        keyBoard();
    }


    //to handle click automatically
    private void keyBoard() {
        points.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    send_pts.performClick();
                    return  true;
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.send_pts:

                mdialog.show();
                sendpoints();
                break;

        }
    }

    private void sendpoints() {
        no_to_send=rec_no.getText().toString();
        points_to_send = points.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        final User user=new User();
        user.setEmail(email);
        user.setPtssend(points_to_send);
        user.setNosend(no_to_send);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.TRANSFER_POINTS);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                mdialog.dismiss();
                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.WALLET,resp.getUser().getMoney());
                    editor.apply();
                   // tv_wallet.setText("Pts: "+pref.getString(Constants.WALLET,""));
                    Toast toast=Toast.makeText(getActivity(),"Points removed from Wallet:" +user.getPtssend(),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else if(resp.getResult().equals(Constants.FAILURE)){
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                else
                {   Log.d("kfkef","fwfjkbsfkdsdfjk");
                    Snackbar.make(getView(), " mobile number does not exist", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();
                mdialog.dismiss();

            }
        });
    }
}
