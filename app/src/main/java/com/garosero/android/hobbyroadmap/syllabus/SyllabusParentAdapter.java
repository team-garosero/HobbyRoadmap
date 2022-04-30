package com.garosero.android.hobbyroadmap.syllabus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;

public class SyllabusParentAdapter extends RecyclerView.Adapter<SyllabusParentAdapter.ViewHolder>{
    @NonNull
    @Override
    public SyllabusParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_syllabus_parent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusParentAdapter.ViewHolder holder, int position) {
        holder.syllabusTitle.setText("titleee"+position);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        holder.recyclerView.setAdapter(new SyllabusChildAdapter());
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView syllabusTitle;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            syllabusTitle = itemView.findViewById(R.id.tv_syllabus_title);
            recyclerView = itemView.findViewById(R.id.rv_course);
            // todo 첫 요소일경우 imageview invisioble
        }
    }
}
