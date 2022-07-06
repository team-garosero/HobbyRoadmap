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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_course_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusChildAdapter.ViewHolder holder, int position) {
        holder.courseDesc.setText("desc"+position);
        holder.courseTitle.setText("title"+position);
        // todo 마지막 아이템이면 dashline invisioble 여백추가-> if 마지막? height set한 다음에 invisioble처리하면 될듯

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color, status, dashLine; // background = taken? color : iv_syllabus_not_taken change stroke color https://stackoverflow.com/questions/4772537/i-need-to-change-the-stroke-color-to-a-user-defined-color-nothing-to-do-with-th
        TextView courseTitle, courseDesc, xp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.iv_color);
            courseTitle = itemView.findViewById(R.id.tv_course_title);
            courseDesc = itemView.findViewById(R.id.tv_course_desc);
            status = itemView.findViewById(R.id.iv_status_color);
            xp = itemView.findViewById(R.id.tv_xp);
            dashLine = itemView.findViewById(R.id.iv_dashline);

        }
    }
}
