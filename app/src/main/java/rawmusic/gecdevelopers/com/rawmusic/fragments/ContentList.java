package rawmusic.gecdevelopers.com.rawmusic.fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.adapters.ContentAdapter;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;
import rawmusic.gecdevelopers.com.rawmusic.model.SharedViewModel;

public class ContentList extends Fragment {


    ContentAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MusicModel> list= MainActivity.myAppDatabase.myDao().getUser();

        for(int i=0;i<list.size();i++){
            if(list.get(i).isInPlaylist()){
                list.remove(i);
            }
        }

        adapter= new ContentAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

    }



}
