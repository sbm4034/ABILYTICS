package com.Wipocab.abilytics.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.Wipocab.abilytics.app.Animations.GradientBackgroundPainter;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private GradientBackgroundPainter gradientBackgroundPainter;


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


        View backgroundImage = findViewById(R.id.root_view);

        final int[] drawables = new int[11];
        drawables[0] = R.drawable.gradients_1;
        drawables[1] = R.drawable.gradients_2;
        drawables[2] = R.drawable.gradients_3;
        drawables[3] = R.drawable.gradient_4;
        drawables[4] = R.drawable.gradient_5;
        drawables[5] = R.drawable.gradient_6;
        drawables[6] = R.drawable.gradient_7;
        drawables[7] = R.drawable.gradient_8;
        drawables[8] = R.drawable.gradient_9;
        drawables[9] = R.drawable.gradient_10;
        drawables[10] = R.drawable.gradient_11;


        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


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
        ft.setCustomAnimations(R.animator.fade_in,R.animator.fade_out);
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();
    }

}
