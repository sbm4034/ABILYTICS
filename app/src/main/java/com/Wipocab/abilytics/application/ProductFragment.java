package com.Wipocab.abilytics.application;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
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

import com.Wipocab.abilytics.application.Adapters.DataAdapter;
import com.Wipocab.abilytics.application.Model.ProductResponse;
import com.Wipocab.abilytics.application.Model.ProductVersion;
import com.Wipocab.abilytics.application.Model.ServerRequest;
import com.Wipocab.abilytics.application.Model.User;
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
    static RecyclerView recyclerView;
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
                return false;
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                    doSearch(s);
                    return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

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
    RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
    final User user = new User();
    user.setQuery(s);
    ServerRequest request = new ServerRequest();
    request.setOperation(Constants.search);
    request.setUser(user);
    //Call<ProductResponse> response = requestInterface.operation(request);
    Call<ProductResponse> call =requestInterface.operation(request);
    call.enqueue(new Callback<ProductResponse>() {
        @Override
        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
            ProductResponse productResponse =response.body();
            products =new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
            if (products.isEmpty()){
                Toast.makeText(getActivity(), "no products found", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            adapter =new DataAdapter(products, getActivity(), new DataAdapter.onClickWish() {
                @Override
                public void onClickprolistener(int pos, String pid) {
                    if(pid.equals("sub_1")||pid.equals("sub_2")||pid.equals("sub_3")||pid.equals("sub_4")||pid.equals("sub_5")||pid.equals("sub_6")||pid.equals("sub_7"))
                    {
                        ShowlengthDialog(pos, pid);
                    }
                   else {
                        startChildFragment(pos,pid,"io");
                    }
                    //Toast.makeText(getActivity(),pid,Toast.LENGTH_SHORT).show();

                }


            });
                    recyclerView.setAdapter(adapter);
            //Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();

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
                    @Override
                    public void onClickprolistener(int pos, String pid) {
                        if(pos>7)
                        {
                         startChildFragment(pos,pid,"io");
                        }
                       else{ ShowlengthDialog(pos,pid);
                        }

                    }
                });

                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Snackbar.make(getView(), t.toString(), Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }


    private void ShowlengthDialog( final int pos,final String  pid) {
        String lengths[]={"45","90","180","450"};
        MaterialDialog materialDialog=new MaterialDialog.Builder(getActivity())
                .titleColor(getResources().getColor(R.color.white))
                .itemsColor(getResources().getColor(R.color.white))
                .widgetColor(getResources().getColor(R.color.primary_dark))
                .backgroundColor(getResources().getColor(R.color.color8))
                .items(lengths)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                        startChildFragment(pos,pid, String.valueOf(text));

                        return true;
                    }
                })


                .title("Pick Length of Product")
                .build();
        materialDialog.show();
    }
//child fragment
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void startChildFragment(int pos, String pid,String length) {
        recyclerView.setVisibility(View.INVISIBLE);
        Fragment fr=SubProductFragment.newInstance(pid,length);
        android.app.FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        ft.replace(R.id.framepro,fr,"Child");
        ft.addToBackStack(null);
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
