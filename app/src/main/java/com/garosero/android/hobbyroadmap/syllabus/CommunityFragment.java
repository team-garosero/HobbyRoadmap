package com.garosero.android.hobbyroadmap.syllabus;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.garosero.android.hobbyroadmap.data.ModuleClassItem;
import com.garosero.android.hobbyroadmap.data.TilItem;
import com.garosero.android.hobbyroadmap.helper.DBHelper;
import com.garosero.android.hobbyroadmap.main.helper.CastHelper;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CommunityFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView tv_title, tv_community_title, tv_desc, tv_count;
    ImageButton bt_info;
    ArrayList<String> classCd;
    private String LClassID;
    private String MClassID;
    private String SClassID;
    private String subClassID;

    public CommunityFragment(ArrayList<String> classCD){
        this.classCd = classCD;
        try {
            this.LClassID = classCD.get(0);
            this.MClassID = classCD.get(1);
            this.SClassID = classCD.get(2);
            this.subClassID = classCD.get(3);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_community, container, false);

        initView();
        initRecyclerView();

        return root;
    }

    @SuppressLint("Range")
    private void initView(){
        recyclerView = root.findViewById(R.id.rv_community);
        tv_title = root.findViewById(R.id.tv_roadmap_title);
        tv_desc = root. findViewById(R.id.tv_desc);
        tv_community_title = root.findViewById(R.id.tv_community_title);
        tv_count = root.findViewById(R.id.tv_count);
        bt_info = root.findViewById(R.id.btn_info);

        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_desc.getVisibility() == View.VISIBLE) tv_desc.setVisibility(View.GONE);
                else tv_desc.setVisibility(View.VISIBLE);
            }
        });

        DBHelper helper = new DBHelper(getContext(), "newdb.db", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from module_table where l_class_code='"+LClassID+"' and m_class_code='"+MClassID+"' and s_class_code='"+SClassID+"' and sub_class_code='"+subClassID+"'";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        tv_title.setText(c.getString(c.getColumnIndex("sub_class_name")));

        db.close();


        if (AppApplication.Companion.getTilData().getValue() == null) AppApplication.Companion.requestSubscribeTil();
        Map<String, TilResponse> tilItems = AppApplication.Companion.getTilData().getValue();
        Set<String> participants = new HashSet<>(); // participants
        for (String key : tilItems.keySet()){
            TilItem item = CastHelper.Companion.tilresponseToTilitem(Objects.requireNonNull(tilItems.get(key)));
            // filter
            if (item.getLClassId().equals(LClassID) && item.getMClassId().equals(MClassID) &&
                    item.getSClassId().equals(SClassID) && item.getSubClassId().equals(subClassID)) {
                participants.add(item.getUid());
            }
        }
        tv_count.setText(participants.size()+"명이 참여하는 중입니다.");
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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