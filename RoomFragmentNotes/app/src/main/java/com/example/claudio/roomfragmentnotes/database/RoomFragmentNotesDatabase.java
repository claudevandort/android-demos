package com.example.claudio.roomfragmentnotes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.claudio.roomfragmentnotes.daos.BoardDao;
import com.example.claudio.roomfragmentnotes.entities.Board;

@Database(entities = {Board.class}, version = 1, exportSchema = false)
public abstract class RoomFragmentNotesDatabase extends RoomDatabase {
    public abstract BoardDao boardDao();

    private static RoomFragmentNotesDatabase INSTANCE;
    public static RoomFragmentNotesDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (RoomFragmentNotesDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomFragmentNotesDatabase.class,
                            "room_fragment_notes_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
