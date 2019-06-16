package rawmusic.gecdevelopers.com.rawmusic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

public class PlayListAdapter extends  RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {


    private Context mCtx;
    List<MusicModel> list= Collections.EMPTY_LIST;

    public PlayListAdapter(Context mCtx, List<MusicModel> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.content_item, viewGroup,false);
        return new PlayListAdapter.PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder playListViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PlayListViewHolder extends RecyclerView.ViewHolder  {

        TextView title;

        Button btnAddToPlaylist;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            btnAddToPlaylist= itemView.findViewById(R.id.add_to_playlist);

        }

    }


}
