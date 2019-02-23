package com.znk.architecutrec;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST=1;
    public static final int UPDATE_NOTE_REQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter=new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
        FloatingActionButton floatingActionButton=findViewById(R.id.button_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                  noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                  
            }

        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_NOTE_REQUEST && resultCode ==RESULT_OK){

            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note=new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode==UPDATE_NOTE_REQUEST && resultCode==RESULT_OK){
            String tit=data.getStringExtra(UpdateNote.EXTRA_TITLE);
//            String des=data.getStringExtra(UpdateNote.EXTRA_DESCRIPTION);
            int id=data.getIntExtra(UpdateNote.EXTRA_IDENTIFIY,1);
           // Note bla=new Note();
            noteViewModel.update(id,data.getStringExtra(UpdateNote.EXTRA_TITLE),data.getStringExtra(UpdateNote.EXTRA_DESCRIPTION),data.getIntExtra(UpdateNote.EXTRA_PRIORITY,1));
            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()){
                case R.id.delete_all_notes:
                    noteViewModel.deleteallnote();
                    Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }
}
