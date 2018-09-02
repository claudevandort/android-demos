package com.example.claudio.roomfragmentnotes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.claudio.roomfragmentnotes.entities.Board;
import com.example.claudio.roomfragmentnotes.repository.NotesRepository;

import java.util.List;

public class BoardViewModel extends AndroidViewModel {
    private NotesRepository mRepository;
    private LiveData<List<Board>> mAllBoards;

    public BoardViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NotesRepository(application);
        mAllBoards = mRepository.getAllBoards();
    }

    public LiveData<List<Board>> getAllBoards() { return mAllBoards; }
    public void insert(Board board){ mRepository.insert(board); }
}
