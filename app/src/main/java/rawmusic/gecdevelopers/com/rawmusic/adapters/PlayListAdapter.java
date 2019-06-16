package rawmusic.gecdevelopers.com.rawmusic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
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
        View view = LayoutInflater.from(mCtx).inflate(R.layout.playlist_item, viewGroup,false);
        return new PlayListAdapter.PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayListViewHolder holder, final int position) {
        final MusicModel musicModel=list.get(position);
        holder.title.setText(musicModel.getTitle());
        Glide.with(mCtx).load(R.drawable.music).into(holder.imageView);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                //TODO add to playlist
                musicModel.setInPlaylist(false);
                MainActivity.myAppDatabase.myDao().updateSong(musicModel);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PlayListViewHolder extends RecyclerView.ViewHolder  {

        TextView title;
        Button btnRemove;
        ImageView imageView;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            btnRemove= itemView.findViewById(R.id.remove_from_playlist);
            imageView=itemView.findViewById(R.id.image_view);

        }

    }


}
