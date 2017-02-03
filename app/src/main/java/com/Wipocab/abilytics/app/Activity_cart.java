package com.Wipocab.abilytics.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.Wipocab.abilytics.app.Adapters.*;
import com.Wipocab.abilytics.app.Animations.GradientBackgroundPainter;
import com.Wipocab.abilytics.app.Model.*;
;


import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_cart extends AppCompatActivity {
    GradientBackgroundPainter gradientBackgroundPainter;
    RecyclerView recyclerview;
    ProgressBar progressBar;
    ArrayList<ProductVersion> products;
    CartAdapter cartAdapter;
    Toolbar ordertoolbar;  Button btnorder;CoordinatorLayout coordinatorLayout;ArrayList<String> idsA,noiA;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ;
        setupbackground();
        initView();
        loadJson();


    }

    private void setupbackground() {
        View backgroundImage = findViewById(R.id.coordinator_cart);

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


    private void initView() {
        pref=getApplicationContext().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        btnorder=(Button)findViewById(R.id.order_btn);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator_cart);
        recyclerview= (RecyclerView) findViewById(R.id.recycler_view_cart);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutmanager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);
        progressBar=(ProgressBar)findViewById(R.id.progress_Spinner_cart);
        progressBar.setVisibility(View.VISIBLE);


    }

    private void loadJson() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user =new User();
        user.setEmail(pref.getString(Constants.EMAIL," "));
        Log.d("Email",Constants.EMAIL);

        final ServerRequest request=new ServerRequest();
        request.setOperation(Constants.showcart);
        request.setUser(user);
        RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
        Call<ProductResponse> call =requestInterface.operation(request);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                idsA=new ArrayList<String>();
                noiA=new ArrayList<String>();
                progressBar.setVisibility(View.INVISIBLE);
                ProductResponse productResponse =response.body();
               products=new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
               for(int i=0;i<products.size();i++){
                    idsA.add(products.get(i).getP_id());
                    noiA.add("1");
                }
                cartAdapter=new CartAdapter(products, getBaseContext(), new CartAdapter.onClickremove() {
                    @Override
                    public void onClickremovelist(int pos) {
                        idsA.remove(pos);
                        noiA.remove(pos);

                    }

                    @Override
                    public void ordercart(ArrayList<String> ids, ArrayList<String> nois) {
                       ordermycart(ids,nois);
                    }

                    @Override
                    public void spinnerchange(int i, String noi) {
                        noiA.add(i,noi);
                    }
                });
                recyclerview.setAdapter(cartAdapter);

                Toast.makeText(getApplicationContext(),"Cart loaded ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(coordinatorLayout,"Cart Failure", Snackbar.LENGTH_SHORT).show();


            }
        });


    }

    public void ordermycart(final ArrayList<String> ids, final ArrayList<String> nois){
            btnorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog progressDialog=new ProgressDialog(Activity_cart.this);
                    progressDialog.setMessage("Ordering items");
                    progressDialog.show();
                    for (int i = 0; i < idsA.size(); i++) {
                     Log.d("IDS",idsA.get(i));
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        User user = new User();
                        user.setEmail(Constants.EMAIL);
                        user.setP_id(String.valueOf(Integer.parseInt(idsA.get(i))));
                        user.setNoi(Integer.parseInt(noiA.get(i)));
                        final ServerRequest request = new ServerRequest();
                        request.setOperation(Constants.ordercart);
                        request.setUser(user);
                        RequestInterfaceProducts requestInterface = retrofit.create(RequestInterfaceProducts.class);
                        Call<ProductResponse> call =requestInterface.operation(request);
                        call.enqueue(new Callback<ProductResponse>() {
                            @Override
                            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                                progressDialog.dismiss();
                                Snackbar.make(coordinatorLayout, "Order placed ", Snackbar.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<ProductResponse> call, Throwable t) {
                                progressDialog.dismiss();
                                Snackbar.make(coordinatorLayout, "Connection problem", Snackbar.LENGTH_LONG).show();

                            }
                        });


                    }
                }
            });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
