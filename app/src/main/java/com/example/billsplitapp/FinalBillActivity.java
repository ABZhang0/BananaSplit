package com.example.billsplitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class FinalBillActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private static ArrayList<Buyer> buyers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_bill);

        try {
            buyers = (ArrayList<Buyer>) getIntent().getSerializableExtra("buyers");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        buildRecyclerView();
    }

    public void onFinishFinalBill (View view) {
        //conclude
        startActivity(new Intent(this, MainActivity.class));
    }

    public void buildRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView4);
        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayoutManager);
        rvAdapter = new MyAdapter(buyers);
        recyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                buyers.remove(position);
                rvAdapter.notifyItemRemoved(position);
            }
        });
    }
}
