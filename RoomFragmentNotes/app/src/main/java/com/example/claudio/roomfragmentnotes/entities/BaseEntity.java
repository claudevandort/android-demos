package com.example.claudio.roomfragmentnotes.entities;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    protected long id;

    @NonNull
    public long getId() { return id; }
    public void setId(@NonNull long id) { this.id = id; }
}
