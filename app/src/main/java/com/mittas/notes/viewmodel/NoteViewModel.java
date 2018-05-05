package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.mittas.notes.BasicApp;
import com.mittas.notes.data.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel{

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<NoteEntity>> observableNotes;

    public NoteViewModel(Application application) {
        super(application);

        observableNotes = new MediatorLiveData<>();

        // TODO remove it???--------------------------------------------------------------------------------
        // set by default null, until we get data from the database.
        // mObservableProducts.setValue(null);

        LiveData<List<NoteEntity>> notes = ((BasicApp) application).getRepository()
                .getAllNotes();

        // observe the changes of the products from the database and forward them
        observableNotes.addSource(notes, observableNotes::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<NoteEntity>> getNotes() {
        return observableNotes;
    }




}
