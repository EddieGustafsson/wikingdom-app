package com.example.wikingdom_app.ui.article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wikingdom_app.EditActivity;
import com.example.wikingdom_app.MarkdownView;
import com.example.wikingdom_app.R;
import com.example.wikingdom_app.TextSettingsSheetDialog;
import com.example.wikingdom_app.ui.history.HistoryActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArticleActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private String source;

    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String title = getIntent().getStringExtra("ARTICLE_NAME"); //Declares the home article by it's title.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_bar_main);

        /* ActionBar */

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Article");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /* Returns JSON article data */

        jsonParseArticle(title, false); //Initializing article data.

        /* Bottom navigation */

        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.replaceMenu(R.menu.bottom_bar_menu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.totop:
                        scrollToTop();
                        break;
                    case R.id.text:
                        TextSettingsSheetDialog bottomSheet = new TextSettingsSheetDialog();
                        bottomSheet.show(getSupportFragmentManager(),"changeTextSize");
                        break;
                    case R.id.search:
                        Toast.makeText(ArticleActivity.this, "Search Clicked",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openToc();
            }

            private void openToc() {
                final View bottomNavigation = getLayoutInflater().inflate(R.layout.toc_menu, null);

                bottomSheetDialog = new BottomSheetDialog(ArticleActivity.this);
                bottomSheetDialog.setContentView(bottomNavigation);
                bottomSheetDialog.show();
                NavigationView navigationView = bottomNavigation.findViewById(R.id.toc_menu);

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch(id){
                            case R.id.nav1:
                                Toast.makeText(ArticleActivity.this, "Test1 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                            case R.id.nav2:
                                Toast.makeText(ArticleActivity.this, "Test2 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                            case R.id.nav3:
                                Toast.makeText(ArticleActivity.this, "Test3 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        /* Swipe Refresh */

        SwipeRefreshLayout swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jsonParseArticle(title, false);
            }
        });

        /* Tabs Layout */

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("TAB", "Selected Article");
                        break;
                    case 1:
                        Intent intent2 = new Intent(ArticleActivity.this, EditActivity.class);
                        intent2.putExtra("ARTICLE_NAME", title);
                        intent2.putExtra("ARTICLE_SOURCE", source);
                        startActivity(intent2);
                        break;
                    case 2:
                        TextView wikiId = findViewById(R.id.wikiId);
                        Intent intent = new Intent(ArticleActivity.this, HistoryActivity.class);
                        intent.putExtra("ARTICLE_NAME", title);
                        intent.putExtra("ARTILCE_ID", wikiId.getText());
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    public void jsonParseArticle(final String title, final boolean debug){
        SharedPreferences pref = getSharedPreferences("settings",0);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.130.216.101/TP/api.php";

        SwipeRefreshLayout swipe;
        swipe = findViewById(R.id.swipe);
        swipe.setRefreshing(false);

        final TextView wikiTitle = findViewById(R.id.wikiTitle);
        final TextView wikiDate = findViewById(R.id.wikiDate);
        final TextView wikiId = findViewById(R.id.wikiId);
        final MarkdownView wikiSource = findViewById(R.id.wikiSource);
        final int fontSize = pref.getInt("text_size", 2);

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONObject(response).getJSONArray("sidor");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject page = jsonArray.getJSONObject(i);

                                if(page.getString("titel").toUpperCase().equals(title.toUpperCase())){
                                    String title = page.getString("titel");
                                    String id = page.getString("id");
                                    String date = page.getString("datum");
                                    source = page.getString("innehall");

                                    wikiTitle.setText(title);
                                    wikiId.setText(id);
                                    wikiDate.setText("Last Modified: " + date);
                                    wikiSource.setMarkDownText(source);

                                    switch(fontSize){
                                        case 0:
                                            changeTextSize(60);
                                            break;
                                        case 1:
                                            changeTextSize(80);
                                            break;
                                        case 2:
                                            changeTextSize(100);
                                            break;
                                        case 3:
                                            changeTextSize(120);
                                            break;
                                        case 4:
                                            changeTextSize(140);
                                            break;
                                    }

                                    if(debug){
                                        Log.d("JSON", "--------------START OF DEBUG--------------");
                                        Log.d("JSON", title + ",\n\n" + date + ",\n\n" + source + "\n\n");
                                        Log.d("JSON", "---------------END OF DEBUG---------------");
                                    }

                                    break;
                                }

                            }

                        } catch (JSONException e) {
                            Toast.makeText(ArticleActivity.this, "Error while executing request to server",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ArticleActivity.this, "Error while retrieving data from server",Toast.LENGTH_SHORT).show();
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
                params.put("wiki", "9");
                return params;
            }
        };

        queue.add(strRequest);

    }

    public void changeTextSize(int size){
        final MarkdownView wikiSource = findViewById(R.id.wikiSource);
        wikiSource.getSettings().setTextZoom(size);
    }

    public void scrollToTop(){
        final NestedScrollView homeScrollView = findViewById(R.id.homeScrollView);
        homeScrollView.smoothScrollTo(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
