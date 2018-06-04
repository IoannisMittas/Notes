package com.mittas.notes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mittas.notes.AppExecutors;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static final String DATABASE_NAME = "notes-database";

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        return  Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .build();
    }
}
