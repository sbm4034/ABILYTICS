package com.Wipocab.abilytics.application.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataAdapterForSubProducts extends RecyclerView.Adapter<DataAdapterForSubProducts.ViewHolder> {
    private ArrayList<ProductVersion> products;
    Context context;
    onClickWish listeners;
    String price;
    ArrayList<Integer> noitext=new ArrayList<>();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_layout_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noi.setText(String.valueOf(products.get(position).getNoi()));

        holder.p_code.setText(String.format("Product Code: %s",products.get(position).getProduct_code()));
        holder.p_pkg.setText(String.format("Pkg: %s",products.get(position).getPkg()));

        if(products.get(position).getNumberAreaofCond()==null){
            holder.p_nodia.setVisibility(View.GONE);

        } else {holder.p_nodia.setText(String.format("Name : %s Sq mm", products.get(position).getNumberAreaofCond()));}
        if(products.get(position).getNumberperDiaofWire()==null){
            holder.p_noArea.setVisibility(View.GONE);

        }else{
        holder.p_noArea.setText(String.format("Number/Dia of Wire : %s mm",products.get(position).getNumberperDiaofWire()));
        }
        if(products.get(position).getNumberperDiaofWireinch()==null){
            holder.p_NumberperDiaofWireinch.setVisibility(View.GONE);

        }else{
            holder.p_NumberperDiaofWireinch.setText(String.format("Name : %S inch",products.get(position).getNumberperDiaofWireinch()));
        }

        if(products.get(position).getPriceperCoil()==null){
            holder.pricepercoil.setVisibility(View.GONE);

        }else {
        holder.pricepercoil.setText(String.format("Price/Coil: ₹  %s",products.get(position).getPriceperCoil()));
        }



        if(products.get(position).getOne_Core()==null){
            holder.p_one_core.setVisibility(View.GONE);

        }
        else{
            holder.p_one_core.setVisibility(View.VISIBLE);
        holder.p_one_core.setText(String.format("One Core :  ₹ %s",products.get(position).getOne_Core()));

        }
        if(products.get(position).getTwo_Core()==null){
            holder.p_two_core.setVisibility(View.GONE);

        }else{
        holder.p_two_core.setText(String.format("Two Core :  ₹ %s",products.get(position).getTwo_Core()));}
        if(products.get(position).getThree_Core()==null){
            holder.p_three_core.setVisibility(View.GONE);

        }
        else{
        holder.p_three_core.setText(String.format("Three Core :  ₹ %s",products.get(position).getThree_Core()));}
        if(products.get(position).getLength()==null){
            holder.p_noLength.setVisibility(View.GONE);

        }else{
        holder.p_noLength.setText(String.format("Length: %s",products.get(position).getLength()));}
        if(products.get(position).getNominalAreaCond()==null){
            holder.p_NominalAreaCond.setVisibility(View.GONE);

        }else{
        holder.p_NominalAreaCond.setText(String.format("Name :%s",products.get(position).getNominalAreaCond()));}
        if(products.get(position).getNominalDiaofCond_04()==null){
            holder.p_NominalDiaofCond_04.setVisibility(View.GONE);

        }else{
        holder.p_NominalDiaofCond_04.setText(String.format("Nominal Dia of Cond 0.4 :₹ %s  ",products.get(position).getNominalDiaofCond_04()));}
        if(products.get(position).getNominalDiaofCond_05()==null){
            holder.p_NominalDiaofCond_05.setVisibility(View.GONE);

        }else{
        holder.p_NominalDiaofCond_05.setText(String.format("Nominal Dia of Cond 0.5 : ₹ %s ",products.get(position).getNominalDiaofCond_05()));}
        if(products.get(position).getNoofPair()==null){
            holder.p_NoofPair.setVisibility(View.GONE);

        }else{
        holder.p_NoofPair.setText(String.format("No of pair: %s",products.get(position).getNoofPair()));}
        if(products.get(position).getPricepermetre()==null){
            holder.p_Pricepermetre.setVisibility(View.GONE);

        }else{
        holder.p_Pricepermetre.setText(String.format("Price per metre :₹ %s ",products.get(position).getPricepermetre()));}
        if(products.get(position).getPriceper100mtrs()==null){
            holder.p_Priceper100metre.setVisibility(View.GONE);

        }else{
        holder.p_Priceper100metre.setText(String.format("Price per 100 metre :₹ %s ",products.get(position).getPriceper100mtrs()));}
        if(products.get(position).getPriceper300mtrs()==null){
            holder.p_Priceper300metre.setVisibility(View.GONE);

        }else{
        holder.p_Priceper300metre.setText(String.format("Price per 300 metre :₹ %s ",products.get(position).getPriceper300mtrs()));}
        if(products.get(position).getPriceper305mtrs()==null){
            holder.p_Priceper305metre.setVisibility(View.GONE);

        }else{
        holder.p_Priceper305metre.setText(String.format("Price per 305 metre :₹ %s ",products.get(position).getPriceper305mtrs()));}
        if(products.get(position).getDescription()==null){
            holder.p_Description.setVisibility(View.GONE);

        }else
        holder.p_Description.setText(String.format("Description : %s",products.get(position).getDescription()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView p_code, p_nodia,pricepercoil,p_one_core,p_NominalDiaofCond_04,noperdia,p_noArea,p_NominalDiaofCond_05,p_NoofPair,p_Pricepermetre,p_Description,p_Priceper100metre,p_Priceper300metre,p_Priceper305metre,p_NominalAreaCond,p_two_core,p_three_core,p_noLength,p_pkg,p_price,
                p_NumberperDiaofWireinch,noi;
        private Button btnAddToCart,inc,dec;


        public ViewHolder(View itemView) {
            super(itemView);
            for (int i=0;i<products.size();i++){
                noitext.add(i,1);

            }
           // itemView.setOnClickListener(this);
            p_code=(TextView)itemView.findViewById(R.id.p_code);
            noi=(TextView)itemView.findViewById(R.id.noiedit);
            p_nodia=(TextView)itemView.findViewById(R.id.p_noperdia);
            p_noArea=(TextView)itemView.findViewById(R.id.p_noArea);
           p_pkg=(TextView)itemView.findViewById(R.id.p_pkg);
            p_noLength=(TextView)itemView.findViewById(R.id.p_noLength);
            pricepercoil=(TextView)itemView.findViewById(R.id.p_price);
            p_one_core=(TextView)itemView.findViewById(R.id.p_one_core);
            p_two_core=(TextView)itemView.findViewById(R.id.p_two_core);
            p_three_core=(TextView)itemView.findViewById(R.id.p_three_core);
            p_NominalAreaCond=(TextView)itemView.findViewById(R.id.p_NominalAreaCond);
            p_NominalDiaofCond_04=(TextView)itemView.findViewById(R.id.p_NominalDiaofCond_04);
            p_NominalDiaofCond_05=(TextView)itemView.findViewById(R.id.p_NominalDiaofCond_05);
            p_Description=(TextView)itemView.findViewById(R.id.p_Description);
            p_NoofPair=(TextView)itemView.findViewById(R.id.p_NoofPair);
            p_Pricepermetre=(TextView)itemView.findViewById(R.id.p_Pricepermetre);
            p_Priceper100metre=(TextView)itemView.findViewById(R.id.p_Priceper100metre);
            p_Priceper300metre=(TextView)itemView.findViewById(R.id.p_Priceper300metre);
            p_Priceper305metre=(TextView)itemView.findViewById(R.id.p_Priceper305metre);
            p_NumberperDiaofWireinch=(TextView)itemView.findViewById(R.id.p_NumberperDiaofWireinch);
            btnAddToCart=(Button)itemView.findViewById(R.id.addtoCart);
            btnAddToCart.setOnClickListener(this);
            inc=(Button)itemView.findViewById(R.id.inc);
            dec=(Button)itemView.findViewById(R.id.dec);
            inc.setOnClickListener(this);
            dec.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if(view.getId()==R.id.inc) {
                inc(pos,view);
            }
            if(view==dec){
                dec(pos,view);
            }
//            int pid = Integer.parseInt(products.get(pos).getP_id());
     //       int subpid= Integer.parseInt(products.get(pos).getSubproducts_id());
    //        listeners.onClickprolistener(pos,pid,subpid);
         //   ShowlengthDialog(view,pos);
           else if(view==btnAddToCart){AddToCart(view,pos);};





        }

    }
    private void inc(int pos,View view) {
        int a= noitext.get(pos);
        a=a+1;
        noitext.set(pos,a);
        products.get(pos).setNoi(noitext.get(pos));
        notifyDataSetChanged();
        //listener.textChanged(pos,String.valueOf(noitext.get(pos)));

    }
    private void dec(int pos,View view) {
        int a= noitext.get(pos);
        if(a<2){
          //  products.get(pos).setNoi(noitext.get(pos));
            Toast.makeText(view.getContext(),"Invalid Quantity",Toast.LENGTH_SHORT).show();
        }else {
            a = a - 1;
            noitext.set(pos, a);
            products.get(pos).setNoi(noitext.get(pos));
            notifyDataSetChanged();
        }
        //listener.textChanged(pos,String.valueOf(noitext.get(pos)));
    }

    private void ShowlengthDialog(View view, final String main_pid, final int pos) {
        ArrayList<String> lengths=new ArrayList<>();
        if(main_pid.equals("sub_8")){
            lengths.add("One core");
            lengths.add("Two core");
            lengths.add("Three core");

        }else if(main_pid.equals("sub_9")){
            lengths.add("Nominal Diameter of conductor 04");
            lengths.add("Nominal Diameter of conductor 05");

    }
        else if(main_pid.equals("sub_12")){
            lengths.add("Price per 100 meter");
            lengths.add("Price per 300 meter");
        }
        MaterialDialog materialDialog=new MaterialDialog.Builder((view.getContext()))
                .titleColor(view.getResources().getColor(R.color.white))
                .itemsColor(view.getResources().getColor(R.color.white))
                .widgetColor(view.getResources().getColor(R.color.primary_dark))
                .backgroundColor(view.getResources().getColor(R.color.color8))
                .items(lengths)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if(main_pid.equals("sub_8")){
                            if(which==0 & products.get(pos).getOne_Core()==null){
                                Toast.makeText(view.getContext(), "No such core exists", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            else if(which==2&products.get(pos).getThree_Core()==null){
                                Toast.makeText(view.getContext(), "No such core exists", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            else if (which==1&products.get(pos).getTwo_Core()==null) {
                                Toast.makeText(view.getContext(), "No such core exists", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                                switch (which) {
                                    case 0:
                                        price = products.get(pos).getOne_Core();
                                        addtooriginalcart(view,pos, price);
                                        break;
                                    case 1:
                                        price = products.get(pos).getTwo_Core();
                                        addtooriginalcart(view,pos, price);
                                        break;
                                    case 2:
                                        price = products.get(pos).getThree_Core();
                                        addtooriginalcart(view,pos, price);
                                        break;

                            }


                        }
                        else if(main_pid.equals("sub_12"))
                        {
                            if(which==0&&products.get(pos).getPriceper100mtrs()==null){
                                Toast.makeText(view.getContext(), "Select a valid option", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            else if(which==1&&products.get(pos).getPriceper300mtrs()==null){
                                Toast.makeText(view.getContext(), "Select a valid option", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            switch (which){
                                case  0:
                                    price=products.get(pos).getPriceper100mtrs();
                                    addtooriginalcart(view,pos, price);
                                    break;
                                case 1:
                                    price=products.get(pos).getPriceper300mtrs();
                                    addtooriginalcart(view,pos, price);
                                    break;
                            }

                        }
                        else if(main_pid.equals("sub_9"))
                        {
                            if(which==0&&products.get(pos).getNominalDiaofCond_04()==null){
                                Toast.makeText(view.getContext(), "Select a valid option", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            else if(which==1&&products.get(pos).getNominalDiaofCond_05()==null){
                                Toast.makeText(view.getContext(), "Select a valid option", Toast.LENGTH_SHORT).show();
                                ShowlengthDialog(view,main_pid,pos);
                            }
                            switch (which){
                                case  0:
                                    price=products.get(pos).getNominalDiaofCond_04();
                                    addtooriginalcart(view,pos, price);
                                    break;
                                case 1:
                                    price=products.get(pos).getNominalDiaofCond_05();
                                    addtooriginalcart(view,pos, price);
                                    break;
                            }

                        }


                            else
                        {
                            addtooriginalcart(view,pos, price);
                        }
                        //Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                        //startChildFragment(pos,pid, String.valueOf(text));

                        return true;
                    }
                })


                .title("Pick Length of Product")
                .build();
        materialDialog.show();
    }

    private void AddToCart(View view, int pos) {

        String mainid=products.get(pos).getMain_product_id();

            if(products.get(pos).getMain_product_id().equals("sub_8")||products.get(pos).getMain_product_id().equals("sub_9")||products.get(pos).getMain_product_id().equals("sub_12")){
                ShowlengthDialog(view,products.get(pos).getMain_product_id(),pos);
            }else
            {
                if(mainid.equals("sub_10")){
                    price=products.get(pos).getPricepermetre();
                }else if(mainid.equals("sub_13")){
                    price=products.get(pos).getPriceper305mtrs();
                }else if(mainid.equals("sub_14")){
                    price=products.get(pos).getPriceper100mtrs();
                }else{
                    price=products.get(pos).getPriceperCoil();
                }

                addtooriginalcart( view, pos,price);
            }


    }
    private void addtooriginalcart(View view, int pos, String price){
        SharedPreferences preferences=view.getContext().getApplicationContext().getSharedPreferences("ABC",Context.MODE_PRIVATE);
        /*SharedPreferences.Editor editor = preferences.edit();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < products.size(); i++) {
            str.append(products.get(i).getNoi()).append(",");
        }
        editor.putString(Constants.cartnoi, str.toString());
        editor.apply();*/


       MaterialDialog.Builder   materialDialog = new MaterialDialog.Builder(view.getContext())
                .content(R.string.loading)
                .widgetColor(Color.RED)
                .progress(true, 0);
        final MaterialDialog  mdialg=materialDialog.build();
        mdialg.show();


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        User user =new User();
        user.setProduct_code(products.get(pos).getProduct_code());
        user.setEmail(preferences.getString(Constants.EMAIL," "));
        user.setId(products.get(pos).getMain_product_id());
        user.setPrice(price);
      //  Toast.makeText(view.getContext(), price, Toast.LENGTH_SHORT).show();
        Log.d("ID",products.get(pos).getMain_product_id());
        user.setNoi(products.get(pos).getNoi());
        final ServerRequest request=new ServerRequest();
        request.setOperation(Constants.addtocart);
        request.setUser(user);
        RequestInterfaceProducts requestInterface=retrofit.create(RequestInterfaceProducts.class);
        Call<ProductResponse> response=requestInterface.operation(request);
        response.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                mdialg.dismiss();
                Toast.makeText(context,"Successfully added to cart",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mdialg.dismiss();
                Toast.makeText(context,"Product already exist ",Toast.LENGTH_SHORT).show();


            }
        });

    }

    }