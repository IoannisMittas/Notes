package com.mittas.notes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mittas.notes.R;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            CreateNoteFragment fragment = new CreateNoteFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CreateNoteFragment.TAG).commit();
        }
    }
}
