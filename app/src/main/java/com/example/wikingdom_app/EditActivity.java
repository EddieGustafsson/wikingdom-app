package com.example.wikingdom_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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
        TextInputEditText textInputEditText = findViewById(R.id.textEditor);
        int startSelection = textInputEditText.getSelectionStart();
        int endSelection = textInputEditText.getSelectionEnd();
        String allText = Objects.requireNonNull(textInputEditText.getText()).toString();
        String selectedText = allText.substring(startSelection, endSelection);
        //final SpannableStringBuilder stringBuilder = new SpannableStringBuilder(textInputEditText.getText().toString());
        switch(mode){
            case "bold":
                if(!selectedText.isEmpty()) {
                    String editedText = "**" + selectedText + "**";
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection;
                    String emptyBold = "****";
                    textInputEditText.getText().insert(currentPosition, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                /*final StyleSpan boldText = new StyleSpan(android.graphics.Typeface.BOLD);
                stringBuilder.setSpan(boldText, startSelection, endSelection, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                textInputEditText.setText(stringBuilder);*/
                break;
            case "italic":
                if(!selectedText.isEmpty()) {
                    String editedText = "*" + selectedText + "*";
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection;
                    String emptyBold = "**";
                    textInputEditText.getText().insert(currentPosition, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                break;
            case "header":
                if(!selectedText.isEmpty()) {
                    String editedText = "#" + selectedText + "#";
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection;
                    String emptyBold = "##";
                    textInputEditText.getText().insert(currentPosition, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                break;
            case "quote":
                if(!selectedText.isEmpty()) {
                    String editedText = ">" + selectedText;
                    String editText = allText.replace(selectedText, editedText);
                    textInputEditText.setText(editText);
                }else{
                    int currentPosition = startSelection;
                    String emptyBold = ">";
                    textInputEditText.getText().insert(currentPosition, emptyBold);
                    textInputEditText.setSelection(currentPosition);
                }
                break;
            case "linebreak":
                Toast.makeText(EditActivity.this, "Linebreaks are not implemented yet...", Toast.LENGTH_SHORT).show();
                break;
            case "link":
                Toast.makeText(EditActivity.this, "Links are not implemented yet...", Toast.LENGTH_SHORT).show();
                break;
            case "image":
                Toast.makeText(EditActivity.this, "Images are not implemented yet...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
