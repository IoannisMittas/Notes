package com.mittas.notes.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoteDatabase {
    private static RemoteDatabase INSTANCE;

    private static DatabaseReference rootDefault;
    private static DatabaseReference root;

    public interface syncRequestListener {
        void onSuccess(List<Note> notes);

        void onFailure(String errorMessage);
    }

    private RemoteDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);

        rootDefault = FirebaseDatabase.getInstance().getReference();

        root = rootDefault;
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
        // Using room and firebase Db, the id in firebase was being saved as
        // id = 0. Have to manually set id to get it right.
        note.setId((int) noteId);

        root.child("notes").child(Long.toString(noteId)).setValue(note);
    }

    public void updateNote(int noteId, String title, String bodyText) {
        HashMap<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/" + noteId + "/title", title);
        childUpdates.put("/notes/" + noteId + "/bodyText", bodyText);
        root.updateChildren(childUpdates);
    }

    public void deleteNote(int noteId) {
        root.child("notes").child(Integer.toString(noteId)).removeValue();
    }

    public void addOnSyncRequestListener(RemoteDatabase.syncRequestListener listener) {
        root.child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Note> allNotes = new ArrayList<>();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    allNotes.add(note);
                }

                listener.onSuccess(allNotes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String errorMessage = "Couldn't sync notes";
                Log.w("RemoteDatabase", errorMessage, databaseError.toException());
                listener.onFailure(errorMessage);
            }
        });
    }

    public static void onUserSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String ui = user.getUid();
            root = rootDefault.child("users").child(ui);
        }
    }

    public static void onUserSignedOut() {
        root = rootDefault;
    }
}

