package com.mittas.notes;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.notes.data.LocalDatabase;
import com.mittas.notes.data.entity.Note;
import com.mittas.notes.data.RemoteDatabase;

import java.util.List;

/**
 * Repository handling the work with notes.
 */
public class NoteRepository {
    private static NoteRepository INSTANCE;
    private final LocalDatabase localDb;
    private final RemoteDatabase remoteDb;
    private final AppExecutors executors;
    private MediatorLiveData<List<Note>> observableNotes;

    private NoteRepository(final LocalDatabase localDb, final RemoteDatabase remoteDb, final AppExecutors executors) {
        this.localDb = localDb;
        this.remoteDb = remoteDb;
        this.executors = executors;

        observableNotes = new MediatorLiveData<>();
        observableNotes.addSource(this.localDb.noteDao().getAllNotes(),
                notes -> observableNotes.postValue(notes));
    }

    public static NoteRepository getInstance(final LocalDatabase localDb, final RemoteDatabase remoteDb, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(localDb, remoteDb, executors);
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

                remoteDb.addNote(note, noteId);
            });
        }
    }

    public void updateNoteById(int noteId, String title, String bodyText) {
        executors.diskIO().execute(() -> localDb.noteDao().updateNoteById(noteId, title, bodyText));

        remoteDb.updateNote(noteId, title, bodyText);
    }

    public void deleteNote(final Note note) {
        if (note != null) {
            executors.diskIO().execute(() -> localDb.noteDao().deleteNotes(note));

            remoteDb.deleteNote(note.getId());
        }
    }

    public void syncNotes() {
        // Reset db for new data are to be fetched
        executors.diskIO().execute(() -> localDb.clearAllTables());

        remoteDb.addOnSyncRequestListener(new RemoteDatabase.syncRequestListener() {
            @Override
            public void onSuccess(List<Note> allNotes) {
                if (allNotes != null) {
                    executors.diskIO().execute(() -> localDb.noteDao().insertNotes(allNotes));
                }
            }

            @Override
            public void onFailure(String message) {
                // do nothing
            }
        });
    }
}
