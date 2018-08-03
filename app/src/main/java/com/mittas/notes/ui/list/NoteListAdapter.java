package com.mittas.notes.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mittas.notes.R;
import com.mittas.notes.data.entity.Note;
import com.mittas.notes.ui.list.gestures.ItemTouchHelperAdapter;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<Note> noteList;
    private OnItemClickListener noteClickListener;
    private OnItemClickListener pinClickListener;
    private OnNoteSwipeCallback swipeCallback;


    public interface OnItemClickListener {
        void onItemClick(View view, int noteId);
    }

    public interface OnNoteSwipeCallback {
        void onNoteSwipe(Note note, int direction);
    }

    public NoteListAdapter(List<Note> noteList, OnItemClickListener noteClickListener,OnItemClickListener pinClickListener,  OnNoteSwipeCallback swipeCallback) {
        this.noteList = noteList;
        this.noteClickListener = noteClickListener;
        this.swipeCallback = swipeCallback;
        this.pinClickListener = pinClickListener;
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

        holder.itemView.setOnClickListener((v -> noteClickListener.onItemClick(v, note.getId())));

        if(note.isPinned()){
            holder.pinToggle.setImageResource(R.drawable.ic_favourite_toggle_on);
        } else {
            holder.pinToggle.setImageResource(R.drawable.ic_favorite_toggle_off);
        }

        holder.pinToggle.setOnClickListener(v -> pinClickListener.onItemClick(v, note.getId()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        // Do nothing
    }

    /**
     * Called on swiping
     *
     * @param position The position of the item dismissed.
     * @param direction The direction of the swiping.
     */
    @Override
    public void onItemDismiss(int position, int direction) {
        Note note = noteList.get(position);
        swipeCallback.onNoteSwipe(note, direction);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView bodyText;
        private ImageButton pinToggle;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_textview);
            bodyText = view.findViewById(R.id.bodytext_textview);
            pinToggle = view.findViewById(R.id.pin_toggle);
        }
    }
}
