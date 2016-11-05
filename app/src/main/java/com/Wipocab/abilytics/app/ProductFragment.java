package com.Wipocab.abilytics.app;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Adapters.DataAdapter;
import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment  {
    private RecyclerView recyclerView;
    private ArrayList<ProductVersion> products;
    private DataAdapter adapter;
    ArrayList<String> list;
    SharedPreferences pref;Set<String> set; SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        initView(view);
       list = new ArrayList<>();
        pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
       set =new HashSet<>();
       editor =pref.edit();

        return view;

    }

    private void initView(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutmanager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutmanager);
        loadJson();
    }

    private void loadJson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://abilytics.16mb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
        Call<ProductResponse> call =requestInterface.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse =response.body();
                products =new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
                adapter =new DataAdapter(products,getActivity(), new DataAdapter.onClickWish() {
                    @Override
                    public void onClickWishlist(int pos) {

                        String proname=products.get(pos).getP_name();
                        list.add(proname);
                        Toast.makeText(getActivity(),proname+" added  to cart",Toast.LENGTH_SHORT).show();
                        set.addAll(list);
                        editor.putStringSet("wishlist",set);
                        editor.commit();

                    }

                    @Override
                    public void onClickremovewish(int pos) {
                        String proname=products.get(pos).getP_name();
                        list.remove(proname);
                        Toast.makeText(getActivity(), proname+" removed  from cart",Toast.LENGTH_SHORT).show();
                        set.addAll(list);
                        editor.putStringSet("wishlist",set);
                        editor.commit();

                    }
                });
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        for(String temp:list){
            Log.d("Products",temp);
        }
       // SharedPreferences pref=getActivity().getSharedPreferences("ABC", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor =pref.edit();
        // Set<String> set=new HashSet<>();
        //set.addAll(list);
        //editor.putStringSet("wishlist",set);
        //editor.commit();

    }
}
