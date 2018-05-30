package com.mittas.notes.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mittas.notes.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent.hasExtra(NoteListFragment.EXTRA_NOTE_ID)) {
                int itemId = intent.getIntExtra(NoteListFragment.EXTRA_NOTE_ID, -1);

                DetailFragment fragment = DetailFragment.newInstance(itemId);

                // TODO: maybe not save == null, maybe replace instead of add
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment, DetailFragment.TAG).commit();
            }
        }
    }

}
