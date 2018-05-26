package com.mittas.notes.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;

import com.mittas.notes.R;
import com.mittas.notes.data.Note;
import com.mittas.notes.viewmodel.NoteListViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment {
    public static final String TAG = "NoteListFragment";
    private NoteListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notelist, container, false);

        setupRecyclerView(rootView);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateNoteActivity.class));
            }
        });

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NoteListViewModel viewModel =
                ViewModelProviders.of(this).get(NoteListViewModel.class);

        subscribeUi(viewModel);
    }

    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new NoteListAdapter(new ArrayList<Note>());
        recyclerView.setAdapter(adapter);
    }


    private void subscribeUi(NoteListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
               adapter.setNotes(notes);
            }
        });
    }
}
