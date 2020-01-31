package com.example.wikingdom_app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wikingdom_app.R;
import com.example.wikingdom_app.ui.article.ArticleActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.Objects;

public class HomeFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);

        SwipeRefreshLayout swipe = root.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //((MainActivity) Objects.requireNonNull(getActivity())).jsonParse(title, false);
            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                //((MainActivity) Objects.requireNonNull(getActivity())).jsonParse(title, false);
            }
        });

        /*
        search = root.findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchForArticle(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });*/

        MaterialSearchBar searchBar = root.findViewById(R.id.searchBar);
        searchBar.setHint("Search after articles...");
        searchBar.setSpeechMode(false);
        searchBar.setSearchIcon(R.drawable.ic_search_black_24dp);
        searchBar.setOnSearchActionListener(this);

        return root;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

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