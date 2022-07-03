package com.garosero.android.hobbyroadmap.syllabus;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.CourseItem;
import com.garosero.android.hobbyroadmap.main.MainActivity;
import com.garosero.android.hobbyroadmap.main.helper.ActionConfig;
import com.garosero.android.hobbyroadmap.main.til.TilActivity;

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

        // Til write 화면으로 이동
        holder.status.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                CourseItem courseItem = new CourseItem(); // todo 여기에 데이터 넣어주세요.
                courseItem.setDesc("설명");
                courseItem.setOrder(0);
                courseItem.setXp(100);
                courseItem.setTitle("제목입니다.");

                Intent intent = new Intent(holder.itemView.getContext(), TilActivity.class);
                intent.setAction(ActionConfig.Companion.getACTION_ROADMAP_TO_TIL_WRITE());
                intent.putExtra(ActionConfig.Companion.getCOURSE_ITEM(), courseItem);
                holder.itemView.getContext().startActivity(intent);
            }
        });
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
