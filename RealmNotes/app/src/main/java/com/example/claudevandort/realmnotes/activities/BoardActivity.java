package com.example.claudevandort.realmnotes.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.claudevandort.realmnotes.R;

public class BoardActivity extends AppCompatActivity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        fab = findViewById(R.id.fab_add_board);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoardDialog("New board", null);
            }
        });
    }

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
                    Toast.makeText(getApplicationContext(), "Create board!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Board name required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }
}