package com.mittas.notes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mittas.notes.AppExecutors;

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static final String DATABASE_NAME = "notes-database";

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext(), executors);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        // TODO needs executors here?----------------------------------------------------------------------------------
        return  Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .build();
    }





}
