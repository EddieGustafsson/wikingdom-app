package com.example.wikingdom_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Editor");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBarEditor);
        bottomAppBar.replaceMenu(R.menu.editor_menu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem menuItem){
                    int id = menuItem.getItemId();
                    String mode;
                    switch(id){
                        case R.id.bold_editor_menu:
                            mode = "bold";
                            setMode(mode);
                            break;
                        case R.id.italic_editor_menu:
                            mode = "italic";
                            setMode(mode);
                            break;
                        case R.id.header_editor_menu:
                            mode = "header";
                            setMode(mode);
                            break;
                        case R.id.quote_editor_menu:
                            mode = "quote";
                            setMode(mode);
                            break;
                        case R.id.linebreak_editor_menu:
                            mode = "linebreak";
                            setMode(mode);
                            break;
                        case R.id.link_editor_menu:
                            mode = "link";
                            setMode(mode);
                            break;
                        case R.id.image_editor_menu:
                            mode = "image";
                            setMode(mode);
                            break;
                    }
                    return false;
                }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setMode(String mode){
        switch(mode){
            case "bold":
                
                break;
            case "italic":

                break;
            case "header":

                break;
            case "quote":

                break;
            case "linebreak":

                break;
            case "link":

                break;
            case "image":

                break;
        }
    }

}
