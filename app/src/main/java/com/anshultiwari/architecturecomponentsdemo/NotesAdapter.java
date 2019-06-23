package com.anshultiwari.architecturecomponentsdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NoteHolder> {
    private static final String TAG = "NotesAdapter";

    private OnNoteItemClickListener mListener;

    protected NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };


    public Note getNoteAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = getItem(position);

        Log.d(TAG, "onBindViewHolder: title = " + note.getTitle());
        Log.d(TAG, "onBindViewHolder: description = " + note.getDescription());

        holder.titleTextView.setText(note.getTitle());
        holder.descTextView.setText(note.getDescription());
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
                        mListener.onNoteItemClicked(getItem(pos));
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
