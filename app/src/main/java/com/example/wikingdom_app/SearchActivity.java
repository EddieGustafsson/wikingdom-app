package com.example.wikingdom_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mukesh.MarkdownView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ListView searchList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchList = (ListView) findViewById(R.id.searchList);
        ArrayList<String> arraySearch = new ArrayList<>();

        arraySearch.addAll(Arrays.asList(getResources().getStringArray(R.array.availableSearches)));

        adapter = new ArrayAdapter<String>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                arraySearch
        );
        searchList.setAdapter(adapter);

        searchList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) searchList.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"You selected : " + item, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public LinkedList jsonParse(final String title, final boolean debug){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.130.216.101/TP/api.php";

        final LinkedList availableSearches = new LinkedList();

        StringRequest strRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONObject(response).getJSONArray(null);

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject currTitle = jsonArray.getJSONObject(i);
                                availableSearches.add(currTitle);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nyckel", "x9qXnagSOsS6sHun");
                params.put("tjanst", "wiki");
                params.put("typ", "JSON");
                params.put("sok", title);
                return params;
            }
        };

        queue.add(strRequest);

        return availableSearches;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.searchList);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
