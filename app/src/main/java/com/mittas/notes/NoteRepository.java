package com.mittas.notes;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.notes.data.AppDatabase;
import com.mittas.notes.data.Note;

import java.util.List;

/**
 * Repository handling the work with notes.
 */
public class NoteRepository {
    private static NoteRepository INSTANCE;
    private final AppDatabase database;
    private final AppExecutors executors;
    private MediatorLiveData<List<Note>> observableNotes;

    private NoteRepository(final AppDatabase database, final AppExecutors executors) {
        this.database = database;
        this.executors = executors;

        observableNotes = new MediatorLiveData<>();
        observableNotes.addSource(this.database.noteDao().getAllNotes(),
                notes -> observableNotes.postValue(notes));
    }

    public static NoteRepository getInstance(final AppDatabase database, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(database, executors);
        }
        return INSTANCE;
    }

    /**
     * Get the list of notes from the database and get notified when the data changes.
     */
    public LiveData<List<Note>> getAllNotes() {
        return observableNotes;
    }

    public LiveData<Note> getNoteById(final int noteId) {
        return database.noteDao().getNoteById(noteId);
    }

    public void addNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> database.noteDao().insertNote(note));
        }
    }

    public void updateNoteById(int noteId, String title, String bodyText) {
        executors.diskIO().execute(() -> database.noteDao().updateNoteById(noteId, title, bodyText));
    }

    public void deleteNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> database.noteDao().deleteNotes(note));
        }
    }
}
