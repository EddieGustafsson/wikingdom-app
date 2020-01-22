package com.example.wikingdom_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mukesh.MarkdownView;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class TextSettingsSheetDialog extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences pref = getActivity().getSharedPreferences("settings",0);
        View v = inflater.inflate(R.layout.text_size_menu, container, false);
        View home = inflater.inflate(R.layout.fragment_home, container, false);
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
                SharedPreferences pref = getActivity().getSharedPreferences("settings",0);
                SharedPreferences.Editor editor = pref.edit();

                switch(progress){
                        case 0:
                            Log.d("Seekbar", "Active SMALLEST");
                            ((MainActivity) Objects.requireNonNull(getActivity())).changeTextSize(60);
                            editor.putInt("text_size", 0);
                            editor.commit();
                            break;
                        case 1:
                            Log.d("Seekbar", "Active SMALLER");
                            ((MainActivity) Objects.requireNonNull(getActivity())).changeTextSize(80);
                            editor.putInt("text_size", 1);
                            editor.commit();
                            break;
                        case 2:
                            Log.d("Seekbar", "Active NORMAL");
                            ((MainActivity) Objects.requireNonNull(getActivity())).changeTextSize(100);
                            editor.putInt("text_size", 2);
                            editor.commit();
                            break;
                        case 3:
                            Log.d("Seekbar", "Active LARGER");
                            ((MainActivity) Objects.requireNonNull(getActivity())).changeTextSize(120);
                            editor.putInt("text_size", 3);
                            editor.commit();
                            break;
                        case 4:
                            Log.d("Seekbar", "Active LARGEST");
                            ((MainActivity) Objects.requireNonNull(getActivity())).changeTextSize(140);
                            editor.putInt("text_size", 4);
                            editor.commit();
                            break;
                    }
                }
            });

        return v;
    }
}
