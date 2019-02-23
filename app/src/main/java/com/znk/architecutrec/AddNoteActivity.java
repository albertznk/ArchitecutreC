package com.znk.architecutrec;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE="com.znk.architecutrec.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.znk.architecutrec.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="com.znk.architecutrec.EXTRA_PRIORITY";
    private EditText editText_title;
    private EditText editText_description;
    private NumberPicker numberPicker_priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        editText_title=(EditText)findViewById(R.id.edit_text_title);
        editText_description=(EditText)findViewById(R.id.edit_text_description);
        numberPicker_priority=(NumberPicker)findViewById(R.id.number_picker);


        numberPicker_priority.setMinValue(1);
        numberPicker_priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);


        return true;
    }
    private void saveNote(){
        String title=editText_title.getText().toString();
        String description=editText_description.getText().toString();
        int priority=numberPicker_priority.getValue();

        if (title.trim().isEmpty() || description.isEmpty())
        {
            Toast.makeText(this, "Please Insert title and des", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save_note:
                saveNote();
                return  true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }
}
