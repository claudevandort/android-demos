package com.example.claudio.roomfragmentnotes.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.claudio.roomfragmentnotes.entities.Board;

import java.util.List;

@Dao
public interface BoardDao {
    @Insert
    void insert(Board board);

    @Query("DELETE FROM boards")
    void deleteAll();

    @Query("SELECT * FROM boards ORDER BY id ASC")
    LiveData<List<Board>> getAll();
}
