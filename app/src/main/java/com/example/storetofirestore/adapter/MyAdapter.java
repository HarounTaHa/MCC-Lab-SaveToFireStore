package com.example.storetofirestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.storetofirestore.R;
import com.example.storetofirestore.model.PersonDetails;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<PersonDetails> list;

    public MyAdapter(List<PersonDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieve_item,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      PersonDetails personDetails =  list.get(position);
      holder.tvName.setText(personDetails.getName());
      holder.tvNumber.setText(personDetails.getNumber());
      holder.tvAddress.setText(personDetails.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvNumber;
        TextView tvAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.name_tv);
            tvNumber=itemView.findViewById(R.id.number_tv);
            tvAddress=itemView.findViewById(R.id.address_tv);
        }
    }
}
