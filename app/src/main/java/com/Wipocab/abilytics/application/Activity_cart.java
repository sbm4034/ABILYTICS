package com.Wipocab.abilytics.application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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


import com.Wipocab.abilytics.application.Adapters.*;
import com.Wipocab.abilytics.application.Animations.GradientBackgroundPainter;
import com.Wipocab.abilytics.application.Model.*;
import com.afollestad.materialdialogs.MaterialDialog;
;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

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
    ArrayList<ArrayList<ProductVersion>> prod;
    CartAdapter cartAdapter;
    Toolbar ordertoolbar;  Button btnorder;CoordinatorLayout coordinatorLayout;ArrayList<String> idsA,noiA;Button p_inc;Button p_dec;
    SharedPreferences pref;
    ArrayList<String> price;
    MaterialDialog.Builder materialDialog;
     MaterialDialog mdialog;
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
        p_inc=(Button)findViewById(R.id.inc);
        p_dec=(Button)findViewById(R.id.dec);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator_cart);
        recyclerview= (RecyclerView) findViewById(R.id.recycler_view_cart);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutmanager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);
        progressBar=(ProgressBar)findViewById(R.id.progress_Spinner_cart);
       progressBar.setVisibility(View.VISIBLE);
        materialDialog  = new MaterialDialog.Builder(getApplicationContext())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
   mdialog = materialDialog.build();




    }

    private void loadJson() {
//       mdialg.show();
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
                price=new ArrayList<String>();
               progressBar.setVisibility(View.INVISIBLE);
                ProductResponse productResponse =response.body();
                String b=productResponse.toString();
                Log.d("ksn",b);
               products=new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
                pref = getSharedPreferences("ABC", Context.MODE_PRIVATE);
                String savedstring=pref.getString(Constants.cartnoi,"");
                StringTokenizer st = new StringTokenizer(savedstring,"");
                Log.d("UOOOOOOOOOOO", st.toString());
                for (int k = 0; k < products.size(); k++) {
                    //products.get(k).setNoi();
                    noiA.add(st.nextToken());
                }
               for(int i=0;i<products.size();i++){
                    idsA.add(products.get(i).getProduct_code());
                   price.add(products.get(i).getCart_price());
                   Log.d("PRICECCC", price.get(i));

                }
                cartAdapter=new CartAdapter(products, getBaseContext(), new CartAdapter.onClickremove() {
                    @Override
                    public void onClickremovelist(int pos) {
                        idsA.remove(pos);
                        noiA.remove(pos);
                        price.remove(pos);

                    }

                    @Override
                    public void ordercart(ArrayList<String> ids, ArrayList<String> nois,ArrayList<String> price) {
                       ordermycart(ids,nois,price);
                    }

                    @Override
                    public void textChanged(int pos,String noip) {
                        noiA.add(pos,noip);
                        //Toast.makeText(getApplicationContext(),noip,Toast.LENGTH_LONG).show();

                    }

                });
                recyclerview.setAdapter(cartAdapter);

                Toast.makeText(getApplicationContext(),"Cart loaded ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
           progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(coordinatorLayout,"Connection Problem", Snackbar.LENGTH_SHORT).show();



            }
        });


    }

    public void ordermycart(final ArrayList<String> ids, final ArrayList<String> nois,final ArrayList<String> pric){
            btnorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                int total=0;
              final ProgressDialog pd=new ProgressDialog(Activity_cart.this,R.style.AppCompatAlertDialogStyle);
                    pd.setMessage("Ordering your items");
                   pd.show();
                    StringBuffer buffer=new StringBuffer();
                    for (int i = 0; i < idsA.size(); i++) {
                        buffer.append(idsA.get(i));
                        buffer.append(" ("+noiA.get(i)+" )");
                        buffer.append(" ");
                        buffer.append("Price");
                        buffer.append(price.get(i));
                        total=total + Integer.parseInt(noiA.get(i))*Integer.parseInt(price.get(i));

                    }
                    buffer.append(String.format("Total Price \n %s",String.valueOf(total)));
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        User user = new User();
                        user.setEmail(pref.getString(Constants.EMAIL," "));
                        user.setProduct_code(String.valueOf(buffer));
                        ServerRequest request = new ServerRequest();
                        request.setOperation(Constants.ordercart);
                        request.setUser(user);
                        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
                        Call<ServerResponse> call =requestInterface.operation(request);
                    final int finalTotal = total;
                    call.enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                               // mdialog.dismiss();
                                pd.dismiss();
                                response.message();
                                Snackbar.make(coordinatorLayout, "order placed "+String.valueOf(finalTotal), Snackbar.LENGTH_SHORT).show();
                                loadJson();


                            }


                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {
                             // mdialog.dismiss();
                                pd.dismiss();
                                Snackbar.make(coordinatorLayout, t.toString(), Snackbar.LENGTH_LONG).show();

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

}
