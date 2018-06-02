package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.mittas.notes.BasicApp;
import com.mittas.notes.NoteRepository;
import com.mittas.notes.data.Note;

public class DetailViewModel extends AndroidViewModel {
    private final NoteRepository repository;
    private final LiveData<Note> observableNote;
    private final MutableLiveData<Integer> idInput;


    public DetailViewModel(@NonNull Application application) {
        super(application);

        repository = ((BasicApp) application).getRepository();

        idInput = new MutableLiveData<>();

        observableNote = Transformations.switchMap(idInput, id ->
             repository.getNoteById(id));
    }

    public LiveData<Note> getNoteById(int noteId) {
        setNoteId(noteId);
        return observableNote;
    }

    public void updateNoteById(int noteId, String title, String bodyText) {
        repository.updateNoteById(noteId, title, bodyText);
    }

    private void setNoteId(int noteId) {
        this.idInput.setValue(noteId);
    }
}
