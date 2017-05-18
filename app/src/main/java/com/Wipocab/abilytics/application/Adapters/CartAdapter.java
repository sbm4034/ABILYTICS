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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.Wipocab.abilytics.application.Constants;
import com.Wipocab.abilytics.application.Model.NoiVersion;
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
    private ArrayList<NoiVersion> noiVersions =new ArrayList<>();
    Context context;
    onClickremove listener; ArrayList<String> ids,nois =new ArrayList<>(),price;





    public CartAdapter(ArrayList<ProductVersion> products,ArrayList<NoiVersion> noiVersions, Context context, onClickremove listener) {
        this.products = products;
        this.noiVersions=noiVersions;
        this.context = context;
        this.listener=listener;
    }
public interface onClickremove {
    public void onClickremovelist(int pos);

    public void ordercart(ArrayList<String> ids, ArrayList<String> nois,ArrayList<String> price);

    public void textChanged(int pos,ArrayList<NoiVersion> noip);


}

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_layout_3, parent, false);

        return new CartAdapter.ViewHolder(view);
    }
@Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {

    holder.noi.setText(String.valueOf(noiVersions.get(position).getNoi()));
    holder.abc.setText(String.format("%s x",products.get(position).getPkg()));
    holder.p_code.setText(String.format("Product Code: %s",products.get(position).getProduct_code()));
    if(noiVersions.get(position).getNoi_for_04().equals("0")){


        holder.lay_04.setVisibility(View.GONE);
        holder.qty_04.setVisibility(View.GONE);
    }
    else
    {


        holder.lay_04.setVisibility(View.VISIBLE);
        holder.qty_04.setVisibility(View.VISIBLE);
        holder.qty_04.setText(String.format("%s",noiVersions.get(position).getNoi_for_04()));
    }
    if(noiVersions.get(position).getNoi_for_05().equals("0")){
        holder.lay_05.setVisibility(View.GONE);
        holder.qty_05.setVisibility(View.GONE);
    }
    else
    {
        holder.lay_05.setVisibility(View.VISIBLE);
        holder.qty_05.setVisibility(View.VISIBLE);
        holder.qty_05.setText(String.format("%s",noiVersions.get(position).getNoi_for_05()));
    }
    if(noiVersions.get(position).getNoi_for_one_core().equals("0")){
        holder.lay_one.setVisibility(View.GONE);
        holder.qty_oneCore.setVisibility(View.GONE);
    }
    else
    {
        holder.lay_one.setVisibility(View.VISIBLE);
        holder.qty_oneCore.setVisibility(View.VISIBLE);
        holder.qty_oneCore.setText(String.format("%s",noiVersions.get(position).getNoi_for_one_core()));
    }
    if(noiVersions.get(position).getNoi_for_two_core().equals("0")){
        holder.lay_two.setVisibility(View.GONE);
        holder.qty_twoCore.setVisibility(View.GONE);
    }
    else
    {
        holder.lay_two.setVisibility(View.VISIBLE);
        holder.qty_twoCore.setVisibility(View.VISIBLE);
        holder.qty_twoCore.setText(String.format("%s",noiVersions.get(position).getNoi_for_two_core()));
    }
    if(noiVersions.get(position).getNoi_for_three_core().equals("0")){
        holder.lay_three.setVisibility(View.GONE);
        holder.qty_threeCore.setVisibility(View.GONE);
    }
    else
    {
        holder.lay_three.setVisibility(View.VISIBLE);
        holder.qty_threeCore.setVisibility(View.VISIBLE);
        holder.qty_threeCore.setText(String.format("%s",noiVersions.get(position).getNoi_for_three_core()));
    }
    if(products.get(position).getNumberAreaofCond1()==null)
    {
        holder.p_nodia1.setVisibility(View.GONE);

    }else{
        holder.p_nodia1.setText(String.format("Name : %s Sq mm", products.get(position).getNumberAreaofCond1()));
    }

    if(products.get(position).getNumberAreaofCond()==null){
        holder.p_nodia.setVisibility(View.GONE);

    } else {holder.p_nodia.setText(String.format("Name : %s Sq mm", products.get(position).getNumberAreaofCond()));}

/*    if(products.get(position).getPriceperCoil()==null){
        holder.pricepercoil.setVisibility(View.GONE);

    }else {
        holder.pricepercoil.setText(String.format("Price: %s",products.get(position).getCart_price()));}*/

    if(products.get(position).getNumberperDiaofWireinch()==null){
        holder.p_NumberperDiaofWireinch.setVisibility(View.GONE);

    }else{
        holder.p_NumberperDiaofWireinch.setText(String.format("Number/Dia of Wire : %S inch",products.get(position).getNumberperDiaofWireinch()));
    }

    if(products.get(position).getNumberperDiaofWire()==null){
        holder.p_NumberperDiaofWire.setVisibility(View.GONE);

    }else{
        holder.p_NumberperDiaofWire.setText(String.format("Number/Dia of Wire : %S  mm",products.get(position).getNumberperDiaofWire()));
    }


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
        holder.p_NominalAreaCond.setText(String.format("Nominal Area of Cond :%s Sq mm",products.get(position).getNominalAreaCond()));}


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
        private TextView p_code,p_NumberperDiaofWire,p_NumberperDiaofWireinch,p_nodia1, p_nodia,pricepercoil,p_one_core,p_NominalDiaofCond_04,noperdia,p_noArea,p_NominalDiaofCond_05,p_NoofPair,p_Pricepermetre,p_Description,p_Priceper100metre,p_Priceper300metre,p_Priceper305metre,p_NominalAreaCond,p_two_core,p_three_core,p_noLength,p_pkg,p_price,abc,tv_oneCore,qty_oneCore,tv_twoCore,qty_twoCore,tv_threeCore,qty_threeCore,qty_04,qty_05;
        Button p_remove,p_inc,p_dec,inc_04,dec_04,inc_05,dec_05,inc_one,dec_one,inc_two,dec_two,inc_three,dec_three,inc_100,dec_100,inc_300,dec_300;
         TextView noi;  ArrayAdapter<Integer> adapter;
        LinearLayout lay_04,lay_05,lay_100,lay_300,lay_one,lay_two,lay_three;
        TextView cart_price;
        SharedPreferences pref;

        public ViewHolder(View itemView) {
            super(itemView);
            ids = new ArrayList<String>();
            nois = new ArrayList<String>();
            price = new ArrayList<String>();
            //itemView.setOnClickListener(this);
            lay_04=(LinearLayout)itemView.findViewById(R.id.lay_04);
            lay_05=(LinearLayout)itemView.findViewById(R.id.lay_05);
            lay_100=(LinearLayout)itemView.findViewById(R.id.lay_100);
            lay_300=(LinearLayout)itemView.findViewById(R.id.lay_300);
            lay_one=(LinearLayout)itemView.findViewById(R.id.lay_one);
            lay_three=(LinearLayout)itemView.findViewById(R.id.lay_three);
            lay_two=(LinearLayout)itemView.findViewById(R.id.lay_two);
            inc_04=(Button)itemView.findViewById(R.id.inc_04);
            inc_05=(Button)itemView.findViewById(R.id.inc_05);
            inc_one=(Button)itemView.findViewById(R.id.inc_one);
            inc_two=(Button)itemView.findViewById(R.id.inc_two);
            inc_three=(Button)itemView.findViewById(R.id.inc_three);
            inc_100=(Button)itemView.findViewById(R.id.inc_100);
            inc_300=(Button)itemView.findViewById(R.id.inc_300);
            dec_04=(Button)itemView.findViewById(R.id.dec_04);
            dec_05=(Button)itemView.findViewById(R.id.dec_05);
            dec_one=(Button)itemView.findViewById(R.id.dec_one);
            dec_two=(Button)itemView.findViewById(R.id.dec_two);
            dec_three=(Button)itemView.findViewById(R.id.dec_three);
            dec_100=(Button)itemView.findViewById(R.id.dec_100);
            dec_300=(Button)itemView.findViewById(R.id.dec_300);
            abc = (TextView) itemView.findViewById(R.id.abc);
            qty_oneCore=(TextView)itemView.findViewById(R.id.val_one);
            qty_twoCore=(TextView)itemView.findViewById(R.id.val_two);
            qty_threeCore=(TextView)itemView.findViewById(R.id.val_three);
            qty_04=(TextView)itemView.findViewById(R.id.val_04);
            qty_05=(TextView)itemView.findViewById(R.id.val_05);
            p_code = (TextView) itemView.findViewById(R.id.p_code);
            p_nodia = (TextView) itemView.findViewById(R.id.p_noperdia);
            //p_noArea = (TextView) itemView.findViewById(R.id.p_noArea);
            p_pkg = (TextView) itemView.findViewById(R.id.p_pkg);
            p_noLength = (TextView) itemView.findViewById(R.id.p_noLength);
            cart_price = (TextView) itemView.findViewById(R.id.cart_price);
            p_NominalAreaCond = (TextView) itemView.findViewById(R.id.p_NominalAreaCond);
            p_Description = (TextView) itemView.findViewById(R.id.p_Description);
            p_NoofPair = (TextView) itemView.findViewById(R.id.p_NoofPair);
            p_nodia1=(TextView)itemView.findViewById(R.id.p_noperdia1);
            p_NumberperDiaofWireinch=(TextView)itemView.findViewById(R.id.p_NumberperDiaofWireinch);
            p_NumberperDiaofWire=(TextView)itemView.findViewById(R.id.p_NumberperDiaofWire);
            noi = (TextView) itemView.findViewById(R.id.noiedit);
            p_remove = (Button) itemView.findViewById(R.id.addtoCart);
            p_inc = (Button) itemView.findViewById(R.id.inc);
            p_dec = (Button) itemView.findViewById(R.id.dec);
            for (int i = 0; i < noiVersions.size(); i++) {
                nois.add(String.valueOf(noiVersions.get(i).getNoi()));
            }
            pref = itemView.getContext().getSharedPreferences("ABC", Context.MODE_PRIVATE);
            String savedstring=pref.getString(Constants.cartnoi,"");
            /*StringTokenizer st = new StringTokenizer(savedstring, ",");
            for (int i = 0; i < products.size(); i++) {
                products.get(i).setNoi(Integer.parseInt(st.nextToken()));
            }*/
          //  listener.ordercart(ids ,nois,price);

            p_remove.setOnClickListener(this);
            p_inc.setOnClickListener(this);
            p_dec.setOnClickListener(this);
            inc_04.setOnClickListener(this);
            dec_04.setOnClickListener(this);
            inc_05.setOnClickListener(this);
            dec_05.setOnClickListener(this);
            inc_one.setOnClickListener(this);
            dec_one.setOnClickListener(this);
            inc_two.setOnClickListener(this);
            dec_two.setOnClickListener(this);
            inc_three.setOnClickListener(this);
            dec_three.setOnClickListener(this);





        }

        void loadremovejson(final int pos, final View view) {
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
                    noiVersions.remove(pos);
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


        @Override
        public void onClick(View view) {
            final int pos = getAdapterPosition();
            listener.ordercart(ids ,nois,price);
            switch (view.getId()){
                case R.id.btn_remove_cart:
                    Toast.makeText(view.getContext(),"removed",Toast.LENGTH_SHORT).show();
                    loadremovejson(pos,view);
                    break;
                case R.id.inc_05:
                    inc_05(pos,view);
                    break;
                case R.id.inc_04:
                    inc_04(pos,view);
                    break;
                case R.id.dec_04:
                    dec_04(pos,view);
                    break;
                case R.id.dec_05:
                    dec_05(pos,view);
                    break;
                case R.id.inc_one:
                    inc_one(pos,view);
                    break;
                case R.id.inc_two:
                    inc_two(pos,view);
                    break;

                case R.id.dec_one:
                    dec_one(pos,view);
                    break;
                case R.id.dec_two:
                    dec_two(pos,view);
                    break;
                case R.id.inc_three:
                    inc_three(pos,view);
                    break;
                case R.id.dec_three:
                    dec_three(pos,view);
                    break;
                case R.id.inc:
                    inc1(pos,view);
                    break;
                case R.id.dec:
                    dec1(pos,view);
                    break;


            }

        }
        private void inc_04(int pos,View view) {
            Toast.makeText(view.getContext(),"0",Toast.LENGTH_SHORT).show();
            if (!noiVersions.get(pos).getNoi_for_04().equals("0")) {
                Toast.makeText(view.getContext(),"1",Toast.LENGTH_SHORT).show();
                int b = Integer.parseInt(noiVersions.get(pos).getNoi_for_04());
                b = b + 1;
                noiVersions.get(pos).setNoi_for_04(String.valueOf(b));
                int a = Integer.parseInt(noiVersions.get(pos).getNoi());
                a = a + 1;
                noiVersions.get(pos).setNoi(String.valueOf(a));

                listener.textChanged(pos, noiVersions);
                notifyDataSetChanged();
            }
        }
        private void inc_05(int pos,View view) {
            if (!noiVersions.get(pos).getNoi_for_05().equals("0")) {
                int b = Integer.parseInt(noiVersions.get(pos).getNoi_for_05());
                b = b + 1;
                noiVersions.get(pos).setNoi_for_05(String.valueOf(b));
                int a = Integer.parseInt(noiVersions.get(pos).getNoi());
                a = a + 1;
                noiVersions.get(pos).setNoi(String.valueOf(a));

                listener.textChanged(pos, noiVersions);
                notifyDataSetChanged();
            }
        }
        private void inc_one(int pos,View view) {
            if(!noiVersions.get(pos).getNoi_for_one_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_one_core());
                b=b+1;
                noiVersions.get(pos).setNoi_for_one_core(String.valueOf(b));
                int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                a=a+1;
                noiVersions.get(pos).setNoi(String.valueOf(a));

                listener.textChanged(pos,noiVersions);
                notifyDataSetChanged();
            }
        }
        private void inc_two(int pos,View view) {
            if(!noiVersions.get(pos).getNoi_for_two_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_two_core());
                b=b+1;
                noiVersions.get(pos).setNoi_for_two_core(String.valueOf(b));
                int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                a=a+1;
                noiVersions.get(pos).setNoi(String.valueOf(a));

                listener.textChanged(pos,noiVersions);
                notifyDataSetChanged();}

        }


        private void inc_three(int pos,View view) {
            if(noiVersions.get(pos).getNoi_for_three_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_three_core());
                b=b+1;
                noiVersions.get(pos).setNoi_for_three_core(String.valueOf(b));
                int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                a=a+1;
                noiVersions.get(pos).setNoi(String.valueOf(a));

                listener.textChanged(pos,noiVersions);
                notifyDataSetChanged();
            }
        }




        private void inc1(int pos,View view){
            int total=0;
            int q=Integer.parseInt(noiVersions.get(pos).getNoi_for_one_core());
            int w=Integer.parseInt(noiVersions.get(pos).getNoi_for_two_core());
            int e=Integer.parseInt(noiVersions.get(pos).getNoi_for_three_core());
            int r=Integer.parseInt(noiVersions.get(pos).getNoi_for_04());
            int t=Integer.parseInt(noiVersions.get(pos).getNoi_for_05());
            total=q+w+e+r+t;
            int a= Integer.parseInt(noiVersions.get(pos).getNoi());
            if(total<a){
                a=a+1;
                noiVersions.get(pos).setNoi(String.valueOf(a));
                nois.add(pos,String.valueOf(noiVersions.get(pos).getNoi()));
                notifyDataSetChanged();
                listener.textChanged(pos,noiVersions);

            }
            else
            {
                Toast.makeText(view.getContext(), "Invalid operation", Toast.LENGTH_SHORT).show();


            }

        }
        private void dec_04(int pos,View view) {

            if (!noiVersions.get(pos).getNoi_for_04().equals("0")) {

                int b = Integer.parseInt(noiVersions.get(pos).getNoi_for_04());
                if (b < 1) {
                    Toast.makeText(view.getContext(), "Invalid Quantity", Toast.LENGTH_SHORT).show();
                } else {
                    b = b - 1;
                    noiVersions.get(pos).setNoi_for_04(String.valueOf(b));
                    int a = Integer.parseInt(noiVersions.get(pos).getNoi());
                    a = a - 1;
                    noiVersions.get(pos).setNoi(String.valueOf(a));
                    notifyDataSetChanged();
                    listener.textChanged(pos, noiVersions);
                }
            }
        }
        private void dec_05(int pos,View view){
            if(!noiVersions.get(pos).getNoi_for_05().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_05());
                if(b<1){
                    Toast.makeText(view.getContext(),"Invalid Quantity",Toast.LENGTH_SHORT).show();
                }else{
                    b=b-1;
                    noiVersions.get(pos).setNoi_for_05(String.valueOf(b));
                    int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                    a=a-1;
                    noiVersions.get(pos).setNoi(String.valueOf(a));
                    notifyDataSetChanged();
                    listener.textChanged(pos,noiVersions);}
            }
        }
        private void dec_one(int pos,View view){
            if(!noiVersions.get(pos).getNoi_for_one_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_one_core());
                if(b<1){
                    Toast.makeText(view.getContext(),"Invalid Quantity",Toast.LENGTH_SHORT).show();
                }else{
                    b=b-1;
                    noiVersions.get(pos).setNoi_for_one_core(String.valueOf(b));
                    int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                    a=a-1;
                    noiVersions.get(pos).setNoi(String.valueOf(a));
                    notifyDataSetChanged();
                    listener.textChanged(pos,noiVersions);}
            }
        }
        private void dec_two(int pos,View view){
            if(!noiVersions.get(pos).getNoi_for_two_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_two_core());
                if(b<1){
                    Toast.makeText(view.getContext(),"Invalid Quantity",Toast.LENGTH_SHORT).show();
                }else{
                    b=b-1;
                    noiVersions.get(pos).setNoi_for_two_core(String.valueOf(b));
                    int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                    a=a-1;
                    noiVersions.get(pos).setNoi(String.valueOf(a));
                    notifyDataSetChanged();
                    listener.textChanged(pos,noiVersions);}
            }
        }
        private void dec_three(int pos,View view){
            if(!noiVersions.get(pos).getNoi_for_three_core().equals("0")){
                int b=Integer.parseInt(noiVersions.get(pos).getNoi_for_three_core());
                if(b<1){
                    Toast.makeText(view.getContext(),"Invalid Quantity",Toast.LENGTH_SHORT).show();
                }else{
                    b=b-1;
                    noiVersions.get(pos).setNoi_for_three_core(String.valueOf(b));
                    int a= Integer.parseInt(noiVersions.get(pos).getNoi());
                    a=a-1;
                    noiVersions.get(pos).setNoi(String.valueOf(a));
                    notifyDataSetChanged();
                    listener.textChanged(pos,noiVersions);}
            }
        }

        private void dec1(int pos,View view) {
            int total=0;
            int q=Integer.parseInt(noiVersions.get(pos).getNoi_for_one_core());
            int w=Integer.parseInt(noiVersions.get(pos).getNoi_for_two_core());
            int e=Integer.parseInt(noiVersions.get(pos).getNoi_for_three_core());
            int r=Integer.parseInt(noiVersions.get(pos).getNoi_for_04());
            int t=Integer.parseInt(noiVersions.get(pos).getNoi_for_05());
            total=q+w+e+r+t;
            int a= Integer.parseInt(noiVersions.get(pos).getNoi());
            if(total>a)
            {
                if(a<1){
                    Toast.makeText(view.getContext(),"Invalid operation",Toast.LENGTH_SHORT).show();
                    loadremovejson(pos,view);
                    notifyDataSetChanged();
                }
                a=a-1;
                noiVersions.get(pos).setNoi(String.valueOf(a));
                nois.add(pos,String.valueOf(noiVersions.get(pos).getNoi()));
                notifyDataSetChanged();
                listener.textChanged(pos,noiVersions);
            }
            else
            {
                Toast.makeText(view.getContext(),"Invalid operation",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        }
    }


}
