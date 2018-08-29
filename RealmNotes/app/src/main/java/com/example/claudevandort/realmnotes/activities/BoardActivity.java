package com.example.claudevandort.realmnotes.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.claudevandort.realmnotes.R;
import com.example.claudevandort.realmnotes.adapters.BoardAdapter;
import com.example.claudevandort.realmnotes.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>{
    private Realm realm;
    private FloatingActionButton fab;
    private ListView listViewBoard;
    private BoardAdapter boardAdapter;
    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);

        boardAdapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listViewBoard = findViewById(R.id.list_view_board);
        listViewBoard.setAdapter(boardAdapter);

        fab = findViewById(R.id.fab_add_board);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoardDialog("New board", null);
            }
        });
    }

    private void createBoard(String boardName){
        Board board = new Board(boardName);
        realm.beginTransaction();
        realm.copyToRealm(board);
        realm.commitTransaction();
    }

    // Dialogs
    private void newBoardDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_board, null);
        builder.setView(view);

        final EditText input = view.findViewById(R.id.edit_text_new_board);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if(boardName.length() > 0){
                    createBoard(boardName);
                    Toast.makeText(getApplicationContext(), boardName + " board created", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Board name required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        boardAdapter.notifyDataSetChanged();
    }
}
