package com.example.claudio.roomfragmentnotes.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "boards")
public class Board extends BaseEntity{
    private String name;

    public Board(String name){ this.name = name; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
