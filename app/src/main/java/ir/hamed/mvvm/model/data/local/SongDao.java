package ir.hamed.mvvm.model.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ir.hamed.mvvm.model.data.local.model.SongEntity;

@Dao
public interface SongDao {

    @Query("SELECT * FROM tbl_song")
    LiveData<List<SongEntity>> loadSong();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveSong(List<SongEntity> articleEntities);

}
