package com.mittas.notes.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pinned_notes",
        foreignKeys = @ForeignKey(entity = Note.class,
                parentColumns = "id",
                childColumns = "noteId",
                onDelete = CASCADE))
public class PinnedNote {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int noteId;

    public PinnedNote() {
    }

    public PinnedNote(int noteId) {
        this.noteId = noteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
