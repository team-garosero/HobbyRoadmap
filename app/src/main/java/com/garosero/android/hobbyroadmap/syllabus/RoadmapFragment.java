package com.garosero.android.hobbyroadmap.syllabus;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;

import java.util.ArrayList;

public class RoadmapFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView tv_title, tv_count, tv_percentage;
    Button bt_myRoadmap, bt_community;
    ArrayList<String> classCd;

    public RoadmapFragment(ArrayList<String> classCd){
        this.classCd = classCd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_roadmap, container, false);
        recyclerView = root.findViewById(R.id.rv_syllabus);
        tv_title = root.findViewById(R.id.tv_roadmap_title);
        tv_count = root.findViewById(R.id.tv_count);
        tv_percentage = root.findViewById(R.id.tv_percentage);
        bt_myRoadmap = root.findViewById(R.id.bt_my_roadmap); // todo 어디로 이동??
        bt_community = root.findViewById(R.id.bt_community);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SyllabusChildAdapter(this.classCd));

        tv_title.setText(ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getName());
        tv_count.setText("학습모듈 "+ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getModuleClassMap().size()+"개");

        bt_community.setOnClickListener(view -> {
            replaceFragment(new CommunityFragment(this.classCd));
        });

        return root;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}