package com.mittas.notes.data;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbHolder{
   private static FirebaseDbHolder INSTANCE;
   private DatabaseReference database;
   private

   private FirebaseDbHolder(){
       FirebaseDatabase.getInstance().setPersistenceEnabled(false);

       database = FirebaseDatabase.getInstance().getReference();
   }

    public static FirebaseDbHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseDbHolder();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public void onUserSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }
}

