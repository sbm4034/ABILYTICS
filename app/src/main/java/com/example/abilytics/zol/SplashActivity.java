package com.example.abilytics.zol;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                   TaskStackBuilder.create(getApplication())
                           .addNextIntentWithParentStack(new Intent(getApplication(), MainActivity.class))
                           //.addNextIntent(new Intent(getApplication(), IntroActivity.class))
                           .startActivities();
               }

           }
       },1500);






    }

}
