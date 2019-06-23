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

    private EditText mTitleEditText;
    private EditText mDescEditText;
    private TextView mNoteHeadTextView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mTitleEditText = findViewById(R.id.title);
        mDescEditText = findViewById(R.id.description);
        mNoteHeadTextView = findViewById(R.id.head);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");


        Intent i = getIntent();
        if (i.hasExtra(ID)) {
            mTitleEditText.setText(i.getStringExtra(TITLE));
            mDescEditText.setText(i.getStringExtra(DESCRIPTION));

            mNoteHeadTextView.setText("Edit Note");

        } else {
            mNoteHeadTextView.setText("Add Note");
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
        String title = mTitleEditText.getText().toString().trim();
        String desc = mDescEditText.getText().toString().trim();
        
        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, desc);

        int id = getIntent().getIntExtra(AddEditNoteActivity.ID, -1);
        Log.d(TAG, "saveNote: received id = " +  id);
        if (id != -1){
            intent.putExtra(ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

        
    }
}
