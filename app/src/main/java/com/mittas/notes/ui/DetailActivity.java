package com.mittas.notes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mittas.notes.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, DetailFragment.TAG).commit();
        }
    }
}
