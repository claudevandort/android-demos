package com.example.claudevandort.realmnotes.activities;

import android.content.DialogInterface;
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
import com.example.claudevandort.realmnotes.adapters.NoteAdapter;
import com.example.claudevandort.realmnotes.models.Board;
import com.example.claudevandort.realmnotes.models.Note;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {
    private ListView listViewNotes;
    private FloatingActionButton fab;

    private NoteAdapter noteAdapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null)
            boardId = getIntent().getIntExtra("id", -1);
        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        setTitle("Board " + board.getTitle());
        notes = board.getNotes();
        board.addChangeListener(this);

        fab = findViewById(R.id.fab_add_note);
        listViewNotes = findViewById(R.id.list_view_note);
        noteAdapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);
        listViewNotes.setAdapter(noteAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNoteDialog("New note in " + board.getTitle(), null);
            }
        });

        registerForContextMenu(listViewNotes);
    }

    // CRUD Actions
    private void createNote(String description){
        Note note = new Note(description);
        realm.beginTransaction();
        realm.copyToRealm(note);
        board.getNotes().add(note);
        realm.commitTransaction();
    }

    private void updateNote(String description, Note note){
        realm.beginTransaction();
        note.setDescription(description);
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    private void deleteNote(Note note){
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAllNotes(){
        realm.beginTransaction();
        board.getNotes().deleteAllFromRealm();
        realm.commitTransaction();
    }

    // Dialogs
    private void newNoteDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_note, null);
        builder.setView(view);

        final EditText input = view.findViewById(R.id.edit_text_new_note);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteDescription = input.getText().toString().trim();
                if(noteDescription.length() > 0){
                    createNote(noteDescription);
                    Toast.makeText(getApplicationContext(), noteDescription + " note created", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Note can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    private void editNoteDialog(String title, String message, final Note note){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_note, null);
        builder.setView(view);

        final EditText input = view.findViewById(R.id.edit_text_new_note);
        input.setText(note.getDescription());
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteDescription = input.getText().toString().trim();
                if(noteDescription.length() == 0)
                    Toast.makeText(getApplicationContext(), "Note can't be empty", Toast.LENGTH_SHORT).show();
                else if (noteDescription == note.getDescription())
                    Toast.makeText(getApplicationContext(), "Note description must be different", Toast.LENGTH_SHORT).show();
                else
                    updateNote(noteDescription, note);
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
                deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete_board:
                deleteNote(notes.get(info.position));
                break;
            case R.id.edit_board:
                editNoteDialog("Edit note", "Change the note description", notes.get(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onChange(Board board) {
        noteAdapter.notifyDataSetChanged();
    }
}
