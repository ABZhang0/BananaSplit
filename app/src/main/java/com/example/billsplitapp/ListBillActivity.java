package com.example.billsplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

public class ListBillActivity extends AppCompatActivity {

    private EditText editText2;
    private EditText editText3;

    private RecyclerView recyclerView;
    private BillAdapter rvAdapter;
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

        purchaseItems = new ArrayList<>();
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        buildRecyclerView();
    }

    public void sendPurchaseItem(View view) {
        String name = editText2.getText().toString();
        String price = editText3.getText().toString();

        if(!name.isEmpty() && !price.isEmpty()) {
            purchaseItems.add(new PurchaseItem(name, Double.parseDouble(price)));
            rvAdapter.notifyItemInserted(purchaseItems.size());
        }

        editText2.getText().clear();
        editText3.getText().clear();
        hideSoftKeyboard(this);
    }

    public void onFinish (View view) {
        if (!purchaseItems.isEmpty()) {
            Intent intent = new Intent(this, BuyerSelectActivity.class);
            intent.putExtra("buyers", buyers);
            intent.putExtra("purchases", purchaseItems);
            startActivity(intent);
        }
    }

    public void hideSoftKeyboard (Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void buildRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayoutManager);
        rvAdapter = new BillAdapter(purchaseItems);
        recyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnItemClickListener(new BillAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                purchaseItems.remove(position);
                rvAdapter.notifyItemRemoved(position);
            }
        });
    }
}
