package com.project.storemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder>{

    Context context;
    ArrayList<Purchase> list;

    public PurchaseAdapter(Context context, ArrayList<Purchase> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.purchase_view, parent, false);
        final PurchaseAdapter.MyViewHolder viewHolder = new PurchaseAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Purchase purchase = list.get(position);
        holder.purCusName.setText(purchase.getCustomerNamePur());
        holder.purCusPhone.setText(purchase.getCustomerPhonePur());
        holder.purProduct.setText(purchase.getProductNamePur());
        holder.purAmount.setText("Rs."+ purchase.getAmountPur());
        holder.purQuantity.setText(""+ purchase.getQuantityPur());
        holder.purStatus.setText(purchase.getPurchaseStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  purCusName, purCusPhone, purProduct, purQuantity, purAmount, purStatus;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            purCusName = itemView.findViewById(R.id.purchase_cus_Name);
            purCusPhone = itemView.findViewById(R.id.purchase_cus_phone);
            purProduct  = itemView.findViewById(R.id.purchase_pro_name);
            purAmount = itemView.findViewById(R.id.purchase_amount);
            purQuantity = itemView.findViewById(R.id.purchase_quantity);
            purStatus = itemView.findViewById(R.id.pur_status);
            layout = itemView.findViewById(R.id.layout_pur);
        }
    }
}
