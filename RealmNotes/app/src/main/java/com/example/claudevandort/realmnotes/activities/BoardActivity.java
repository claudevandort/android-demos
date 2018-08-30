package com.example.claudevandort.realmnotes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.claudevandort.realmnotes.R;
import com.example.claudevandort.realmnotes.adapters.BoardAdapter;
import com.example.claudevandort.realmnotes.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener{
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
        listViewBoard.setOnItemClickListener(this);

        fab = findViewById(R.id.fab_add_board);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoardDialog("New board", null);
            }
        });

        registerForContextMenu(listViewBoard);
    }

    // CRUD Actions
    private void createBoard(String boardName){
        Board board = new Board(boardName);
        realm.beginTransaction();
        realm.copyToRealm(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAllBoards(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName);
        realm.copyToRealmOrUpdate(board);
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

    private void editBoardDialog(String title, String message, final Board board){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_board, null);
        builder.setView(view);

        final EditText input = view.findViewById(R.id.edit_text_new_board);
        input.setText(board.getTitle());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if(boardName.length() == 0)
                    Toast.makeText(getApplicationContext(), "Board name required", Toast.LENGTH_SHORT).show();
                else if(boardName == board.getTitle())
                    Toast.makeText(getApplicationContext(), "Board name must be different", Toast.LENGTH_SHORT).show();
                else
                    editBoard(boardName, board);
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all:
                deleteAllBoards();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getTitle());
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                break;
            case R.id.edit_board:
                editBoardDialog("Edit board", "Change the board name", boards.get(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        boardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(BoardActivity.this, NoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());
        startActivity(intent);
    }
}
