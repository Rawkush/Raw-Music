package rawmusic.gecdevelopers.com.rawmusic.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity (tableName = "user")
public class MusicModel {

    @ColumnInfo(name = "data")
    private String data;

    @NonNull
    @PrimaryKey
    private String title;

    private String album;
    private String artist;

    @ColumnInfo(name = "isInPlayList")
    private boolean inPlaylist;

    public boolean isInPlaylist() {
        return inPlaylist;
    }

    public void setInPlaylist(boolean inPlaylist) {
        this.inPlaylist = inPlaylist;
    }


    public MusicModel(String data, String title) {
        this.data = data;
        this.title = title;
        inPlaylist=false;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}

