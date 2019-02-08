package com.example.bananasplit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Buyer> buyers;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public ImageView deleteImage;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView);
            textView2 = view.findViewById(R.id.textView2);
            deleteImage = view.findViewById(R.id.imageView2);

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    MyAdapter(ArrayList<Buyer> buyers) {
        this.buyers = buyers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_item, viewGroup, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Buyer buyer = buyers.get(i);
        myViewHolder.imageView.setImageResource(buyer.getPortrait());
        myViewHolder.textView.setText(buyer.getName());
        myViewHolder.textView2.setText(String.format("$%.2f", buyer.getBill()));
    }

    @Override
    public int getItemCount() {
        return buyers.size();
    }
}
