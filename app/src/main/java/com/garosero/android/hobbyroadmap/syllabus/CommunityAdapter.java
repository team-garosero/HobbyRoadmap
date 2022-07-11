package com.garosero.android.hobbyroadmap.syllabus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.SClassItem;
import com.garosero.android.hobbyroadmap.data.TilItem;
import com.garosero.android.hobbyroadmap.main.helper.CastHelper;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{
    private String LClassID;
    private String MClassID;
    private String SClassID;
    private String subClassId;

    public CommunityAdapter(ArrayList<String> classCD) {
        try {
            this.LClassID = classCD.get(0);
            this.MClassID = classCD.get(1);
            this.SClassID = classCD.get(2);
            this.subClassId = classCD.get(3);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_community, parent, false);
        return new CommunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        holder.bind(dataSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content, tv_title, tv_nickname;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_title = itemView.findViewById(R.id.tv_title);
            cardView = itemView.findViewById(R.id.recyclerView_CardView);
        }

        public void bind(TilItem item, int position){
            tv_title.setText(item.getModuleName());
            tv_content.setText(item.getContent());
            tv_nickname.setText(item.getUid()); // todo : tiltitem에 nickanme을 포함하든가, 닉네임을 찾는 과정이 필요

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CommunityDetailActivity.class);
                    intent.putExtra("content",item.getContent());
                    intent.putExtra("moduleName",item.getModuleName());
                    intent.putExtra("nickname",item.getUid());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private ArrayList<TilItem> dataSet = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void submitData(Map<String, TilResponse> tilItems){
        dataSet.clear();
        for (String key : tilItems.keySet()){
            TilItem item = CastHelper.Companion.tilresponseToTilitem(Objects.requireNonNull(tilItems.get(key)));

            // filter: lClass, mclass, sclass, subclass should be the same
            if (item.getLClassId().equals(LClassID) && item.getMClassId().equals(MClassID) &&
                    item.getSClassId().equals(SClassID) && item.getSubClassId().equals(subClassId)) {
                dataSet.add(item);
            } // end if

        }
        notifyDataSetChanged();
    }
}
