package ir.hamed.mvvm.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.hamed.mvvm.NetworkBoundResource;
import ir.hamed.mvvm.model.data.local.SongDao;
import ir.hamed.mvvm.model.data.local.model.SongEntity;
import ir.hamed.mvvm.model.data.remote.ApiService;
import ir.hamed.mvvm.model.data.remote.Resource;
import ir.hamed.mvvm.model.data.remote.model.MusicModel;
import retrofit2.Call;

public class SongRepository {

    private SongDao dao;
    private ApiService apiService;

    public SongRepository(SongDao dao, ApiService apiService) {
        this.dao = dao;
        this.apiService = apiService;
    }

    public LiveData<Resource<List<SongEntity>>> loadSong() {
        return new NetworkBoundResource<List<SongEntity>, MusicModel>() {
            @Override
            protected void saveCallResult(MusicModel item) {
                if (null != item)
                    dao.saveSong(item.getSongList());
            }

            @NonNull
            @Override
            protected LiveData<List<SongEntity>> loadFromDb() {
                return dao.loadSong();
            }

            @NonNull
            @Override
            protected Call<MusicModel> createCall() {
                return apiService.getSong();
            }
        }.getAsLiveData();
    }
}
