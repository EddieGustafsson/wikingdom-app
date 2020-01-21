package com.example.wikingdom_app.ui.wikipage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wikingdom_app.MainActivity;
import com.example.wikingdom_app.R;

public class PageFragment extends Fragment {

    private PageViewModel pageViewModel;
    private String search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                ((MainActivity)getActivity()).jsonParse("Home", true);
            }
        });
        return root;
    }
}
