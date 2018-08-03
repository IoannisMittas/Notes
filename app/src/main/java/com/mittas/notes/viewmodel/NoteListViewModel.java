package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.notes.BasicApp;
import com.mittas.notes.NoteRepository;
import com.mittas.notes.data.entity.Note;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel{
    private final MediatorLiveData<List<Note>> observableNotes;
    private final NoteRepository repository;

    public NoteListViewModel(Application application) {
        super(application);

        repository = ((BasicApp) application).getRepository();

        observableNotes = new MediatorLiveData<>();

        LiveData<List<Note>> notesInput = repository.getAllNotes();

        // observe the changes of the notes from the database and forward them
        observableNotes.addSource(notesInput, (notes) -> observableNotes.setValue(notes));
    }

    /**
     * Expose the LiveData notes query so the UI can observe it.
     */
    public LiveData<List<Note>> getAllNotes() {
        return observableNotes;
    }

    public void syncNotes() {
        repository.syncNotes();
    }

    public void deleteNote(final Note note) {
                repository.deleteNote(note);
    }

    public void onPinToggleClicked(final int noteId) {
        repository.onPinToggleClicked(noteId);
    }
}
