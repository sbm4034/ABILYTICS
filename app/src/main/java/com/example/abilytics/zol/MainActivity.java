package com.example.abilytics.zol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
//    NavigationView navigationView;
//    private TextView tv_emailnav;


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
        initFragment();

        //initNavigationDrawer();

    }


   /* public void initNavigationDrawer() {

        final NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();
                Fragment fr;

                switch (id){
                    case R.id.home:
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putBoolean(Constants.IS_LOGGED_IN,false);
                        editor.putString(Constants.EMAIL,"");
                        editor.putString(Constants.UNIQUE_ID,"");
                        editor.putString(Constants.NAME,"");
                        editor.apply();
                            fr = new LoginFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_frame, fr);
                            ft.commit();
                            drawerLayout.closeDrawers();

                            //Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                            break;



                    case R.id.logout:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.exit:
                        drawerLayout.closeDrawers();
                        finish();
                        break;

                }
                return true;
            }
        });
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.show, R.string.close) {

                @Override
                public void onDrawerClosed(View v) {
                    super.onDrawerClosed(v);
                }

                @Override
                public void onDrawerOpened(View v) {
                    super.onDrawerOpened(v);
                }
            };
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

            actionBarDrawerToggle.syncState();
        }




    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if(count==0) {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
        else {
            getFragmentManager().popBackStack();
        }

    }*/


    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            Intent intent=new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        }else {
            fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,fragment);
            ft.commit();
        }

    }

}
