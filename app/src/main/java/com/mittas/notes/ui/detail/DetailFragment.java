package com.mittas.notes.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mittas.notes.R;
import com.mittas.notes.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {
    public static final String TAG = "DETAIL_FRAGMENT";
    private static final String ARG_NOTE_ID = "NOTE_ID";
    private int noteId;

    private DetailViewModel viewModel;
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

        setHasOptionsMenu(true);

        setViews(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        subscribeUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackButtonPressed();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * // User pressed the back button, update note
     */
    private void onBackButtonPressed() {
        String editedTitle = titleEditText.getText().toString();
        String editedBodyText = bodyTextEditText.getText().toString();
        viewModel.updateNoteById(noteId, editedTitle, editedBodyText);
    }

    private void subscribeUi() {
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
