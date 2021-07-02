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
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> list;

    public ItemAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = list.get(viewHolder.getAdapterPosition()).getName();
                int itemStock = list.get(viewHolder.getAdapterPosition()).getQuantity();
                int itemPrice = list.get(viewHolder.getAdapterPosition()).getPrice();

                Intent intent = new Intent(v.getContext(), EditItemActivity.class);
                intent.putExtra("item_name", itemName);
                intent.putExtra("item_stock", itemStock);
                intent.putExtra("item_price", itemPrice);
                v.getContext().startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Item item = list.get(position);
        holder.itemName.setText(item.getName());
        holder.price.setText("Rs."+item.getPrice());
        holder.stock.setText("Stock:"+item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView itemName, stock, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_view_name);
            stock = itemView.findViewById(R.id.item_view_stock);
            price = itemView.findViewById(R.id.item_view_price);

            layout = itemView.findViewById(R.id.item_layout);
        }
    }
}


