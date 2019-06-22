package com.anshultiwari.architecturecomponentsdemo;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> mAllNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        mNoteRepository = new NoteRepository(application);
        mAllNotes = mNoteRepository.getAllNotes();
    }


    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    public void update(Note note) {
        mNoteRepository.update(note);
    }

    public void delete(Note note) {
        mNoteRepository.delete(note);
    }

    public void deleteAllNotes() {
        mNoteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

}
