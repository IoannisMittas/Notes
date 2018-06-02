package com.mittas.notes.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mittas.notes.R;
import com.mittas.notes.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {
    public static final String TAG = "DETAIL_FRAGMENT";
    private static final String ARG_NOTE_ID = "NOTE_ID";
    private int noteId;

    private EditText titleEditText;
    private EditText bodyTextEditText;

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

        setViews(rootView);


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
            if (note != null) {
                titleEditText.setText(note.getTitle());
                bodyTextEditText.setText(note.getBodyText());
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViews(View rootView) {
        titleEditText = rootView.findViewById(R.id.edit_text_title);
        titleEditText.setOnTouchListener((v, event) -> {
            titleEditText.setCursorVisible(true);
            return false;
        });

        bodyTextEditText = rootView.findViewById(R.id.edit_text_body_text);
        bodyTextEditText.setOnTouchListener((v, event) -> {
            bodyTextEditText.setCursorVisible(true);
            return false;
        });
    }

}
