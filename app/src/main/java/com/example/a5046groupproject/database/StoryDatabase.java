package com.example.a5046groupproject.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.a5046groupproject.dao.StoryDao;
import com.example.a5046groupproject.entity.Story;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Story.class}, version = 1, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase{

    public abstract StoryDao storyDao();

    private static StoryDatabase INSTANCE;

    //we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //A synchronized method in a multi threaded environment means that two threads are not allowed to access data at the same time
    public static synchronized StoryDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StoryDatabase.class, "StoryDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
//    private static StoryDatabase instance;
//
//    public abstract StoryDao storyDao();
//
//    public static synchronized StoryDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    StoryDatabase.class, "note_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build();
//        }
//        return instance;
//    }
//
//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db){
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
//        private StoryDao storyDao;
//
//        private PopulateDbAsyncTask(StoryDatabase db){
//            storyDao = db.storyDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids){
//            storyDao.insert(new Story("Test1", "detail1", "eat", 10.0f, 123123123));
//            storyDao.insert(new Story("Test2", "detail2", "eat", 10.0f, 123123123));
//            storyDao.insert(new Story("Test3", "detail3", "eat", 10.0f, 123123123));
//            return null;
//        }
//    }

}
