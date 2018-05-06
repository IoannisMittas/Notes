package com.mittas.notes.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mittas.notes.R;
import com.mittas.notes.data.NoteEntity;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{
    private List<NoteEntity> noteList;

    public NoteListAdapter(List<NoteEntity> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notelist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        NoteEntity note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.bodyText.setText(note.getBodyText());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<NoteEntity> noteList) {
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
