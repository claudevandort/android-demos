package com.example.claudio.roomfragmentnotes.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.claudio.roomfragmentnotes.daos.BoardDao;
import com.example.claudio.roomfragmentnotes.database.RoomFragmentNotesDatabase;
import com.example.claudio.roomfragmentnotes.entities.Board;

import java.util.List;

public class NotesRepository {
    private BoardDao mBoardDao;
    private LiveData<List<Board>> mAllBoards;

    public NotesRepository(Application application){
        RoomFragmentNotesDatabase db = RoomFragmentNotesDatabase.getDatabase(application);
        mBoardDao = db.boardDao();
        mAllBoards = mBoardDao.getAll();
    }

    public LiveData<List<Board>> getAllBoards() { return mAllBoards; }
    public void insert(Board board){ new InsertAsyncTask(mBoardDao).execute(board); }

    private static class InsertAsyncTask extends AsyncTask<Board, Void, Void>{
        private BoardDao mAsyncTaskDao;

        public InsertAsyncTask(BoardDao dao){ mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Board... boards) {
            mAsyncTaskDao.insert(boards[0]);
            return null;
        }
    }
}
