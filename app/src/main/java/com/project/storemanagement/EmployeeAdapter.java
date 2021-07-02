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

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>  {

    Context context;
    ArrayList<Employee> list;

    public EmployeeAdapter(Context context, ArrayList<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.payment_view, parent, false);
        final EmployeeAdapter.MyViewHolder viewHolder = new EmployeeAdapter.MyViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empName = list.get(viewHolder.getAdapterPosition()).getName();
                String empPhone = list.get(viewHolder.getAdapterPosition()).getPhone_number();
                int empSalary = list.get(viewHolder.getAdapterPosition()).getSalary();
                int empLeaves= list.get(viewHolder.getAdapterPosition()).getLeaves();


                Intent intent = new Intent(v.getContext(), EditEmployeeActivity.class);
                intent.putExtra("emp_name", empName);
                intent.putExtra("emp_phone", empPhone);
                intent.putExtra("emp_salary", empSalary);
                intent.putExtra("emp_leaves", empLeaves);

                v.getContext().startActivity(intent);


            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Employee employee =list.get(position);
        holder.empName.setText(employee.getName());
        holder.empPhone.setText(employee.getPhone_number());
        holder.empSalary.setText("Rs."+employee.getSalary());
        holder.empLeaves.setText("Leaves: "+employee.getLeaves());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView empName, empPhone, empSalary, empLeaves;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            empLeaves = itemView.findViewById(R.id.emp_view_leaves);
            empName = itemView.findViewById(R.id.emp_view_name);
            empPhone = itemView.findViewById(R.id.emp_view_phone);
            empSalary = itemView.findViewById(R.id.emp_view_salary);
            layout = itemView.findViewById(R.id.layout_emp);
        }
    }
}
