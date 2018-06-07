package com.mittas.notes;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.mittas.notes.data.LocalDatabase;
import com.mittas.notes.data.Note;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository handling the work with notes.
 */
public class NoteRepository {
    private static NoteRepository INSTANCE;
    private final LocalDatabase localDb;
    private final DatabaseReference firebaseDb;
    private final AppExecutors executors;
    private MediatorLiveData<List<Note>> observableNotes;

    private NoteRepository(final LocalDatabase localDb, final DatabaseReference firebaseDb, final AppExecutors executors) {
        this.localDb = localDb;
        this.firebaseDb = firebaseDb;
        this.executors = executors;

        observableNotes = new MediatorLiveData<>();
        observableNotes.addSource(this.localDb.noteDao().getAllNotes(),
                notes -> observableNotes.postValue(notes));
    }

    public static NoteRepository getInstance(final LocalDatabase localDb,final DatabaseReference firebaseDb, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(localDb, firebaseDb, executors);
        }
        return INSTANCE;
    }

    /**
     * Get the list of notes from the localDb and get notified when the data changes.
     */
    public LiveData<List<Note>> getAllNotes() {
        return observableNotes;
    }

    public LiveData<Note> getNoteById(final int noteId) {
        return localDb.noteDao().getNoteById(noteId);
    }

    public void addNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> {
                long noteId = localDb.noteDao().insertNote(note);

                firebaseDb.child("notes").child(Long.toString(noteId)).setValue(note);
            });
        }
    }

    public void updateNoteById(int noteId, String title, String bodyText) {
        executors.diskIO().execute(() -> localDb.noteDao().updateNoteById(noteId, title, bodyText));

        HashMap<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/" + noteId + "/title", title);
        childUpdates.put("/notes/" + noteId + "/bodyText", bodyText);
        firebaseDb.updateChildren(childUpdates);
    }

    public void deleteNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> localDb.noteDao().deleteNotes(note));

            int noteId = note.getId();
            firebaseDb.child("notes").child(Integer.toString(noteId)).removeValue();
        }
    }

    public void syncNotes() {

    }
}
