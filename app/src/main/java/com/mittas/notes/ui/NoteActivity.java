package com.mittas.notes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mittas.notes.R;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (savedInstanceState == null) {
            NoteFragment fragment = new NoteFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, NoteFragment.TAG).commit();
        }
    }
}
