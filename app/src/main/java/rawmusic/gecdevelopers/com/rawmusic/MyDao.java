package rawmusic.gecdevelopers.com.rawmusic;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

@Dao
public interface MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public  void  addSong(MusicModel musicModel);

    @Query("select * from user")
    public List<MusicModel> getUser();


    @Update
    public void updateSong(MusicModel musicModel);

}
