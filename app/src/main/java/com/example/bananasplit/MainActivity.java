package com.example.bananasplit;

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


public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private RecyclerView recyclerView;
    private MyAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private static ArrayList<Buyer> buyers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buyers = new ArrayList<>();

        editText = (EditText) findViewById(R.id.editText);

        buildRecyclerView();
        
    }

    public void sendName (View view) {
        String name = editText.getText().toString();

        if (!name.equals("")) {
            buyers.add(new Buyer(name));
            rvAdapter.notifyItemInserted(buyers.size());
        }

        editText.getText().clear();
        hideSoftKeyboard(this);
    }

    public void onFinish (View view) {
        if (!buyers.isEmpty()) {
            Intent intent = new Intent(this, ListBillActivity.class);
            intent.putExtra("buyers", buyers);
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
