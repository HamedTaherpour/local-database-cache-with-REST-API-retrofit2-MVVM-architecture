package ir.hamed.mvvm.model.data.remote;

import ir.hamed.mvvm.model.data.remote.model.MusicModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Admin on 18/11/2018.
 */

public interface ApiService {

    @GET("mobile/PlayList/Get/1/20/0")
    Call<MusicModel> getSong();
}
