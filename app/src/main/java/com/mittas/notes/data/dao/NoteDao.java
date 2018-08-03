package com.mittas.notes.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mittas.notes.data.entity.Note;

import java.util.List;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<Note> getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY isPinned DESC")
    LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = REPLACE)
    long insertNote(Note note);

    @Insert(onConflict = REPLACE)
    void insertNotes(List<Note> notes);

    @Query("UPDATE notes SET title = :title, bodytext = :bodyText WHERE id = :id")
    void updateNoteById(int id, String title, String bodyText);

    @Delete
    void deleteNotes(Note... notes);

    @Query("SELECT isPinned FROM notes WHERE id = :id")
    boolean isNotePinned(int id);

    // Room library limitation: have to hardcode in queries 1 as true, and 0 as false,
    // as Room treats boolean as integer
    @Query("UPDATE notes SET isPinned = 1 WHERE id = :id")
    void setNotePinned(int id);

    @Query("UPDATE notes SET isPinned = 0 WHERE id = :id")
    void unPinNote(int id);

    @Query("SELECT * FROM notes WHERE isPinned = 1")
    LiveData<Note> getAllPinnedNotes();
}
