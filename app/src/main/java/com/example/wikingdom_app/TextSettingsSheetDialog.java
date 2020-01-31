package com.example.wikingdom_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.annotation.Nullable;

import com.example.wikingdom_app.ui.article.ArticleActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;

public class TextSettingsSheetDialog extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences("settings",0);
        View v = inflater.inflate(R.layout.text_size_menu, container, false);
        SeekBar sb = v.findViewById(R.id.seekBarFont);

        sb.setProgress(pref.getInt("text_size", 2));

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences("settings",0);
                SharedPreferences.Editor editor = pref.edit();

                switch(progress){
                        case 0:
                            Log.d("Seekbar", "Active SMALLEST");
                            ((ArticleActivity) Objects.requireNonNull(getActivity())).changeTextSize(60);
                            editor.putInt("text_size", 0);
                            editor.apply();
                            break;
                        case 1:
                            Log.d("Seekbar", "Active SMALLER");
                            ((ArticleActivity) Objects.requireNonNull(getActivity())).changeTextSize(80);
                            editor.putInt("text_size", 1);
                            editor.apply();
                            break;
                        case 2:
                            Log.d("Seekbar", "Active NORMAL");
                            ((ArticleActivity) Objects.requireNonNull(getActivity())).changeTextSize(100);
                            editor.putInt("text_size", 2);
                            editor.apply();
                            break;
                        case 3:
                            Log.d("Seekbar", "Active LARGER");
                            ((ArticleActivity) Objects.requireNonNull(getActivity())).changeTextSize(120);
                            editor.putInt("text_size", 3);
                            editor.apply();
                            break;
                        case 4:
                            Log.d("Seekbar", "Active LARGEST");
                            ((ArticleActivity) Objects.requireNonNull(getActivity())).changeTextSize(140);
                            editor.putInt("text_size", 4);
                            editor.apply();
                            break;
                    }
                }
            });

        return v;
    }
}
