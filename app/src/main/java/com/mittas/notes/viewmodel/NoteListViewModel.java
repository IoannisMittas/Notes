package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.mittas.notes.BasicApp;
import com.mittas.notes.data.Note;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel{
    private final MediatorLiveData<List<Note>> observableNotes;

    public NoteListViewModel(Application application) {
        super(application);

        observableNotes = new MediatorLiveData<>();

        LiveData<List<Note>> notesInput = ((BasicApp) application).getRepository()
                .getAllNotes();

        // observe the changes of the notes from the database and forward them
        observableNotes.addSource(notesInput, (notes) -> observableNotes.setValue(notes));
    }

    /**
     * Expose the LiveData notes query so the UI can observe it.
     */
    public LiveData<List<Note>> getAllNotes() {
        return observableNotes;
    }
}
