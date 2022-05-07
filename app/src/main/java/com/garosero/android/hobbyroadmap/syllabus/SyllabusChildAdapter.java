package com.garosero.android.hobbyroadmap.syllabus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;

public class SyllabusChildAdapter extends RecyclerView.Adapter<SyllabusChildAdapter.ViewHolder>{
    @NonNull
    @Override
    public SyllabusChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview_course_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusChildAdapter.ViewHolder holder, int position) {
        holder.courseDesc.setText("desc"+position);
        holder.courseTitle.setText("title"+position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color, status; // background = taken? color : iv_syllabus_not_taken change stroke color https://stackoverflow.com/questions/4772537/i-need-to-change-the-stroke-color-to-a-user-defined-color-nothing-to-do-with-th
        TextView courseTitle, courseDesc, xp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.iv_color);
            courseTitle = itemView.findViewById(R.id.tv_course_title);
            courseDesc = itemView.findViewById(R.id.tv_course_desc);
            status = itemView.findViewById(R.id.iv_status_color);
            xp = itemView.findViewById(R.id.tv_xp);
        }
    }
}
