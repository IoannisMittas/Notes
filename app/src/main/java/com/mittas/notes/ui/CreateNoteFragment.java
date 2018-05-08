package com.mittas.notes.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mittas.notes.R;


public class CreateNoteFragment extends Fragment {
    public static final String TAG = "CreateNoteFragment";

    private EditText titleEditText;
    private EditText bodyTextEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_note, container, false);

        setHasOptionsMenu(true);

        titleEditText = rootView.findViewById(R.id.text_input_title);
        bodyTextEditText = rootView.findViewById(R.id.text_input_body_text);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO handle back click
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
