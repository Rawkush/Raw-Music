package rawmusic.gecdevelopers.com.rawmusic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rawmusic.gecdevelopers.com.rawmusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentMusic extends Fragment {


    public CurrentMusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_music, container, false);
    }

}
