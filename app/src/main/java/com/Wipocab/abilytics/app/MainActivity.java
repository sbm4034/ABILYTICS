package com.Wipocab.abilytics.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref=getSharedPreferences("ABC",MODE_PRIVATE);
        //pref = getPreferences(0);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        if(getIntent().getBooleanExtra("EXIT",false))
        {
            finish();
        }
        else if(getIntent().getIntExtra("fragment",0)==1)
        {
            goToLogin();
        }
        initFragment();

        //initNavigationDrawer();

    }



    private void initFragment(){

        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            Intent intent=new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        }else {
           goToLogin();
        }

    }
    public void goToLogin()
    {   Fragment fragment;
        fragment = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();
    }

}