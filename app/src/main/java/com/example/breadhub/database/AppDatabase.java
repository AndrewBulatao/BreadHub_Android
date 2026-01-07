package com.example.breadhub.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SandwichType.class, Sandwich.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // AppDao provides access to DAO methods
    public abstract AppDao appDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "breadhub-db" // database file name
                    )
                    // Pre-populate sandwich types on first create
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Room doesnt allow databse operations on the main UI thread
                            new Thread(() -> {
                                AppDao dao = getInstance(context).appDao();
                                dao.insertType(new SandwichType("sandwiches"));
                                dao.insertType(new SandwichType("subs/hoagies"));
                                dao.insertType(new SandwichType("bánh mì"));
                                dao.insertType(new SandwichType("panini"));
                                dao.insertType(new SandwichType("burgers"));
                            }).start();
                        }
                    })
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
