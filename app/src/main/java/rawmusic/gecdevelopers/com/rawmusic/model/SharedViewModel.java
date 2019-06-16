package rawmusic.gecdevelopers.com.rawmusic.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {


    private MutableLiveData<List<MusicModel>> songList= new MutableLiveData<>();

    public void setSongList(MutableLiveData<List<MusicModel>> songList) {
        this.songList = songList;
    }

    public LiveData<List<MusicModel>> getSongList(){
        return songList;
    }

}
