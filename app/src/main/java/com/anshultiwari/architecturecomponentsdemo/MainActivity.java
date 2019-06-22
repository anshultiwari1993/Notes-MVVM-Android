package com.anshultiwari.architecturecomponentsdemo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel mNoteViewModel;
    private FloatingActionButton mAddNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddNoteButton = findViewById(R.id.add_note);

        final RecyclerView notesRecyclerView = findViewById(R.id.notes);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        notesRecyclerView.setHasFixedSize(true);

        final NotesAdapter notesAdapter = new NotesAdapter();
        notesRecyclerView.setAdapter(notesAdapter);


        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d(TAG, "onChanged: notes size = " + notes.size());
                // Update the RecyclerView
                notesAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(notesRecyclerView);

        notesAdapter.setNoteItemClickListener(new NotesAdapter.OnNoteItemClickListener() {
            @Override
            public void onNoteItemClicked(Note note) {
                Log.d(TAG, "onNoteItemClicked: called");

                Intent i = new Intent(MainActivity.this, AddEditNoteActivity.class);
                i.putExtra(AddEditNoteActivity.ID, note.getId());
                Log.d(TAG, "onNoteItemClicked: note id = " + note.getId());

                i.putExtra(AddEditNoteActivity.TITLE, note.getTitle());
                i.putExtra(AddEditNoteActivity.DESCRIPTION, note.getDescription());
                i.putExtra(AddEditNoteActivity.PRIORITY, note.getPriority());
                startActivityForResult(i, EDIT_NOTE_REQUEST);
            }
        });

        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(i, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all: {
                mNoteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            Note note = new Note(title, desc, priority);
            mNoteViewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

        } else  if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            int id = data.getIntExtra(AddEditNoteActivity.ID, -1);

            if (id != -1) {
                Note note = new Note(title, desc, priority);
                note.setId(id);
                mNoteViewModel.update(note);

                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}