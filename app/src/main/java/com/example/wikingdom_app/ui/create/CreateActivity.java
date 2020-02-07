package com.example.wikingdom_app.ui.create;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wikingdom_app.MainActivity;
import com.example.wikingdom_app.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CreateActivity extends AppCompatActivity {

    private int headerTier = 1;
    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String title = getIntent().getStringExtra("ARTICLE_NAME");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Create");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        textInputEditText = findViewById(R.id.textEditor);

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
                    case R.id.list_editor_menu:
                        mode = "list";
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);// New activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void setMode(String mode){
        textInputEditText = findViewById(R.id.textEditor);
        int startSelection = textInputEditText.getSelectionStart();
        int endSelection = textInputEditText.getSelectionEnd();
        String allText = Objects.requireNonNull(textInputEditText.getText()).toString();
        String selectedText = allText.substring(startSelection, endSelection);
        switch(mode){
            case "bold":
                if(!selectedText.isEmpty()) {
                    String editedText = "**" + selectedText + "**";
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection + 2;
                    String emptyBold = "****";
                    textInputEditText.getText().insert(startSelection, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                headerTier = 1;
                break;
            case "italic":
                if(!selectedText.isEmpty()) {
                    String editedText = "*" + selectedText + "*";
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection + 1;
                    String emptyBold = "**";
                    textInputEditText.getText().insert(startSelection, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                headerTier = 1;
                break;
            case "header":
                if(!selectedText.isEmpty()) {
                    StringBuffer output = new StringBuffer(headerTier);
                    int currentPosition = startSelection + selectedText.length() + headerTier + 1;
                    for(int i = 0; i < headerTier; i++){
                        output.append("#");
                    }
                    output.append(" ");
                    output.append(selectedText);
                    String editText = allText.replace(selectedText, output);
                    textInputEditText.setText(editText);
                    textInputEditText.setSelection(currentPosition);
                    if(headerTier >= 5){
                        headerTier = 1;
                    }else{
                        headerTier++;
                    }
                }else{
                    StringBuffer output = new StringBuffer(headerTier);
                    int currentPosition = startSelection + headerTier + 1;
                    for(int j = 0; j < headerTier; j++){
                        output.append("#");
                    }
                    output.append(" ");
                    textInputEditText.getText().insert(startSelection, output);
                    textInputEditText.setSelection(currentPosition);
                    if(headerTier >= 5) {
                        headerTier = 1;
                    }else{
                        headerTier++;
                    }
                }
                break;
            case "quote":
                if(!selectedText.isEmpty()) {
                    String editedText = ">" + selectedText;
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection + 1;
                    String emptyBold = ">";
                    textInputEditText.getText().insert(startSelection, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                headerTier = 1;
                break;
            case "list":
                Toast.makeText(CreateActivity.this, "Lists are not implemented yet...", Toast.LENGTH_SHORT).show();
                headerTier = 1;
                break;
            case "linebreak":
                Toast.makeText(CreateActivity.this, "Linebreaks are not implemented yet...", Toast.LENGTH_SHORT).show();
                headerTier = 1;
                break;
            case "link":
                Toast.makeText(CreateActivity.this, "Links are not implemented yet...", Toast.LENGTH_SHORT).show();
                headerTier = 1;
                break;
            case "image":
                Toast.makeText(CreateActivity.this, "Images are not implemented yet...", Toast.LENGTH_SHORT).show();
                headerTier = 1;
                break;
        }
    }

}
