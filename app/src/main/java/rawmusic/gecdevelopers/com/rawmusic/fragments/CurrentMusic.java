package rawmusic.gecdevelopers.com.rawmusic.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentMusic extends Fragment {

    private SimpleExoPlayer exoPlayer;
    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.i(TAG,"onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(TAG,"onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG,"onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(TAG,"onPlayerStateChanged: playWhenReady = "+String.valueOf(playWhenReady)
                    +" playbackState = "+playbackState);
            switch (playbackState){
                case ExoPlayer.STATE_ENDED:
                    Log.i(TAG,"Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
                    exoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
                    Log.i(TAG,"ExoPlayer ready! pos: "+exoPlayer.getCurrentPosition()
                            +" max: "+stringForTime((int)exoPlayer.getDuration()));
                    setProgress();
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    Log.i(TAG,"Playback buffering!");
                    break;
                case ExoPlayer.STATE_IDLE:
                    Log.i(TAG,"ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG,"onPlaybackError: "+error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.i(TAG,"onPositionDiscontinuity");
        }
    };
    private SeekBar seekPlayerProgress;
    private Handler handler;
    private ImageButton btnPlay;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private TextView txtCurrentTime, txtEndTime;
    private boolean isPlaying = false;
    private static final String TAG = "MainActivity";
    static  int song=0;
    List<MusicModel> list= new ArrayList<>();
    View root;

    public CurrentMusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_current_music, container, false);

        root=v;
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchlist();

        //TODO: load previously played
        if(list.isEmpty())
            prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(R.raw.chainsmoker));
        else
            prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(Integer.parseInt(list.get(0).getData())));

    }



    private void fetchlist(){
        list= MainActivity.myAppDatabase.myDao().getUser();

        for(int i=0;i<list.size();i++){
            if(!list.get(i).isInPlaylist()){
                list.remove(i);
            }
        }
    }



    private void prepareExoPlayerFromRawResourceUri(Uri uri){
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(null), new DefaultLoadControl());
        exoPlayer.addListener(eventListener);

        DataSpec dataSpec = new DataSpec(uri);
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getContext());
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return rawResourceDataSource;
            }
        };

        MediaSource audioSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
                factory, new DefaultExtractorsFactory(), null, null);


        exoPlayer.prepare(audioSource);
        initMediaControls();
    }






    private void initMediaControls() {
        initPlayButton();
        initSeekBar();
        initTxtTime();
        initNextButton();
        initPrevButton();
    }



    private void initPrevButton(){
        btnPrev=root.findViewById(R.id.btnPrev);
        btnPrev.requestFocus();
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exoPlayer.release();
                song--;
                if(song<=0) {
                    if (list.isEmpty()) {
                        setPlayPause(false);
                        prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(R.raw.chainsmoker));
                        setPlayPause(true);

                        return;
                    }
                    song = list.size() - 1;
                }
                setPlayPause(false);

                prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(Integer.parseInt(list.get(song).getData())));
                    setPlayPause(true);

            }
        });
    }

    private void initNextButton(){
        btnNext= (ImageButton) root.findViewById(R.id.btnNext);
        btnNext.requestFocus();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                song++;
                if(song>=list.size())
                    song=0;

                if (list.isEmpty()) {
                    setPlayPause(false);
                    prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(R.raw.chainsmoker));
                    setPlayPause(true);

                    return;
                }
                setPlayPause(false);
                prepareExoPlayerFromRawResourceUri(RawResourceDataSource.buildRawResourceUri(Integer.parseInt(list.get(song).getData())));
                setPlayPause(true);
            }
        });
    }


    private void initPlayButton() {
        btnPlay = (ImageButton) root.findViewById(R.id.btnPlay);
        btnPlay.requestFocus();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayPause(!isPlaying);
            }
        });
    }

    /**
     * Starts or stops playback. Also takes care of the Play/Pause button toggling
     * @param play True if playback should be started
     */
    private void setPlayPause(boolean play){
        isPlaying = play;
        exoPlayer.setPlayWhenReady(play);
        if(!isPlaying){
            btnPlay.setImageResource(android.R.drawable.ic_media_play);
        }else{
            setProgress();
            btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    private void initTxtTime() {
        txtCurrentTime = (TextView) root.findViewById(R.id.time_current);
        txtEndTime = (TextView) root.findViewById(R.id.player_end_time);
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds =  timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private void setProgress() {
        seekPlayerProgress.setProgress(0);
        seekPlayerProgress.setMax((int) exoPlayer.getDuration()/1000);
        txtCurrentTime.setText(stringForTime((int)exoPlayer.getCurrentPosition()));
        txtEndTime.setText(stringForTime((int)exoPlayer.getDuration()));

        if(handler == null)handler = new Handler();
        //Make sure you update Seekbar on UI thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && isPlaying) {
                    seekPlayerProgress.setMax((int) exoPlayer.getDuration()/1000);
                    int mCurrentPosition = (int) exoPlayer.getCurrentPosition() / 1000;
                    seekPlayerProgress.setProgress(mCurrentPosition);
                    txtCurrentTime.setText(stringForTime((int)exoPlayer.getCurrentPosition()));
                    txtEndTime.setText(stringForTime((int)exoPlayer.getDuration()));

                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void initSeekBar() {
        seekPlayerProgress = (SeekBar) root.findViewById(R.id.mediacontroller_progress);
        seekPlayerProgress.requestFocus();

        seekPlayerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }

                exoPlayer.seekTo(progress*1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekPlayerProgress.setMax(0);
        seekPlayerProgress.setMax((int) exoPlayer.getDuration()/1000);

    }



}
