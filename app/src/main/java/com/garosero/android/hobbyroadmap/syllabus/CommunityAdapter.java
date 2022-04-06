package com.garosero.android.hobbyroadmap.syllabus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{
    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_community, parent, false);
        return new CommunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
