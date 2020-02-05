package com.example.wikingdom_app.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wikingdom_app.MainActivity;
import com.example.wikingdom_app.MarkdownView;
import com.example.wikingdom_app.R;
import com.example.wikingdom_app.ui.article.ArticleActivity;
import com.example.wikingdom_app.ui.history.HistoryActivity;
import com.example.wikingdom_app.ui.history.HistoryAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final MarkdownView wikiSource = root.findViewById(R.id.wikiSource);

        SwipeRefreshLayout swipe = root.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity) Objects.requireNonNull(getActivity())).jsonParse("home", false);
            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                ((MainActivity) Objects.requireNonNull(getActivity())).jsonParse("home", false);
            }
        });

        final MaterialSearchBar searchBar = root.findViewById(R.id.searchBar);
        final ListView lv = root.findViewById(R.id.mListView);
        lv.setVisibility(View.GONE);

        searchBar.setHint("Search after articles...");
        searchBar.setSpeechMode(false);
        searchBar.setSearchIcon(R.drawable.ic_search_black_24dp);
        searchBar.setOnSearchActionListener(this);

        /* Suggestions List */
        final List<String> suggestionsList = new ArrayList<>();

        ((MainActivity) Objects.requireNonNull(getActivity())).getArticle(new MainActivity.VolleyCallback(){
            @Override
            public void onSuccess(String result){
                try {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("sidor");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject page = jsonArray.getJSONObject(i);
                        Log.d("JSON", "index: " + i + " " + "title: " + page.getString("titel"));
                        suggestionsList.add(page.getString("titel"));
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });

        /* Adapter */
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, suggestionsList);
        lv.setAdapter(adapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchBar.getText().trim().equals("")){
                    lv.setVisibility(View.GONE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchForArticle(adapter.getItem(position));
            }
        });

        return root;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        ListView lv = Objects.requireNonNull(getActivity()).findViewById(R.id.mListView);
        String s = enabled ? "enabled" : "disabled";
        if(s.equals("disabled")){
            Log.d("Search", "disabled");
            lv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        searchForArticle(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        MaterialSearchBar searchBar = Objects.requireNonNull(getActivity()).findViewById(R.id.searchBar);

        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(getActivity(), "Button Nav ", Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }

    }

    private void searchForArticle(String query){
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("ARTICLE_NAME", query);
        intent.putExtra("ARTILCE_ID", "67");
        startActivity(intent);
    }

}