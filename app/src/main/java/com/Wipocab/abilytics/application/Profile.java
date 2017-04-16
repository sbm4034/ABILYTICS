package com.Wipocab.abilytics.application;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Wipocab.abilytics.application.Animations.GradientBackgroundPainter;
import com.afollestad.materialdialogs.MaterialDialog;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;


public class Profile extends AppCompatActivity{
    private SharedPreferences pref;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    NavigationView navigationView;
    private TextView tv_emailnav;
    private LinearLayout header;
    int pointer=0;
    private GradientBackgroundPainter gradientBackgroundPainter;
    private FloatingActionButton fabCreate;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private ViewGroup viewGroup;
    private NavigationView navView;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;
    int n=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        fabCreate=(FloatingActionButton)findViewById(R.id.fab);
        pref=getSharedPreferences("ABC", Context.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        initFragment();
        initNavigationDrawer();
        navText();
        View backgroundImage = findViewById(R.id.drawer);

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

        navView = (NavigationView) findViewById(R.id.navigation_view);
        Menu m = navView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabclicked();
            }
        });
        materialDialog=new MaterialDialog.Builder(getApplicationContext())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        dialog=materialDialog.build();


    }


    private void navText() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        tv_emailnav = (TextView) headerView.findViewById(R.id.tv_emailnav);
        tv_emailnav.setText(pref.getString(Constants.EMAIL, ""));


    }

    public void initNavigationDrawer() {

        final NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fr;
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.home:
                        drawerLayout.closeDrawers();
                        fr = new ProfileFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        if(n==1) {
                            ft.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            ft.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=1;
                        ft.replace(R.id.fragment_frame, fr);
                        ft.commit();
                        break;
                    case R.id.our_poducts:
                        drawerLayout.closeDrawers();
                        fr = new ProductFragment();
                        FragmentTransaction pf = getFragmentManager().beginTransaction();
                        if(n==1) {
                            pf.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            pf.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=2;

                        pf.replace(R.id.fragment_frame, fr,"PF");
                        pf.addToBackStack(null);
                        pf.commit();
                        break;
                    case R.id.transfer:
                        drawerLayout.closeDrawers();
                        fr = new Transfer_frag();
                        FragmentTransaction tf = getFragmentManager().beginTransaction();
                        if(n<=2) {
                            tf.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            tf.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=3;
                        tf.replace(R.id.fragment_frame, fr);
                        tf.addToBackStack(null);
                        tf.commit();
                        break;
                    case R.id.profile:
                        fr = new Edit_profile_frag();
                        FragmentTransaction f = getFragmentManager().beginTransaction();
                        if(n<=3) {
                            f.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            f.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=4;
                        Log.d("Tag", String.valueOf(n));
                        f.replace(R.id.fragment_frame, fr);
                        f.addToBackStack(null);
                        f.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.change_password:
                        fr= new changepasswordfrag();
                        FragmentTransaction o= getFragmentManager().beginTransaction();
                        if(n<=4) {
                            o.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            o.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=5;
                        o.replace(R.id.fragment_frame,fr);
                        o.addToBackStack(null);
                        o.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about_us:
                        fr = new AboutFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        if(n<=5) {
                            fragmentTransaction.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_up_out);
                        }else{
                            fragmentTransaction.setCustomAnimations(R.animator.slide_in_down,R.animator.slide_out_down);
                        }
                        n=6;
                        fragmentTransaction.replace(R.id.fragment_frame, fr);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.log_out:
                        logout();
                        break;

                    case R.id.exit:
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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

    private void initFragment(){
        Fragment fragment;
        fragment = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_up_out);
        ft.replace(R.id.fragment_frame,fragment);


        ft.commit();
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {

        Fragment pf=getFragmentManager().findFragmentByTag("PF");
        if (pf!= null && pf.getChildFragmentManager().getBackStackEntryCount() > 0)
        {
            // Get the fragment fragment manager - and pop the backstack
            pf.getChildFragmentManager().popBackStack();
        }

    else
    {
        getFragmentManager().popBackStack();
    }
}




    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startIntroAnimation() {
      fabCreate.setTranslationY(2 * 72);

        int actionbarSize = dpToPx(56);
        toolbar.setTranslationY(-actionbarSize);
        tv_emailnav.setTranslationY(-actionbarSize);
      //  getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        tv_emailnav.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
      /*  getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                       // startContentAnimation();
                    }
                })
                .start();*/
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        startIntroAnimation();
        return true;
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
       // feedAdapter.updateItems(true);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    private void fabclicked() {

        Intent intent=new Intent(this,Activity_cart.class);
        startActivity(intent);


    }





}
