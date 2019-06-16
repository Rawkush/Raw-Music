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

import java.util.ArrayList;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.FragmentLifecycle;
import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.adapters.ContentAdapter;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

public class ContentList extends Fragment implements FragmentLifecycle {
    List<MusicModel> list;

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
        list=new ArrayList<>();

        recyclerView= view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateList();
        adapter= new ContentAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

    }


    private void updateList(){
        List<MusicModel> tempList= MainActivity.myAppDatabase.myDao().getUser();

        Log.e("mylisdy",""+list);

        for(int i=0;i<list.size();i++){
            if(list.get(i).isInPlaylist()){
                list.remove(i);
            }
        }

        list.clear();
        list.addAll(tempList);
    }


    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

        updateList();
        adapter.notifyDataSetChanged();
    }
}
