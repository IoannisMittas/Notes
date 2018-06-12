package com.mittas.notes.ui.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.mittas.notes.R;
import com.mittas.notes.data.Note;
import com.mittas.notes.ui.create.CreateNoteActivity;
import com.mittas.notes.ui.detail.DetailActivity;
import com.mittas.notes.ui.list.gestures.SimpleItemTouchHelperCallback;
import com.mittas.notes.util.FirebaseHelper;
import com.mittas.notes.viewmodel.NoteListViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteListFragment extends Fragment {
    public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";
    public static final String TAG = "NOTELIST_FRAG";

    private static final  int RC_SIGN_IN = 100;

    private NoteListViewModel viewModel;
    private NoteListAdapter adapter;
    private RecyclerView recyclerView;

    private NoteListAdapter.OnItemClickListener itemClickListener = (view, noteId) -> {
        // Start DetailActivity
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        startActivity(intent);
    };

    private NoteListAdapter.OnNoteSwipeCallback noteSwipeCallback = (note, direction) -> {
        viewModel.deleteNote(note);
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notelist, container, false);

        setHasOptionsMenu(true);

        setupRecyclerView(rootView);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), CreateNoteActivity.class)));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel.class);

        subscribeUi();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!FirebaseHelper.isSignedIn()) {
            signInWithFirebaseUI();
        }
    }

    private void signInWithFirebaseUI() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            FirebaseHelper.handleSignInWithFirebaseUI(resultCode, data);
            if(FirebaseHelper.isSignedIn()) {
                viewModel.syncNotes();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                FirebaseHelper.handleSignOutWithFirebaseUI(getActivity());
                signInWithFirebaseUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new NoteListAdapter(new ArrayList<Note>(), itemClickListener, noteSwipeCallback);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void subscribeUi() {
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }
}
