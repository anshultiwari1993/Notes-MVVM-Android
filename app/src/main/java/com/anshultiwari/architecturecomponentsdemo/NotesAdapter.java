package com.anshultiwari.architecturecomponentsdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private static final String TAG = "NotesAdapter";

    private List<Note> notes = new ArrayList<>();
    private OnNoteItemClickListener mListener;

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);

        Log.d(TAG, "onBindViewHolder: title = " + note.getTitle());
        Log.d(TAG, "onBindViewHolder: description = " + note.getDescription());

        holder.titleTextView.setText(note.getTitle());
        holder.descTextView.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);


            titleTextView = itemView.findViewById(R.id.title);
            descTextView = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    Log.d(TAG, "onClick: called");
                    Log.d(TAG, "onClick: listener = " + mListener);
                    Log.d(TAG, "onClick: pos = " + pos);

                    if (mListener != null && pos != NO_POSITION) {
                        mListener.onNoteItemClicked(notes.get(pos));
                    }

                }
            });
        }
    }

    public interface OnNoteItemClickListener {
        void onNoteItemClicked(Note note);
    }

    public void setNoteItemClickListener(OnNoteItemClickListener listener) {
        mListener = listener;
    }
}
