package com.mittas.notes.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.notes.R;
import com.mittas.notes.data.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{
    private List<Note> noteList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public NoteListAdapter(List<Note> noteList, OnItemClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notelist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.bodyText.setText(note.getBodyText());

        holder.itemView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView bodyText;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_textview);
            bodyText = view.findViewById(R.id.bodytext_textview);
        }
    }
}
