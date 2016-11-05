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

import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.ProductFragment;
import com.Wipocab.abilytics.app.R;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Callback;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<ProductVersion> products;
    Context context;
    onClickWish listeners;


    public interface onClickWish{
        public void onClickWishlist(int pos);
        void onClickremovewish(int pos);
    }

    public DataAdapter(ArrayList<ProductVersion> products, Context context, onClickWish listeners) {

        this.products=products;
        this.context=context;
        this.listeners=listeners;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.p_name.setText(products.get(position).getP_name());
        holder.p_info.setText(products.get(position).getP_info());
        holder.p_sold.setText(products.get(position).getP_sold());


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView p_name,p_sold,p_info;
        private ImageButton btnLike;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            p_name=(TextView)itemView.findViewById(R.id.p_name);
            p_info=(TextView)itemView.findViewById(R.id.p_info);
            p_sold=(TextView)itemView.findViewById(R.id.p_sold);
            btnLike=(ImageButton) itemView.findViewById(R.id.btnLike);

        }

        @Override
        public void onClick(View view) {
            int pos= getAdapterPosition();
           // Toast.makeText(view.getContext(),products.get(pos).getP_name(),Toast.LENGTH_SHORT).show();
            if(products.get(pos).isliked()) {
                products.get(pos).setIsliked(false);
                listeners.onClickremovewish(pos);
            }else {
                products.get(pos).setIsliked(true);
                animateHeartButton();
                listeners.onClickWishlist(pos);
            }
            btnLike.setImageResource(products.get(pos).isliked() ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
            //savetosharedpref(view,pos);


        }

        private void animateHeartButton() {
             final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
              final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
             final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(btnLike, "rotation", 0f, 360f);
            rotationAnim.setDuration(300);
            rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(btnLike, "scaleX", 0.2f, 1f);
            bounceAnimX.setDuration(300);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(btnLike, "scaleY", 0.2f, 1f);
            bounceAnimY.setDuration(300);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
            bounceAnimY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    btnLike.setImageResource(R.drawable.ic_heart_red);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });

            animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
            animatorSet.start();
        }
    }

    private void savetosharedpref(View view,int pos) throws IOException {
        Context context=view.getContext();
       SharedPreferences pref=context.getSharedPreferences("ABC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =pref.edit();
        ArrayList<String> list  =new ArrayList<>();
        list.add(products.get(pos).getP_name());
      //  Set<String> set=new HashSet<>();
        //set.addAll(list);
        //editor.putStringSet("wishlist",set);
        //editor.apply();

     //   pref.getStringSet("ABC",set);
       // for (String temp:set){
         //   Toast.makeText(context,temp,Toast.LENGTH_SHORT).show();
       // }

    }

}
