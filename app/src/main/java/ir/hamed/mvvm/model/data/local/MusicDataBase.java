package ir.hamed.mvvm.model.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ir.hamed.mvvm.model.data.local.model.SongEntity;

@Database(entities = {SongEntity.class}, version = 1)
public abstract class MusicDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "recipes_db";

    private static MusicDataBase instance;

    public static MusicDataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MusicDataBase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract SongDao songDao();
}
