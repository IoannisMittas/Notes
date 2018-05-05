package com.mittas.notes.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mittas.notes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {
    public static final String TAG = "NoteListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

}
