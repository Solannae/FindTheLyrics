package com.solannae.findthelyrics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private DeezerSongModel[] localDataset;
    final private SongListClickListener clickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String placeholder = localDataset[position].getArtist().getName() + " - " + localDataset[position].getTitle();
        holder.textView.setText(placeholder);
    }

    @Override
    public int getItemCount() {
        return localDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private final TextView textView;

        public ViewHolder(TextView view)
        {
            super(view);
            view.setOnClickListener(this);
            textView = view;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onListItemClick(position);
        }
    }

    interface SongListClickListener {
        void onListItemClick(int position);
    }

    public SearchAdapter(DeezerSongModel[] dataset, SongListClickListener listener) {
        localDataset = dataset;
        clickListener = listener;
    }
}
