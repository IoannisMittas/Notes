package com.mittas.notes.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.firebase.ui.auth.data.model.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<Note> getNoteById(int id);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = REPLACE)
    long insertNote(Note note);

    @Insert(onConflict = REPLACE)
    void insertNotes(List<Note> notes);

    @Query("UPDATE notes SET title = :title, bodytext = :bodyText WHERE id = :id")
    void updateNoteById(int id, String title, String bodyText);

    @Delete
    void deleteNotes(Note... notes);

}
