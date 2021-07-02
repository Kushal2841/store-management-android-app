package com.project.storemanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {


    Context context;
    ArrayList<Customer> list;

    public CustomerAdapter(Context context, ArrayList<Customer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customer_view, parent, false);
        final CustomerAdapter.MyViewHolder viewHolder = new CustomerAdapter.MyViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusName = list.get(viewHolder.getAdapterPosition()).getName();
                String cusPhone = list.get(viewHolder.getAdapterPosition()).getPhone_number();
                int cusDebt = list.get(viewHolder.getAdapterPosition()).getDebt();

                Intent intent = new Intent(v.getContext(), EditCustomerActivity.class);
                intent.putExtra("cus_name", cusName);
                intent.putExtra("cus_phone", cusPhone);
                intent.putExtra("cus_debt", cusDebt);
                v.getContext().startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Customer customer =list.get(position);
        holder.cusName.setText(customer.getName());
        holder.cusPhone.setText(customer.getPhone_number());
        holder.cusDebt.setText("Rs." +customer.getDebt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cusName, cusPhone, cusDebt;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cusName = itemView.findViewById(R.id.customer_view_name);
            cusPhone = itemView.findViewById(R.id.cus_view_phone);
            cusDebt = itemView.findViewById(R.id.cus_view_debt);
            layout = itemView.findViewById(R.id.layout_cus);
        }
    }
}
