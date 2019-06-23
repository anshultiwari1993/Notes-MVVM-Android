package com.anshultiwari.architecturecomponentsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEditNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddEditNoteActivity";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";

    private EditText titleEditText;
    private EditText descEditText;
    private TextView noteHeadTextView;
    private Toolbar toolbar;
//    private NumberPicker priorityNumPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.title);
        descEditText = findViewById(R.id.description);
        noteHeadTextView = findViewById(R.id.head);
//        priorityNumPicker = findViewById(R.id.priority);

//        priorityNumPicker.setMinValue(1);
//        priorityNumPicker.setMaxValue(10);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");


        Intent i = getIntent();
        if (i.hasExtra(ID)) {
            titleEditText.setText(i.getStringExtra(TITLE));
            descEditText.setText(i.getStringExtra(DESCRIPTION));
//            priorityNumPicker.setValue(i.getIntExtra(PRIORITY, 1));

//            setTitle("Edit Note");
            noteHeadTextView.setText("Edit Note");

        } else {
//            setTitle("Add Note");
            noteHeadTextView.setText("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                saveNote();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String desc = descEditText.getText().toString().trim();
//        int priority = priorityNumPicker.getValue();
        
        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, desc);
//        intent.putExtra(PRIORITY, priority);

        int id = getIntent().getIntExtra(AddEditNoteActivity.ID, -1);
        Log.d(TAG, "saveNote: received id = " +  id);
        if (id != -1){
            intent.putExtra(ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

        
    }
}
