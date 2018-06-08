package com.mittas.notes.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoteDatabase {
   private static RemoteDatabase INSTANCE;
   private DatabaseReference database;

   private RemoteDatabase(){
       FirebaseDatabase.getInstance().setPersistenceEnabled(false);

       database = FirebaseDatabase.getInstance().getReference();
   }

    public static RemoteDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDatabase();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

   public void addNote(Note note, long noteId) {
       database.child("notes").child(Long.toString(noteId)).setValue(note);
   }


    public void onUserSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }
}

