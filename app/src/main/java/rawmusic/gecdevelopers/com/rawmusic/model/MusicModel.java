package rawmusic.gecdevelopers.com.rawmusic.model;

public class MusicModel {


    private String data;
    private String title;
    private String album;
    private String artist;
    private boolean inPlaylist=false;

    public boolean isInPlaylist() {
        return inPlaylist;
    }

    public void setInPlaylist(boolean inPlaylist) {
        this.inPlaylist = inPlaylist;
    }

    public MusicModel(String data, String title, String album, String artist) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public MusicModel(String data, String title) {
        this.data = data;
        this.title = title;
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

