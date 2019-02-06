package com.example.billsplitapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {
    private ArrayList<PurchaseItem> purchaseItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView textView3;
        public TextView textView4;
        public ImageView deleteImage;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            textView3 = view.findViewById(R.id.textView3);
            textView4 = view.findViewById(R.id.textView4);
            deleteImage = view.findViewById(R.id.imageView3);

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

    BillAdapter(ArrayList<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_item, viewGroup, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        PurchaseItem purchaseItem = purchaseItems.get(i);
        myViewHolder.textView3.setText(purchaseItem.getName());
        myViewHolder.textView4.setText(String.format("$%.2f", purchaseItem.price()));
    }

    @Override
    public int getItemCount() {
        return purchaseItems.size();
    }
}
