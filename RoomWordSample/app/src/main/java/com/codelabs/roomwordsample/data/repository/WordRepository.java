package com.codelabs.roomwordsample.data.repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.codelabs.roomwordsample.data.WordRoomDatabase;
import com.codelabs.roomwordsample.data.dao.WordDao;
import com.codelabs.roomwordsample.data.table.Word;

import java.util.List;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert (Word word) {
        new insertAsyncTask(wordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao asyncTaskDao;

        insertAsyncTask(WordDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            asyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
