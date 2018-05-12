package com.mittas.notes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mittas.notes.R;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        if (savedInstanceState == null) {
            NoteListFragment fragment = new NoteListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, NoteListFragment.TAG).commit();
        }
    }
}
