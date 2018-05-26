package com.mittas.notes.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mittas.notes.R;
import com.mittas.notes.data.Note;
import com.mittas.notes.viewmodel.CreateNoteViewModel;

import java.util.Calendar;
import java.util.Date;

public class CreateNoteFragment extends Fragment {
    public static final String TAG = "CreateNoteFragment";

    private CreateNoteViewModel viewModel;
    private EditText titleEditText;
    private EditText bodyTextEditText;

    private NoteListAdapter.OnItemClickListener itemClickListener = (view, position) -> {

        // Start DetailActivity


        //        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
//        intent.putExtra(IMAGE_POSITION, position);

        startActivity(intent);
    };

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CreateNoteViewModel.class);
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
     * // User pressed the back button, save note
     */
    private void onBackButtonPressed() {
        boolean hasTitle = !isEditTextEmpty(titleEditText);
        boolean hasBodyText = !isEditTextEmpty(bodyTextEditText);

        if (hasTitle || hasBodyText) {
            Note newNote = new Note(
                    titleEditText.getText().toString(),
                    bodyTextEditText.getText().toString(),
                    getDate()
            );

            viewModel.addNote(newNote);
        }
    }

    private Date getDate() {
        return Calendar.getInstance().getTime();
    }

    private boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }
}
