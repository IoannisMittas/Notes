package com.mittas.notes.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mittas.notes.R;
import com.mittas.notes.ui.list.NoteListFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent.hasExtra(NoteListFragment.EXTRA_NOTE_ID)) {
                int itemId = intent.getIntExtra(NoteListFragment.EXTRA_NOTE_ID, -1);

                DetailFragment fragment = DetailFragment.newInstance(itemId);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment, DetailFragment.TAG).commit();
            }
        }
    }

}
