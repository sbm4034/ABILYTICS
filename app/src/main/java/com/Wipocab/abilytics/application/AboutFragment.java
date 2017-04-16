package com.Wipocab.abilytics.application;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment  implements View.OnClickListener{

    TextView clickEmail,mbl;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
       // ((Profile) getActivity()).setActionBarTitle("Contact Us");
        TextView abs =(TextView)getActivity().findViewById(R.id.abs);
        abs.setText("Contact us");
        initView(view);
        return  view;
    }

    private void initView(View v) {
        clickEmail=(TextView) v.findViewById(R.id.clickEmail);

        clickEmail.setOnClickListener(this);
        //clickEmail.setPaintFlags(clickEmail.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        mbl=(TextView) v.findViewById(R.id.mbl);

        mbl.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clickEmail:
                clickEmail();
                break;
            case R.id.mbl:
                dialPhoneNumber();
                break;


        }

    }

    private void clickEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "info@abilytics.in");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Contact");

        startActivity(Intent.createChooser(intent, "Send Email"));

    }
    public void dialPhoneNumber() {
        String phoneNumber="9999697908";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
