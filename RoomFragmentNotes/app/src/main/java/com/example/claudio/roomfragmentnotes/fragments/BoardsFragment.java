package com.example.claudio.roomfragmentnotes.fragments;


import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.claudio.roomfragmentnotes.R;
import com.example.claudio.roomfragmentnotes.adapters.BoardsAdapter;
import com.example.claudio.roomfragmentnotes.entities.Board;
import com.example.claudio.roomfragmentnotes.viewmodel.BoardViewModel;

import java.util.List;

public class BoardsFragment extends Fragment {
    private FloatingActionButton newBoardFab;
    private RecyclerView boardsRecyclerView;
    private BoardsAdapter boardsAdapter;
    private BoardViewModel boardViewModel;

    public BoardsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boardsAdapter = new BoardsAdapter(getContext());
        boardViewModel = ViewModelProviders.of(this).get(BoardViewModel.class);
        boardViewModel.getAllBoards().observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(@Nullable List<Board> boards) {
                boardsAdapter.setBoards(boards);
            }
        });

        View view = inflater.inflate(R.layout.fragment_boards, container, false);
        boardsRecyclerView = view.findViewById(R.id.board_recycler_view);
        boardsRecyclerView.setAdapter(boardsAdapter);
        boardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newBoardFab = view.findViewById(R.id.new_board_fab);
        newBoardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoardDialog();
            }
        });

        return view;
    }

    private void newBoardDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_board, null);
        builder.setTitle("New Board");
        builder.setView(view);

        final EditText newBoardNameEditText = view.findViewById(R.id.dialog_board_name_edit_text);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(newBoardNameEditText != null && !newBoardNameEditText.getText().toString().isEmpty()){
                    String name = newBoardNameEditText.getText().toString();
                    Board board = new Board(name);
                    boardViewModel.insert(board);
                }
                else
                    Toast.makeText(getContext(), "Board name missing", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
}
