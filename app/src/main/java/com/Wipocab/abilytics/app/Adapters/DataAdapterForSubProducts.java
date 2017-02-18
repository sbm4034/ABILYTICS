package com.Wipocab.abilytics.app.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Wipocab.abilytics.app.ActProductInfo;
import com.Wipocab.abilytics.app.Activity_cart;
import com.Wipocab.abilytics.app.Constants;
import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.User;
import com.Wipocab.abilytics.app.R;
import com.Wipocab.abilytics.app.RequestInterfaceProducts;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataAdapterForSubProducts extends RecyclerView.Adapter<DataAdapterForSubProducts.ViewHolder> {
    private ArrayList<ProductVersion> products;
    Context context;
    onClickWish listeners;

    public DataAdapterForSubProducts(ArrayList<ProductVersion> products, Activity activity) {

        this.products = products;
        this.context = context;
    }

    public DataAdapterForSubProducts() {

    }


    public interface onClickWish {
        public void onClickprolistener(int pos, int pid, int subpid);
    }

    public DataAdapterForSubProducts(ArrayList<ProductVersion> products, Context context, onClickWish listeners) {

        this.products = products;
        this.context = context;
        this.listeners = listeners;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.p_code.setText(String.format("Product Code: %s",products.get(position).getProduct_code()));
        holder.p_nodia.setText(String.format("Number/Dia of Wire (mm): - %s",products.get(position).getNumberperDiaofWire()));
        holder.p_area.setText(String.format("Number Area of Cond.(Sq. mm)\t: %s",products.get(position).getNumberperDiaofWire()));
        holder.pricepercoil.setText(String.format("Price/Coil: %s",products.get(position).getPriceperCoil()));
        holder.pkg.setText(String.format("Pkg: %s",products.get(position).getPkg()));
        holder.lenght.setText(String.format("Lenght: %s",products.get(position).getLength()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView p_code, p_nodia,p_area,pkg,lenght,pricepercoil ;
        private Button btnAddToCart;

        public ViewHolder(View itemView) {
            super(itemView);
           // itemView.setOnClickListener(this);
            p_code=(TextView)itemView.findViewById(R.id.p_code);
            p_nodia=(TextView)itemView.findViewById(R.id.p_noperdia);
            p_area=(TextView)itemView.findViewById(R.id.p_noArea);
           pkg=(TextView)itemView.findViewById(R.id.p_pkg);
            lenght=(TextView)itemView.findViewById(R.id.p_noLength);
            pricepercoil=(TextView)itemView.findViewById(R.id.p_price);
            btnAddToCart=(Button)itemView.findViewById(R.id.addtoCart);
            btnAddToCart.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
//            int pid = Integer.parseInt(products.get(pos).getP_id());
     //       int subpid= Integer.parseInt(products.get(pos).getSubproducts_id());
    //        listeners.onClickprolistener(pos,pid,subpid);
         //   ShowlengthDialog(view,pos);
           // AddToCart(view,pos);



        }

    }




    private void AddToCart(View view, int pos) {
        SharedPreferences preferences=view.getContext().getApplicationContext().getSharedPreferences("ABC",Context.MODE_PRIVATE);
        final ProgressDialog progressDialog=new ProgressDialog(view.getContext());
        progressDialog.setMessage(products.get(pos).getP_name() + " adding to your cart");
        progressDialog.show();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user =new User();
        user.setProduct_code(products.get(pos).getProduct_code());
        user.setEmail(preferences.getString(Constants.EMAIL," "));
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
                Toast.makeText(context,"Successfully added to cart",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"Failed ",Toast.LENGTH_SHORT).show();


            }
        });
    }
    }