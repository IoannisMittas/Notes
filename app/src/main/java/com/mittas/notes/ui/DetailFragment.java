package com.mittas.notes.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mittas.notes.R;
import com.mittas.notes.data.Note;
import com.mittas.notes.viewmodel.DetailViewModel;
import com.mittas.notes.viewmodel.NoteListViewModel;

import java.util.List;

public class DetailFragment extends Fragment {
    public static final String TAG = "DETAIL_FRAGMENT";
    private static final String ARG_NOTE_ID = "NOTE_ID";
    private int noteId;

    private TextView titleTextView;
    private TextView bodyTextView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.noteId = args.getInt(ARG_NOTE_ID, -1);
    }

    public static DetailFragment newInstance(int noteId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTE_ID, noteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = rootView.findViewById(R.id.text_view_title);
        bodyTextView = rootView.findViewById(R.id.text_view_body_text);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DetailViewModel viewModel =
                ViewModelProviders.of(this).get(DetailViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(DetailViewModel viewModel) {
        viewModel.getNoteById(noteId).observe(this, note -> {
            if(note != null) {
                titleTextView.setText(note.getTitle());
                bodyTextView.setText(note.getBodyText());
            }
        });
    }

}
