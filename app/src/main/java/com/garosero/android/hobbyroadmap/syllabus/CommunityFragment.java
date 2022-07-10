package com.garosero.android.hobbyroadmap.syllabus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.AppApplication;
import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;

import java.util.ArrayList;
import java.util.Map;

public class CommunityFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView tv_title, tv_community_title, tv_desc;
    ImageButton bt_info;
    ArrayList<String> classCd;

    public CommunityFragment(ArrayList<String> classCd){
        this.classCd = classCd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_community, container, false);

        initView();
        initRecyclerView();

        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.rv_community);
        tv_title = root.findViewById(R.id.tv_roadmap_title);
        tv_desc = root. findViewById(R.id.tv_desc);
        tv_community_title = root.findViewById(R.id.tv_community_title);
        bt_info = root.findViewById(R.id.btn_info);
        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_desc.getVisibility() == View.VISIBLE) tv_desc.setVisibility(View.GONE);
                else tv_desc.setVisibility(View.VISIBLE);
            }
        });

        tv_title.setText(ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getName());
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fixme 제대로 연결되었는지 확인
        CommunityAdapter communityAdapter = new CommunityAdapter(classCd);
        recyclerView.setAdapter(communityAdapter);

        if (AppApplication.Companion.getTilData().getValue() != null){
            communityAdapter.submitData(AppApplication.Companion.getTilData().getValue());
        } // 초기값 지정

        AppApplication.Companion.requestSubscribeTil();
        registerObserver(communityAdapter);
    }

    private void registerObserver(CommunityAdapter communityAdapter){
        Observer<Map<String, TilResponse>> tilObserver = new Observer<Map<String, TilResponse>>(){
            @Override
            public void onChanged(Map<String, TilResponse> stringTilResponseMap) {
                communityAdapter.submitData(stringTilResponseMap);
            }
        };
        AppApplication.Companion.getTilData().observe(getViewLifecycleOwner(), tilObserver);
    }
}