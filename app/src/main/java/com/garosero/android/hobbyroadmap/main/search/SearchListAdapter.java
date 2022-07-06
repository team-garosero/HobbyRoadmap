package com.garosero.android.hobbyroadmap.main.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    View view;
    ArrayList<ArrayList<String>> classMap;

    int flag; // 0: 대분류, 1: 중분류, 2: 소분류, 3: 세분류

    // callback
    private OnDataChange onDataChange;
    public void setOnDataChangeListener(OnDataChange onDataChangeListener){
        this.onDataChange = onDataChangeListener;
    }
    public interface OnDataChange {
        void onDataChange(int flag);
    }

    public ArrayList<ArrayList<String>> getClassMap() {
        return classMap;
    }

    public void setClassMap(ArrayList<ArrayList<String>> classMap) {
        this.classMap = classMap;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public SearchListAdapter(){this.flag = 0;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search_list, parent, false);

        return new SearchListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d("SLA-1",flag+"");

        String lClassNm = this.classMap.get(position).get(1);
        String code = this.classMap.get(position).get(0);
        holder.tv_path.setText(lClassNm);

        if(this.flag == 0){ // 대분류 선택시 Api 통신
            holder.ll_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(()->{
                        ApiRequest.request(code, lClassNm); // 대분류 기준 모든 모듈 받아옴
                        SearchFragment.classCd.add(code);
                        onDataChange.onDataChange(1);
                    }).start();
                }
            });
        }
        else if(this.flag == 1){ // 중분류
            holder.ll_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchFragment.classCd.add(code);
                    onDataChange.onDataChange(2);
                }
            });
        }
        else if(this.flag == 2){ // 소분류
            holder.ll_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchFragment.classCd.add(code);
                    onDataChange.onDataChange(3);
                }
            });
        }
        else if(this.flag == 3){ // 세분류
            holder.ll_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchFragment.classCd.add(code);
                    onDataChange.onDataChange(4);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return this.classMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_path;
        LinearLayout ll_class;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_path = itemView.findViewById(R.id.tv_path);
            ll_class = itemView.findViewById(R.id.ll_class);
        }
    }

}
