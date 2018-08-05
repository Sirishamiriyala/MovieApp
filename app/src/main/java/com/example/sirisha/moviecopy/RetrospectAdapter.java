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

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by sirisha on 18-05-2018.
 */

public class RetrospectAdapter extends RecyclerView.Adapter<RetrospectAdapter.AdapterHolder>{

    Context context;

    ArrayList<RetrospectPOJO>ReviewList;

    public RetrospectAdapter(Context context, ArrayList<RetrospectPOJO> retrospectPOJOS) {
        this.context=context;
        this.ReviewList=retrospectPOJOS;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.review_list,parent,false);
        return new AdapterHolder(v);

    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {

        RetrospectPOJO p=ReviewList.get(position);

        holder.writer.setText(p.getReview_writer());

       // holder.url.setText(p.getReview_url());

        holder.des.setText(p.getReview_des());

        //holder.id.setText(p.getReview_id());

    }

    @Override
    public int getItemCount() {
        return ReviewList.size();
    }

    public class AdapterHolder extends ViewHolder implements View.OnClickListener{
        TextView writer,des,id,url;

        public AdapterHolder(View itemView) {
            super(itemView);
            writer=itemView.findViewById(R.id.writer);
            des=itemView.findViewById(R.id.description);
          //  id=itemView.findViewById(R.id.id);
           // url=itemView.findViewById(R.id.url);
            //url.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ReviewList.get(getAdapterPosition()).getReview_writer())));
        }
    }
}
