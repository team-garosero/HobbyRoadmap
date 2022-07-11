package com.garosero.android.hobbyroadmap.syllabus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.AppApplication;
import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.MyClass;
import com.garosero.android.hobbyroadmap.data.TilItem;
import com.garosero.android.hobbyroadmap.main.MainActivity;
import com.garosero.android.hobbyroadmap.main.helper.CastHelper;
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;
import com.garosero.android.hobbyroadmap.network.response.UserResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

        int subClassSize = ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getModuleClassMap().size();

        tv_title.setText(ApiRequest.lClass.getmClassMap().get(this.classCd.get(1))
                .getsClassMap().get(this.classCd.get(2))
                .getSubClassMap().get(this.classCd.get(3)).getName());
        tv_count.setText("학습모듈 "+subClassSize+"개");

        initRecyclerView();

       // todo 1. 유저데이터 접근 2. %계산
//        if (AppApplication.Companion.getUserData().getValue() == null) AppApplication.Companion.requestSubscribeUser();
//        UserResponse userResponse = AppApplication.Companion.getUserData().getValue();
//        int percentage = userResponse.getMyClass().size() / subClassSize;
//        tv_percentage.setText(percentage+"%");

        bt_community.setOnClickListener(view -> {
            replaceFragment(new CommunityFragment(this.classCd));
        });

        bt_myRoadmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("roadmapFrag","myList");
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fixme 제대로 연결되었는지 확인
        SyllabusChildAdapter syllabusChildAdapter = new SyllabusChildAdapter(classCd);
        recyclerView.setAdapter(syllabusChildAdapter);

        if (AppApplication.Companion.getTilData().getValue() != null){
            syllabusChildAdapter.submitData(AppApplication.Companion.getTilData().getValue());
        } // 초기값 지정

        AppApplication.Companion.requestSubscribeTil();
        registerObserver(syllabusChildAdapter);
    }

    private void registerObserver(SyllabusChildAdapter syllabusChildAdapter){
        Observer<Map<String, TilResponse>> tilObserver = new Observer<Map<String, TilResponse>>(){
            @Override
            public void onChanged(Map<String, TilResponse> stringTilResponseMap) {
                syllabusChildAdapter.submitData(stringTilResponseMap);
            }
        };
        AppApplication.Companion.getTilData().observe(getViewLifecycleOwner(), tilObserver);
    }


    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}