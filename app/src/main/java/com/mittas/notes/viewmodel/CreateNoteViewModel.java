package com.mittas.notes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.mittas.notes.BasicApp;
import com.mittas.notes.NoteRepository;
import com.mittas.notes.data.Note;

public class CreateNoteViewModel extends AndroidViewModel{
    private NoteRepository noteRepository;

    public CreateNoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = ((BasicApp) application).getRepository();
    }

    public void addNote(Note note) {
        noteRepository.addNote(note);
    }
}
