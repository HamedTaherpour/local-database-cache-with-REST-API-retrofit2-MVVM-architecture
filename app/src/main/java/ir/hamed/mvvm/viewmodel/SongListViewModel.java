package ir.hamed.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.hamed.mvvm.model.SongRepository;
import ir.hamed.mvvm.model.data.local.MusicDataBase;
import ir.hamed.mvvm.model.data.local.model.SongEntity;
import ir.hamed.mvvm.model.data.remote.ApiClient;
import ir.hamed.mvvm.model.data.remote.ApiService;
import ir.hamed.mvvm.model.data.remote.Resource;

public class SongListViewModel extends AndroidViewModel {

    private LiveData<Resource<List<SongEntity>>> songData;
    private SongRepository repository;

    public SongListViewModel(@NonNull Application application) {
        super(application);
        repository = new SongRepository(MusicDataBase.getInstance(application.getApplicationContext()).songDao(), ApiClient.get().create(ApiService.class));
        songData = repository.loadSong();
    }

    public LiveData<Resource<List<SongEntity>>> getSongData() {
        return songData;
    }
}
