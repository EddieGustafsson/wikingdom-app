package com.example.wikingdom_app.ui.history;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wikingdom_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("ARTICLE_NAME");
        String id = getIntent().getStringExtra("ARTILCE_ID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("History - " + title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        jsonParseHistory(id);
    }

    /* Actionbar back button */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                break;
        }
        return true;
    }

    //Returns an arrayList with history for the selected article id.

    public void jsonParseHistory(final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.130.216.101/TP/api.php";

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<HistoryAdapter.HistoryItem> historyItemArrayList = new ArrayList<>();

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject page = jsonArray.getJSONObject(i);
                                historyItemArrayList.add(new HistoryAdapter.HistoryItem("#" + page.getString("id") + " " + page.getString("titel"), page.getString("datum")));
                            }

                            Collections.reverse(historyItemArrayList);

                            mRecyclerView = findViewById(R.id.recyclerView);
                            mRecyclerView.setHasFixedSize(true);
                            mlayoutManager = new LinearLayoutManager(HistoryActivity.this);
                            mAdapter = new HistoryAdapter(historyItemArrayList);

                            mRecyclerView.setLayoutManager(mlayoutManager);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {

                            ArrayList<HistoryAdapter.HistoryItem> historyItemArrayList = new ArrayList<>();
                            historyItemArrayList.add(new HistoryAdapter.HistoryItem("Info", "No previous versions of this article."));

                            mRecyclerView = findViewById(R.id.recyclerView);
                            mRecyclerView.setHasFixedSize(true);
                            mlayoutManager = new LinearLayoutManager(HistoryActivity.this);
                            mAdapter = new HistoryAdapter(historyItemArrayList);

                            mRecyclerView.setLayoutManager(mlayoutManager);
                            mRecyclerView.setAdapter(mAdapter);

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(HistoryActivity.this, "Error while retrieving history data from server",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nyckel", "x9qXnagSOsS6sHun");
                params.put("tjanst", "wiki");
                params.put("typ", "JSON");
                params.put("sidId", id);
                return params;
            }
        };

        queue.add(strRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }
}
