package com.Wipocab.abilytics.application.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.Wipocab.abilytics.application.Constants;
import com.Wipocab.abilytics.application.Model.ProductResponse;
import com.Wipocab.abilytics.application.Model.ProductVersion;
import com.Wipocab.abilytics.application.Model.ServerRequest;
import com.Wipocab.abilytics.application.Model.User;
import com.Wipocab.abilytics.application.R;
import com.Wipocab.abilytics.application.RequestInterfaceProducts;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<ProductVersion> products;
    Context context;
    onClickremove listener; ArrayList<String> ids,nois,price;
    ArrayList<Integer> noitext=new ArrayList<>();



    public CartAdapter(ArrayList<ProductVersion> products, Context context, onClickremove listener) {

        this.products = products;
        this.context = context;
        this.listener=listener;
    }
public interface onClickremove {
    public void onClickremovelist(int pos);

    public void ordercart(ArrayList<String> ids, ArrayList<String> nois,ArrayList<String> price);

    public void textChanged(int pos,String noip);


}

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_layout_3, parent, false);

        return new CartAdapter.ViewHolder(view);
    }
@Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {

    holder.noi.setText(String.valueOf(products.get(position).getNoi()));
    holder.abc.setText(String.format("%s x",products.get(position).getPkg()));
    holder.p_code.setText(String.format("Product Code: %s",products.get(position).getProduct_code()));

    if(products.get(position).getNominalAreaCond()==null){
        holder.p_nodia.setVisibility(View.GONE);

    } else {holder.p_nodia.setText(String.format("Nominal Area : %s", products.get(position).getNominalAreaCond()));}
    if(products.get(position).getNumberAreaofCond()==null){
        holder.p_noArea.setVisibility(View.GONE);

    }else{
        holder.p_noArea.setText(String.format("Area of Cond.\t: %s Sq mm",products.get(position).getNumberperDiaofWire()));}
/*    if(products.get(position).getPriceperCoil()==null){
        holder.pricepercoil.setVisibility(View.GONE);

    }else {
        holder.pricepercoil.setText(String.format("Price: %s",products.get(position).getCart_price()));}*/
    if(products.get(position).getPkg()==null){
        holder.p_pkg.setVisibility(View.GONE);

    }else{
        holder.p_pkg.setText(String.format("Pkg: %s",products.get(position).getPkg()));}
    holder.cart_price.setText(String.format("Price %s",products.get(position).getCart_price()));
    if(products.get(position).getLength()==null){
        holder.p_noLength.setVisibility(View.GONE);

    }else{
        holder.p_noLength.setText(String.format("Length: %s",products.get(position).getLength()));}
    if(products.get(position).getNominalAreaCond()==null){
        holder.p_NominalAreaCond.setVisibility(View.GONE);

    }else{
        holder.p_NominalAreaCond.setText(String.format("Nominal Area Cond. :%s",products.get(position).getNominalAreaCond()));}


    /*
    if(products.get(position).getOne_Core()==null){
        holder.p_one_core.setVisibility(View.GONE);

    }
    else{
        holder.p_one_core.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getTwo_Core()==null){
        holder.p_two_core.setVisibility(View.GONE);

    }else{
        holder.p_two_core.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getThree_Core()==null){
        holder.p_three_core.setVisibility(View.GONE);

    }
    else{
        holder.p_three_core.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getNominalDiaofCond_04()==null){
        holder.p_NominalDiaofCond_04.setVisibility(View.GONE);

    }else{
        holder.p_NominalDiaofCond_04.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getNominalDiaofCond_05()==null){
        holder.p_NominalDiaofCond_05.setVisibility(View.GONE);

    }else{
        holder.p_NominalDiaofCond_05.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getPricepermetre()==null){
        holder.p_Pricepermetre.setVisibility(View.GONE);

    }else{
        holder.p_Pricepermetre.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getPriceper100mtrs()==null){
        holder.p_Priceper100metre.setVisibility(View.GONE);

    }else{
        holder.p_Priceper100metre.setText(String.format("Price  : %s",products.get(position).getCart_price()));}
    if(products.get(position).getPriceper300mtrs()==null){
        holder.p_Priceper300metre.setVisibility(View.GONE);

    }else{
        holder.p_Priceper300metre.setText(String.format("Price : %s",products.get(position).getCart_price()));}
    if(products.get(position).getPriceper305mtrs()==null){
        holder.p_Priceper305metre.setVisibility(View.GONE);

    }else{
        holder.p_Priceper305metre.setText(String.format("Price : %s",products.get(position).getCart_price()));}*/
    if(products.get(position).getNoofPair()==null){
        holder.p_NoofPair.setVisibility(View.GONE);

    }else{
        holder.p_NoofPair.setText(String.format("No of pair: %s",products.get(position).getNoofPair()));}

    if(products.get(position).getDescription()==null){
        holder.p_Description.setVisibility(View.GONE);

    }else
        holder.p_Description.setText(String.format("Description : %s",products.get(position).getDescription()));
        //holder.product_code.setText(products.get(position).getProduct_code());
     //   Picasso.with(context).load(products.get(position).getP_image()).into(holder.p_image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView p_code, p_nodia,pricepercoil,p_one_core,p_NominalDiaofCond_04,noperdia,p_noArea,p_NominalDiaofCond_05,p_NoofPair,p_Pricepermetre,p_Description,p_Priceper100metre,p_Priceper300metre,p_Priceper305metre,p_NominalAreaCond,p_two_core,p_three_core,p_noLength,p_pkg,p_price,abc;
        Button p_remove,p_inc,p_dec;
         TextView noi;  ArrayAdapter<Integer> adapter;
        TextView cart_price;
        SharedPreferences pref;

        public ViewHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);

            abc = (TextView) itemView.findViewById(R.id.abc);
            p_code = (TextView) itemView.findViewById(R.id.p_code);
            p_nodia = (TextView) itemView.findViewById(R.id.p_noperdia);
            p_noArea = (TextView) itemView.findViewById(R.id.p_noArea);
            p_pkg = (TextView) itemView.findViewById(R.id.p_pkg);
            p_noLength = (TextView) itemView.findViewById(R.id.p_noLength);
            cart_price = (TextView) itemView.findViewById(R.id.cart_price);
            //pricepercoil=(TextView)itemView.findViewById(R.id.p_price);
            //p_one_core=(TextView)itemView.findViewById(R.id.p_one_core);
            //p_two_core=(TextView)itemView.findViewById(R.id.p_two_core);
            //p_three_core=(TextView)itemView.findViewById(R.id.p_three_core);
            p_NominalAreaCond = (TextView) itemView.findViewById(R.id.p_NominalAreaCond);
            //p_NominalDiaofCond_04=(TextView)itemView.findViewById(R.id.p_NominalDiaofCond_04);
            //p_NominalDiaofCond_05=(TextView)itemView.findViewById(R.id.p_NominalDiaofCond_05);
            p_Description = (TextView) itemView.findViewById(R.id.p_Description);
            p_NoofPair = (TextView) itemView.findViewById(R.id.p_NoofPair);
            //p_Pricepermetre=(TextView)itemView.findViewById(R.id.p_Pricepermetre);
            //p_Priceper100metre=(TextView)itemView.findViewById(R.id.p_Priceper100metre);
            //p_Priceper300metre=(TextView)itemView.findViewById(R.id.p_Priceper300metre);
            //p_Priceper305metre=(TextView)itemView.findViewById(R.id.p_Priceper305metre);
            noi = (TextView) itemView.findViewById(R.id.noiedit);
            p_remove = (Button) itemView.findViewById(R.id.addtoCart);
            p_inc = (Button) itemView.findViewById(R.id.inc);
            p_dec = (Button) itemView.findViewById(R.id.dec);
            itemView.setOnClickListener(this);
            p_remove.setOnClickListener(this);
            p_inc.setOnClickListener(this);
            p_dec.setOnClickListener(this);
            ids = new ArrayList<String>();
            nois = new ArrayList<String>();
            price = new ArrayList<String>();
            for (int i = 0; i < products.size(); i++) {
                nois.add(String.valueOf(products.get(i).getNoi()));
            }
            pref = itemView.getContext().getSharedPreferences("ABC", Context.MODE_PRIVATE);

            String savedstring=pref.getString(Constants.cartnoi,"");
            StringTokenizer st = new StringTokenizer(savedstring, ",");
            for (int i = 0; i < products.size(); i++) {
                products.get(i).setNoi(Integer.parseInt(st.nextToken()));
            }
            listener.ordercart(ids ,nois,price);


        }

        @Override
        public void onClick(View view) {
            final int pos = getAdapterPosition();
            pref=view.getContext().getApplicationContext().getSharedPreferences("ABC",Context.MODE_PRIVATE);
            if(view.getId()==p_remove.getId()){
                    loadremovejson(pos,view);
            }
            if(view.getId()==p_inc.getId()){
                inc(pos,view);
            }
            if(view.getId()==p_dec.getId()){
                dec(pos,view);

            }


        }
        private void loadremovejson(int pos, final View view) {
            listener.onClickremovelist(pos);
            final Context context=view.getContext();
          MaterialDialog.Builder  materialDialog = new MaterialDialog.Builder(view.getContext())
                    .content(R.string.loading)
                    .widgetColor(Color.RED)
                    .progress(true, 0);
          final MaterialDialog  mdialg=materialDialog.build();
            mdialg.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            User user =new User();
            user.setEmail(pref.getString(Constants.EMAIL,""));
            user.setProduct_code((products.get(pos).getProduct_code()));
            final ServerRequest request=new ServerRequest();
            request.setOperation(Constants.removecart);
            request.setUser(user);
            RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
            Call<ProductResponse> call =requestInterface.operation(request);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                   mdialg.dismiss();
                    ProductResponse productResponse =response.body();
                    products=new ArrayList<ProductVersion>(Arrays.asList(productResponse.getProducts()));
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext().getApplicationContext(),"Successfully removed",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                  mdialg.dismiss();
                    // Snackbar.make(getCurrentFocus(),"Connection problem",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(context,"Not able to remove ", Toast.LENGTH_SHORT).show();


                }
            });

        }


    }

    private void inc(int pos,View view) {
       int a= noitext.get(pos);
        a=a+1;
        noitext.set(pos,a);
        notifyDataSetChanged();
        products.get(pos).setNoi(noitext.get(pos));
        nois.add(pos,String.valueOf(products.get(pos).getNoi()));
        listener.textChanged(pos,String.valueOf(noitext.get(pos)));

    }
    private void dec(int pos,View view) {
        int a= noitext.get(pos);
        a=a-1;
        noitext.set(pos,a);
        notifyDataSetChanged();
        products.get(pos).setNoi(noitext.get(pos));
        nois.add(pos,String.valueOf(products.get(pos).getNoi()));

        listener.textChanged(pos,String.valueOf(noitext.get(pos)));
    }
}
