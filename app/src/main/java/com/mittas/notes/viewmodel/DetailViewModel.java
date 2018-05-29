package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.mittas.notes.BasicApp;
import com.mittas.notes.data.Note;

public class DetailViewModel extends AndroidViewModel {
    private final LiveData<Note> observableNote;
    private final MutableLiveData<Integer> idInput;


    public DetailViewModel(@NonNull Application application) {
        super(application);

        idInput = new MutableLiveData<>();

        observableNote = Transformations.switchMap(idInput, id ->
             ((BasicApp) application).getRepository().getNoteById(id));
    }

    public LiveData<Note> getNoteById(int noteId) {
        setNoteId(noteId);
        return observableNote;
    }

    private void setNoteId(int noteId) {
        this.idInput.setValue(noteId);
    }


}
