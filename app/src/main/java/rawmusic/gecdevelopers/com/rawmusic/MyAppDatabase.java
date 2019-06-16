package rawmusic.gecdevelopers.com.rawmusic;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.google.android.exoplayer2.upstream.DataSpec;

import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;


@Database(entities = {MusicModel.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
