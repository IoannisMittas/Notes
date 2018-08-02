package com.mittas.notes.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.mittas.notes.data.entity.Note;
import com.mittas.notes.data.entity.PinnedNote;

import java.util.List;

@Dao
public abstract class PinnedNoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract long insert(PinnedNote pinnedNote);

    @Query("SELECT * FROM notes INNER JOIN pinned_notes "
            + "ON notes.id = pinned_notes.noteId")
    abstract LiveData<List<Note>> getAllPinnedNotes();

    @Query("DELETE FROM pinned_notes WHERE noteId = :id")
    abstract void  deleteByNoteId(int id);
}
