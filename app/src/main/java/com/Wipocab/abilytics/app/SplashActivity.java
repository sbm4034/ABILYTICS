package com.Wipocab.abilytics.app;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ImageView logo=(ImageView)findViewById(R.id.logo);
        final Animation anim =AnimationUtils.loadAnimation(this,R.anim.scale);
        logo.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

                 TaskStackBuilder.create(getApplication())
                        .addNextIntentWithParentStack(new Intent(getApplication(), MainActivity.class))
                           //.addNextIntent(new Intent(getApplication(), IntroActivity.class))
                          .startActivities();
             //  Intent intent=new Intent(getApplication(),MainActivity.class);
             //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
              // startActivity(intent);
                         finish();


           }
       },1000);






    }

}
