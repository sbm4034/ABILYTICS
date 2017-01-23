package com.Wipocab.abilytics.app;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Adapters.DataAdapter;
import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private ArrayList<ProductVersion> products;
    private DataAdapter adapter;
    MaterialDialog.Builder materialDialog; MaterialDialog dialog;
    ArrayList<String> list;
    SwipeRefreshLayout swipeRefreshLayout;
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
        setHasOptionsMenu(true);

        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profilemenu,menu);

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside edittext component


        // implementing the listener
        sv.setOnCloseListener(new SearchView.OnCloseListener()
        {
            @Override
            public boolean onClose(){
                initView(getView());
                return true;
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 4) {
                    Toast.makeText(getActivity(),
                            "Your search query must not be less than 3 characters",
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {

                    doSearch(s);
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                doSearch(newText);
                return false;
            }
        }
        );

        item.setActionView(sv);
    }


public void doSearch(String s){
    dialog.show();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RequestInterface requestInterface=retrofit.create(RequestInterface.class);
    ServerRequest request = new ServerRequest();
    request.setquery(s);
    //Call<ProductResponse> response = requestInterface.operation(request);
    Call<ProductResponse> call =requestInterface.query(request);
    call.enqueue(new Callback<ProductResponse>() {
        @Override
        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
            ProductResponse productResponse =response.body();
            if(productResponse.getResult().equals(Constants.SUCCESS)){
            products =new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
            dialog.dismiss();
            adapter =new DataAdapter(products, getActivity(), new DataAdapter.onClickWish() {
                @Override
                public void onClickprolistener(int pos) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        startChildFragment(pos);
                    }

                }
            });
                    recyclerView.setAdapter(adapter);
            Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();

        }
        }

        @Override
        public void onFailure(Call<ProductResponse> call, Throwable t) {
            Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();

        }
    });
}





    private void initView(View view) {
        TextView abs =(TextView)getActivity().findViewById(R.id.abs);
        abs.setText("Our Products");
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this) ;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutmanager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutmanager);
        materialDialog=new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        dialog=materialDialog.build();
        loadJson();
        //dialog.show();
        swipeRefreshLayout.setColorSchemeResources(R.color.color5,R.color.color1,R.color.color2,
                R.color.color3,R.color.color4,R.color.color6);
    }





    private void loadJson() {
        swipeRefreshLayout.setRefreshing(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
        Call<ProductResponse> call =requestInterface.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse =response.body();
                products =new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
               // dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);

                adapter =new DataAdapter(products, getActivity(), new DataAdapter.onClickWish() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClickprolistener(int pos) {
                        startChildFragment(pos);

                    }
                });

                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Snackbar.make(getView(), "Connection Problem", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void startChildFragment(int pos) {
        recyclerView.setVisibility(View.INVISIBLE);
        Fragment fr=SubProductFragment.newInstance(pos);
        android.app.FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        ft.add(R.id.framepro,fr);
        ft.addToBackStack("Child");
        ft.commit();


    }

    @Override
    public void onPause() {
        super.onPause();
        for(String temp:list){
            Log.d("Products",temp);
        }


    }

    @Override
    public void onRefresh() {
        loadJson();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refreshit:
                loadJson();
                return true;
        }
        return true;

    }

}
