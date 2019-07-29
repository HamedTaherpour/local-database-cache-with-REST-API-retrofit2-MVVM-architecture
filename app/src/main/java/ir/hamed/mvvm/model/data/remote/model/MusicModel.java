
package ir.hamed.mvvm.model.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.hamed.mvvm.model.data.local.model.SongEntity;


public class MusicModel {

    @SerializedName("song_list")
    private List<SongEntity> mSongList;

    public List<SongEntity> getSongList() {
        return mSongList;
    }

    public void setSongList(List<SongEntity> songList) {
        mSongList = songList;
    }
}
