package com.Wipocab.abilytics.app;


import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;
import com.Wipocab.abilytics.app.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment  extends Fragment implements View.OnClickListener {
    private TextView tv_name, tv_email, tv_message, tv_wallet, tv_promo_money;
    TextView abs;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password, btn_logout, btn_redeem;
    private EditText redeeemText;
    String greeting = null;
    MaterialDialog.Builder materialDialog;
    MaterialDialog mdialog;
    Typeface font,font2,font3,font4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //((Profile) getActivity()).setActionBarTitle("Wipocab");
        abs=(TextView)getActivity().findViewById(R.id.abs);
         font= Typeface.createFromAsset(getActivity().getAssets(),"Pacifico.ttf");
        abs.setTypeface(font);
       font2 = Typeface.createFromAsset(getActivity().getAssets(),"Asiago.ttf");
        font3 = Typeface.createFromAsset(getActivity().getAssets(),"Alex.ttf");
        font4 = Typeface.createFromAsset(getActivity().getAssets(),"CaviarDreams.ttf");


        initViews(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String greets = getTimeFromAndroid();
        abs.setText(greets);;
        pref = getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        tv_name.setText( pref.getString(Constants.NAME, ""));
        tv_name.setTypeface(font2);
        tv_email.setText(pref.getString(Constants.EMAIL, ""));
        tv_wallet.setText("Pts: " + pref.getString(Constants.WALLET, ""));
        tv_wallet.setTypeface(font4);

    }

    private void initViews(View view) {
        TextView text_promo_code=(TextView)view.findViewById(R.id.text_promo_code);
        text_promo_code.setTypeface(font3);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);
        btn_redeem = (AppCompatButton) view.findViewById(R.id.btn_redeem);
        redeeemText = (EditText) view.findViewById(R.id.redeem_text);
        //btn_redeem.setTypeface(font4);
        btn_redeem.setOnClickListener(this);
        redeeemText.addTextChangedListener(new FourDigitCardFormatWatcher());
        redeeemText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
        redeeemText.getBackground().setAlpha(70);

        tv_email.setVisibility(View.INVISIBLE);

        materialDialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        mdialog = materialDialog.build();
        keyBoard();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_redeem:
                //    progress.setVisibility(View.VISIBLE);
                mdialog.show();
                redeem();
                break;

        }
    }

    //redeem code from server
    private void redeem() {
        String email = tv_email.getText().toString();


        String raw = redeeemText.getText().toString();
        String promo_code=raw.replace("-","");
        Log.e("Rdeem ", promo_code);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        final User user = new User();
        user.setEmail(email);
        user.setcode(promo_code);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REDEEM_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // progress.setVisibility(View.INVISIBLE);
                mdialog.dismiss();
                ServerResponse resp = response.body();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                //    tv_promo_money.setText(resp.getUser().getPromo_money());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.WALLET, resp.getUser().getMoney());
                    editor.apply();
                   // tv_wallet.setText("Pts: " + resp.getUser().getMoney());
                    tv_wallet.setText("Pts: " + pref.getString(Constants.WALLET, ""));
                    Toast toast = Toast.makeText(getActivity(), "Points Added To Wallet:" + resp.getUser().getPromo_money(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Log.d("kfkef", "fwfjkbsfkdsdfjk");
                    Snackbar.make(getView(), "Redeem point does not exist", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();
                //  progress.setVisibility(View.INVISIBLE);
                mdialog.dismiss();
            }
        });


    }


    //time of day

    private String getTimeFromAndroid() {
        String greeting = null;
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);


        if (hours >= 0 && hours <= 12) {
            greeting = "Good Morning";
        } else if (hours >= 12 && hours <= 16) {
            greeting = "Good Afternoon";
        } else if (hours >= 16 && hours <= 21) {
            greeting = "Good Evening";
        } else if (hours >= 21 && hours <= 24) {
            greeting = "Good Night";
        }
        return greeting;
    }

    //to handle click automatically
    private void keyBoard() {
        redeeemText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    btn_redeem.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Formats the watched EditText to a credit card number
     */
    public static class FourDigitCardFormatWatcher implements TextWatcher {

        // Change this to what you want... ' ', '-' etc..
        private static final char space = '-';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 5) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed.
            if (s.length() > 0 && (s.length() % 5) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }
    }


    }
