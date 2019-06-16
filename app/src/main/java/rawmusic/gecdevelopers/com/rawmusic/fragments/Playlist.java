package rawmusic.gecdevelopers.com.rawmusic.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.FragmentLifecycle;
import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.adapters.ContentAdapter;
import rawmusic.gecdevelopers.com.rawmusic.adapters.PlayListAdapter;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Playlist extends Fragment implements FragmentLifecycle {


    PlayListAdapter adapter;
    RecyclerView recyclerView;

    public Playlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MusicModel> list= MainActivity.myAppDatabase.myDao().getUser();

        for(int i=0;i<list.size();i++){
            Log.d("myl play", list.get(i).getTitle() + list.get(i).isInPlaylist()) ;

            if(!list.get(i).isInPlaylist()){
                list.remove(i);
                i--;
            }
        }

        adapter= new PlayListAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
