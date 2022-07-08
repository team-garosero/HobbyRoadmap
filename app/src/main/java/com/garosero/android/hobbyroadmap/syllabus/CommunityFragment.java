package com.garosero.android.hobbyroadmap.syllabus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView tv_title, tv_community_title, tv_desc;
    ArrayList<String> classCd;

    public CommunityFragment(ArrayList<String> classCd){
        this.classCd = classCd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_community, container, false);

        recyclerView = root.findViewById(R.id.rv_community);
        tv_title = root.findViewById(R.id.tv_roadmap_title);
        tv_desc = root. findViewById(R.id.tv_desc);
        tv_community_title = root.findViewById(R.id.tv_community_title);
        tv_community_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_desc.getVisibility() == View.VISIBLE) tv_desc.setVisibility(View.GONE);
                else tv_desc.setVisibility(View.VISIBLE);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CommunityAdapter());

        tv_title.setText(ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getName());

        return root;
    }
}