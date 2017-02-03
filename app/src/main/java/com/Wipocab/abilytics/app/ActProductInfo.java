package com.Wipocab.abilytics.app;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Adapters.DataAdapter;
import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.User;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActProductInfo extends AppCompatActivity {
    String p_id;
    TextView A_name,A_diameter,A_length; LinearLayout act_product;
    Button p_cart;
    private ProductVersion product;
    private ArrayList<ProductVersion> products;
    ProgressBar progressBar;
    RatingBar ratingBar;
    CoordinatorLayout coordinatorLayout;


    MaterialDialog progressbar;
    OkHttpClient client;Context actcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_product_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        p_id=getIntent().getStringExtra("DATAINTENT");
        Button gotoorderbtn=(Button)findViewById(R.id.btngorder);
        gotoorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Activity_cart.class);
                startActivity(intent);
            }
        });


        initView();
        loadJson();



    }


    private void initView() {
         actcontext=getApplicationContext();
        act_product=(LinearLayout)findViewById(R.id.content_act_product_info);
        act_product.setVisibility(View.INVISIBLE);
         A_name=(TextView)findViewById(R.id.A_name);
         A_diameter=(TextView)findViewById(R.id.A_diameter);
         A_length=(TextView)findViewById(R.id.A_length);
          p_cart=(Button)findViewById(R.id.btncart);
        progressBar=(ProgressBar)findViewById(R.id.progress_Spinner_info);
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator_product_info);


    }




    private void loadJson() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user =new User();
        user.setPid(Integer.parseInt(p_id));
        final ServerRequest request=new ServerRequest();
        request.setOperation(Constants.getproductdetail);
        request.setUser(user);
        RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
        Call<ProductResponse> response=requestInterface.operation(request);
        response.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                act_product.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                ProductResponse productResponse =response.body();
                products=new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
                A_name.setText(products.get(0).getProduct_name());
                A_length.setText(products.get(0).getProduct_length());
                A_diameter.setText(products.get(0).getProduct_diameter());


                plceCart();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                SnackbarFailed();


            }
        });


    }

    private void plceCart() {

        p_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(ActProductInfo.this);
                progressDialog.setMessage(products.get(0).getP_name() + " adding to your cart");
                progressDialog.show();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                User user =new User();
                user.setPid(Integer.parseInt(p_id));
                 user.setEmail("7827789246");
                user.setNoi(Integer.parseInt("1"));
                final ServerRequest request=new ServerRequest();
                request.setOperation(Constants.addtocart);
                request.setUser(user);
                RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
                Call<ProductResponse> response=requestInterface.operation(request);
                response.enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        progressDialog.dismiss();
                        Snackbar.make(coordinatorLayout,"Successfully added to cart", Snackbar.LENGTH_INDEFINITE)
                                .setAction("CART", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(getApplicationContext(),Activity_cart.class);
                                        startActivity(intent);
                                    }
                                }).show();

                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        SnackbarFailed();

                    }
                });
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

   public void SnackbarFailed(){
       Snackbar.make(coordinatorLayout,"Check connection", Snackbar.LENGTH_INDEFINITE)
               .setAction("RETRY", new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent= new Intent(getApplicationContext(),ActProductInfo.class);
                       startActivity(intent);
                       finish();
                   }
               })
               .show();

    }








}
