package com.example.wikingdom_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mukesh.MarkdownView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomSheetDialog bottomSheetDialog;
    private RequestQueue mQueue;

    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        mQueue = Volley.newRequestQueue(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse("Home", true);
            }

        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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
                        Toast.makeText(MainActivity.this, "Search Clicked",Toast.LENGTH_SHORT).show();
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

                bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(bottomNavigation);
                bottomSheetDialog.show();
                NavigationView navigationView = bottomNavigation.findViewById(R.id.toc_menu);

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch(id){
                            case R.id.nav1:
                                Toast.makeText(MainActivity.this, "Test1 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                            case R.id.nav2:
                                Toast.makeText(MainActivity.this, "Test2 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                            case R.id.nav3:
                                Toast.makeText(MainActivity.this, "Test3 Clicked",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

    }

    public void changeTextSize(int size){
        final MarkdownView wikiSource = findViewById(R.id.wikiSource);
        wikiSource.getSettings().setTextZoom(size);
    }

    public void scrollToTop(){
        final NestedScrollView homeScrollView = findViewById(R.id.homeScrollView);
        homeScrollView.smoothScrollTo(0,0);
    }

    public void jsonParse(final String title, final boolean debug){
        SharedPreferences pref = getSharedPreferences("settings",0);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.130.216.101/TP/api.php";

        final TextView wikiTitle = findViewById(R.id.wikiTitle);
        final TextView wikiDate = findViewById(R.id.wikiDate);
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
                                    String date = page.getString("datum");
                                    String source = page.getString("innehall");

                                    wikiTitle.setText(title);
                                    wikiDate.setText(date);
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
                params.put("wiki", "9");
                return params;
            }
        };

        queue.add(strRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
