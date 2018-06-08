package com.mittas.notes.util;

import android.content.Intent;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.mittas.notes.data.RemoteDatabase;

import static android.app.Activity.RESULT_OK;

public class FirebaseHelper {
    public static void handleSignInWithFirebaseUI(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK) {
            // Successfully signed in
            RemoteDatabase.onUserSignedIn();
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    public static boolean isSignedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }
}
