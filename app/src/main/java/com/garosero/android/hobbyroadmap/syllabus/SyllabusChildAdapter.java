package com.garosero.android.hobbyroadmap.syllabus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.ModuleClassItem;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class SyllabusChildAdapter extends RecyclerView.Adapter<SyllabusChildAdapter.ViewHolder>{
    HashMap<String, ModuleClassItem> moduleClassMap;
    ArrayList<String> classCd;

    public SyllabusChildAdapter(ArrayList<String> classCd){
        this.classCd = classCd;
        this.moduleClassMap = ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getModuleClassMap();
    }

    @NonNull
    @Override
    public SyllabusChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_course_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusChildAdapter.ViewHolder holder, int position) {
        final int[] colorList = {R.color.roadmap_blue_1, R.color.roadmap_blue_2};

        holder.courseTitle.setText(this.moduleClassMap.get(String.valueOf(position)).getName());

        int i = (position % 6) < 3 ? 0 : 1;
        holder.color.setBackgroundTintList(holder.color.getResources().getColorStateList(colorList[i]));

        if(position == this.moduleClassMap.size()-1){ // 마지막 아이템이면 dashline invisioble
            holder.dashLine.setVisibility(View.INVISIBLE);
        }

        holder.recycler_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo: TIL로 넘어가기
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.moduleClassMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color, status, dashLine; // background = taken? color : iv_syllabus_not_taken change stroke color https://stackoverflow.com/questions/4772537/i-need-to-change-the-stroke-color-to-a-user-defined-color-nothing-to-do-with-th
        TextView courseTitle, xp;
        CardView recycler_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.iv_color);
            courseTitle = itemView.findViewById(R.id.tv_course_title);
            status = itemView.findViewById(R.id.iv_status_color);
            xp = itemView.findViewById(R.id.tv_xp);
            dashLine = itemView.findViewById(R.id.iv_dashline);
            recycler_cardView = itemView.findViewById(R.id.recyclerView_CardView);
        }
    }
}
