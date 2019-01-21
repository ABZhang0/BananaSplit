package com.example.billsplitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;

public class ListBillActivity extends AppCompatActivity {

    private EditText editText;

    private RecyclerView recyclerView;
    private MyAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private ArrayList<PurchaseItem> purchaseItems;

    private static ArrayList<Buyer> buyers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);

        try {
            buyers = (ArrayList<Buyer>) getIntent().getSerializableExtra("buyers");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
