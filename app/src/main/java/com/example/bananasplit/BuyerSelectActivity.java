package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class BuyerSelectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BuyerSelectAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private ArrayList<PurchaseItem> purchaseItems;
    private static ArrayList<Buyer> buyers;
    private ArrayList<Buyer> selectedBuyers;
    private Stack<ArrayList<Buyer>> buyerHistory;
    private Stack<PurchaseItem> pItemHistory;

    private TextView displayName;
    private TextView displayPrice;
    private int pIndex;
    private boolean isComplete;

    Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_select);
        nextButton = (Button) findViewById(R.id.button5);

        try {
            buyers = (ArrayList<Buyer>) getIntent().getSerializableExtra("buyers");
            purchaseItems = (ArrayList<PurchaseItem>) getIntent().getSerializableExtra("purchases");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        displayName = findViewById(R.id.textView5);
        displayPrice = findViewById(R.id.textView6);
        selectedBuyers = new ArrayList<>();
        buyerHistory = new Stack<>();
        pItemHistory = new Stack<>();
        buildRecyclerView();

        displayName.setText(purchaseItems.get(0).getName());
        displayPrice.setText(String.format("$%.2f", purchaseItems.get(0).price()));
        pIndex = 1;
        isComplete = false;
    }

    public void prevPurchaseItem(View view) {
        if (pIndex > 1) {
            ArrayList<Buyer> lastBuyers = buyerHistory.pop();
            PurchaseItem display = pItemHistory.pop();
            for (Buyer b : lastBuyers) {
                b.subFromBill(display.price()/lastBuyers.size());
                b.deselect();
                rvAdapter.notifyDataSetChanged();
            }
            displayName.setText(display.getName());
            displayPrice.setText(String.format("$%.2f", display.price()));
            selectedBuyers.clear(); //should fix the uneven split bug
            pIndex--;

            if (isComplete) {
                isComplete = false;
                nextButton.setText(R.string.next_button);
            }
        }
    }

    public void nextPurchaseItem(View view) {
        if (isComplete) {
            onFinishBuyerSelect();
        } else if (selectedBuyers.size() > 0 && pIndex <= purchaseItems.size()) {
            for (Buyer b : selectedBuyers) {
                b.addToBill(purchaseItems.get(pIndex-1).price()/selectedBuyers.size()); //split the bill
                b.deselect();
                rvAdapter.notifyDataSetChanged();
            }
            pItemHistory.push(purchaseItems.get(pIndex-1));
            ArrayList<Buyer> copy = new ArrayList<>();
            for (Buyer b : selectedBuyers) {
                copy.add(b);
            }
            buyerHistory.push(copy);
            selectedBuyers.clear();

            if (pIndex < purchaseItems.size()) {
                PurchaseItem pItem = purchaseItems.get(pIndex);
                displayName.setText(pItem.getName());
                displayPrice.setText(String.format("$%.2f", pItem.price()));
            } else {
                isComplete = true;
                nextButton.setText(R.string.done_button);
                displayName.setText(null);
                displayPrice.setText(null);
            }
            pIndex++;
        }
    }

    public void onFinishBuyerSelect() {
        //next activity (probably final numbers)
        Intent intent = new Intent(this, FinalBillActivity.class);
        intent.putExtra("buyers", buyers);
        startActivity(intent);
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView3);
        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayoutManager);
        rvAdapter = new BuyerSelectAdapter(buyers);
        recyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnItemClickListener(new BuyerSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!isComplete) {
                    Buyer buyer = buyers.get(position);
                    if (!buyer.isSelected()) {
                        selectedBuyers.add(buyer); // do not add to list if deselection
                        buyer.select();
                    } else {
                        selectedBuyers.remove(buyer);
                        buyer.deselect();
                    }
                    rvAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
