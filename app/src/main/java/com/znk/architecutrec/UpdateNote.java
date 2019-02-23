package com.znk.architecutrec;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class UpdateNote extends AppCompatActivity {

    public static final String EXTRA_TITLE="title";
    public static final String EXTRA_DESCRIPTION="description";
    public static final String EXTRA_PRIORITY="priority";
    public static final String EXTRA_IDENTIFIY="identity";
    private EditText editText_title;
    private EditText editText_description;
    private NumberPicker numberPicker_priority;
    public   int priority;
    private String title,description;
    private NoteViewModel noteViewModel;
    public  static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("id");
        title=bundle.getString("title");
        description=bundle.getString("description");
        priority=bundle.getInt("priority");



        //Toast.makeText(getApplicationContext(),String.valueOf(id),Toast.LENGTH_LONG).show();


        editText_title=(EditText)findViewById(R.id.edit_text_title_update);
        editText_description=(EditText)findViewById(R.id.edit_text_description_update);
        numberPicker_priority=(NumberPicker)findViewById(R.id.number_picker_update);

        editText_description.setText(description);
        editText_title.setText(title);

        numberPicker_priority.setMinValue(1);
        numberPicker_priority.setMaxValue(10);
        numberPicker_priority.setValue(priority);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Update Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }
    private void update(){
        String title=editText_title.getText().toString();
        String description=editText_description.getText().toString();
        int priority=numberPicker_priority.getValue();
        int id=UpdateNote.id;
        if (title.trim().isEmpty() || description.isEmpty())
        {
            Toast.makeText(this, "Please Insert title and des", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_IDENTIFIY,id);
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        setResult(RESULT_OK,data);
     //   Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save_note:
                update();
                return  true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
