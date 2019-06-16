package rawmusic.gecdevelopers.com.rawmusic;

import android.arch.persistence.room.Room;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.fragments.ContentList;
import rawmusic.gecdevelopers.com.rawmusic.fragments.CurrentMusic;
import rawmusic.gecdevelopers.com.rawmusic.fragments.Playlist;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

public class MainActivity extends AppCompatActivity  {

    public  List<MusicModel> list;
    private ViewPager viewPager;
    public static MyAppDatabase myAppDatabase;

    private TabLayout tabs;
    private String[] navLabels = {
"My music","Currently Playing", "Playlist"    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.myviewpager);
        tabs =  findViewById(R.id.tabs);

        list= new ArrayList<>();
        fetchMusic();

        myAppDatabase=Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb").allowMainThreadQueries().build();

        for(MusicModel m:list){
            myAppDatabase.myDao().addSong(m);
            Log.d("myl", m.getTitle() + m.isInPlaylist()) ;
        }

        init();
    }

    private void init(){

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ContentList() , "My Music");
        adapter.addFrag(new CurrentMusic(), "Play");
        adapter.addFrag(new Playlist(), "PlayList");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition=0;
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int newPosition) {
                FragmentLifecycle fragmentToShow = (FragmentLifecycle)adapter.getItem(newPosition);
                fragmentToShow.onResumeFragment();

                FragmentLifecycle fragmentToHide = (FragmentLifecycle)adapter.getItem(currentPosition);
                fragmentToHide.onPauseFragment();

                currentPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setOffscreenPageLimit(0);
        tabs.setupWithViewPager(viewPager);

    }

    private  void fetchMusic(){
        list.add(new MusicModel(""+R.raw.want,"Want something"));
        list.add(new MusicModel(""+R.raw.tangled,"tangled"));
        list.add(new MusicModel(""+R.raw.treat,"treat me like"));
        list.add(new MusicModel(""+R.raw.chainsmoker,"chain smoker"));
        list.add(new MusicModel(""+R.raw.something,"something"));
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
