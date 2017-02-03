package com.Wipocab.abilytics.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Wipocab.abilytics.app.Adapters.DataAdapterForSubProducts;
import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ProductVersion;
import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.User;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gautam on 23/1/17.
 */

public class SubProductFragment extends Fragment {
    int main_pro;
    ProgressBar progressBar;
    ArrayList<ProductVersion> products;
    DataAdapterForSubProducts dataAdapter;
    RecyclerView recyclerview;

    public static SubProductFragment newInstance(int pid) {
        SubProductFragment frag = new SubProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product", String.valueOf(pid));
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subprofragment, container, false);
        String pid = getArguments().getString("product");
        Toast.makeText(getActivity(), String.format("position %s clicked", pid), Toast.LENGTH_SHORT).show();
        initView(view);
        main_pro=Integer.parseInt(pid);
        loadJson();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(),"yooooooooo",Toast.LENGTH_LONG).show();
        ProductFragment.recyclerView.setVisibility(View.VISIBLE);
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_Spinner);
        recyclerview= (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutmanager=new GridLayoutManager(getActivity(),2);
        recyclerview.setLayoutManager(layoutmanager);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_Spinner);
        progressBar.setVisibility(View.VISIBLE);
    }




    private void loadJson() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user = new User();
        user.setPid(main_pro);
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setOperation(Constants.getfromPid);
        serverRequest.setUser(user);
        RequestInterfaceProducts requestInterface = retrofit.create(RequestInterfaceProducts.class);
        Call<ProductResponse> call = requestInterface.operation(serverRequest);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                ProductResponse productResponse =response.body();
                products=new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
                dataAdapter =new DataAdapterForSubProducts(products, getActivity(), new DataAdapterForSubProducts.onClickWish() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClickprolistener(int pos, int pid, int subpid) {

                        Intent intent=new Intent(getActivity(),ActProductInfo.class);
                        intent.putExtra("DATAINTENT",Integer.toString(subpid));
                        startActivity(intent);
                        Log.d("LKKK",String.valueOf(subpid));


                    }

                });
                recyclerview.setAdapter(dataAdapter);

                //Toast.makeText(getActivity(),"Products successfully loaded",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Products failed",Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

}