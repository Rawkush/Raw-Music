package rawmusic.gecdevelopers.com.rawmusic;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tabviewlibrary.TabView;
import com.example.tabviewlibrary.model.FragmentModel;

import java.util.ArrayList;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.fragments.ContentList;
import rawmusic.gecdevelopers.com.rawmusic.fragments.CurrentMusic;
import rawmusic.gecdevelopers.com.rawmusic.fragments.Playlist;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

public class MainActivity extends TabView {

     public static List<MusicModel> list;

    private ViewPager viewPager;
    private TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tabs);

        list= new ArrayList<>();
        fetchMusic();

        initViewpagerAndTablayout(viewPager,tabs);
        addFragment(new FragmentModel(new ContentList(),"My Music"));
        addFragment(new FragmentModel(new CurrentMusic(),"Cureently playing"));
        addFragment(new FragmentModel(new Playlist(),"Playlist"));


    }

    private  void fetchMusic(){
        list.add(new MusicModel(""+R.raw.want,"Want something"));
        list.add(new MusicModel(""+R.raw.tangled,"tangled"));
        list.add(new MusicModel(""+R.raw.treat,"treat me like"));
        list.add(new MusicModel(""+R.raw.chainsmoker,"chain smoker"));
        list.add(new MusicModel(""+R.raw.something,"something"));
    }





}
