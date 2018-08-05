package com.example.sirisha.moviecopy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sirisha on 17-05-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterHOlder> {
    ArrayList<MoviePOJO> videosList;
    Context context;
    public MovieAdapter(Context context, ArrayList<MoviePOJO> moviePOJOS) {
        super();
        this.context=context;
        this.videosList=moviePOJOS;

    }

    @Override
    public AdapterHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.vedios_list,parent,false);
        return new AdapterHOlder(v);
    }

    @Override
    public void onBindViewHolder(AdapterHOlder holder, int position) {
        holder.tv.setText(videosList.get(position).getKey());

    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }



    public class AdapterHOlder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        public AdapterHOlder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.Videos_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+""+videosList.get(getAdapterPosition()).getKey())));
        }
    }
}
