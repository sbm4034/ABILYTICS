package com.Wipocab.abilytics.app.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.R;

import java.io.IOException;
import java.util.ArrayList;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.p_name.setText(products.get(position).getProduct_name());
        //holder.p_info.setText(products.get(position).getP_info());
        //holder.p_sold.setText(products.get(position).getP_sold());


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView p_name, p_sold, p_info;
        private ImageButton btnLike;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            p_name = (TextView) itemView.findViewById(R.id.p_name);
            //p_info = (TextView) itemView.findViewById(R.id.p_info);
            //p_sold = (TextView) itemView.findViewById(R.id.p_sold);
            //btnLike = (ImageButton) itemView.findViewById(R.id.btnLike);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            int pid = Integer.parseInt(products.get(pos).getP_id());
            int subpid= Integer.parseInt(products.get(pos).getSubproducts_id());
            listeners.onClickprolistener(pos,pid,subpid);

        }

    }
}