package com.mittas.notes.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mittas.notes.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent.hasExtra(NoteListFragment.EXTRA_NOTE_ID)) {
                String itemId = intent.getStringExtra(NoteListFragment.EXTRA_NOTE_ID);

                DetailFragment fragment = DetailFragment.newInstance(itemId);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment, DetailFragment.TAG).commit();
            }
        }
    }

}
