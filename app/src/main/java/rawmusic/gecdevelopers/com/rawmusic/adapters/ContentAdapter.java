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

import rawmusic.gecdevelopers.com.rawmusic.MainActivity;
import rawmusic.gecdevelopers.com.rawmusic.R;
import rawmusic.gecdevelopers.com.rawmusic.model.MusicModel;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context mCtx;

    List<MusicModel> list= Collections.EMPTY_LIST;

    public ContentAdapter(Context mCtx, List<MusicModel> list) {
        this.mCtx = mCtx;
        this.list=list;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.content_item, parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder holder, final int position) {


        final MusicModel musicModel=list.get(position);
        holder.title.setText(musicModel.getTitle());

        holder.btnAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                //TODO add to playlist

                for(MusicModel m: MainActivity.list){
                    if(m.getTitle().equals(musicModel.getTitle())){
                        m.setInPlaylist(true);
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder  {

        TextView title;

        Button btnAddToPlaylist;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            btnAddToPlaylist= itemView.findViewById(R.id.add_to_playlist);

        }

    }

}
