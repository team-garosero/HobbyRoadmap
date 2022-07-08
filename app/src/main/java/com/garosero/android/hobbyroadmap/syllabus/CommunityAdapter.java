package com.garosero.android.hobbyroadmap.syllabus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{
    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_community, parent, false);
        return new CommunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
//        holder.tv_content.setText(position+"cd");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CommunityDetailActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putString("content",);
                // todo: bundle로 닉네임 모듈명 내용 넘기기
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
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
    }
}
