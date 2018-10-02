package com.example.yahia.movieappudacitystage2.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yahia.movieappudacitystage2.R;

public class TraillerAdapter extends RecyclerView.Adapter<TraillerAdapter.TrailerViewHolder> {

    int size;
    TrailerClickListener trailerClickListener;

    public TraillerAdapter(int size, TrailerClickListener trailerClickListener) {
        this.size = size;
        this.trailerClickListener = trailerClickListener;
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailler_item,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
         holder.trailerNumer.setText("Trailler"+"  "+position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        private TextView trailerNumer;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerNumer=itemView.findViewById(R.id.trailer_nmber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trailerClickListener.OnTraillerClickLisner(getAdapterPosition());
                }
            });
        }
    }

    public interface TrailerClickListener{
        void OnTraillerClickLisner(int position);
    }
}
