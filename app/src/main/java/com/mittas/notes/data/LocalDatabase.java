package com.mittas.notes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mittas.notes.data.dao.NoteDao;
import com.mittas.notes.data.entity.Note;

@Database(entities = {Note.class, PinnedNote.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;

    public static final String DATABASE_NAME = "notes_local_database";

    public abstract NoteDao noteDao();

    public abstract PinnedNoteDao pinnedNoteDao();

    public static LocalDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static LocalDatabase buildDatabase(final Context appContext) {
        return  Room.databaseBuilder(appContext, LocalDatabase.class, DATABASE_NAME)
                .build();
    }
}
