package com.mittas.notes;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.notes.data.AppDatabase;
import com.mittas.notes.data.NoteEntity;

import java.util.List;

/**
 * Repository handling the work with notes.
 */
public class NoteRepository {
    private static NoteRepository INSTANCE;
    private final AppDatabase database;
    private MediatorLiveData<List<NoteEntity>> observableNotes;

    private NoteRepository(final AppDatabase database) {
        this.database = database;

        observableNotes = new MediatorLiveData<>();
        observableNotes.addSource(this.database.noteDao().getAllNotes(),
                noteEntities -> observableNotes.postValue(noteEntities));
    }

    public static NoteRepository getInstance(final AppDatabase database) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(database);
        }
        return INSTANCE;
    }

    /**
     * Get the list of notes from the database and get notified when the data changes.
     */
    public LiveData<List<NoteEntity>> getAllNotes() {
        return observableNotes;
    }

    public NoteEntity getNoteById(final int noteId) {
        return database.noteDao().getNoteById(noteId);
    }

}
