package com.garosero.android.hobbyroadmap.syllabus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.garosero.android.hobbyroadmap.ProgressActivity;
import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.ModuleClassItem;
import com.garosero.android.hobbyroadmap.data.MyClass;
import com.garosero.android.hobbyroadmap.data.TilItem;
import com.garosero.android.hobbyroadmap.helper.DBHelper;
import com.garosero.android.hobbyroadmap.main.MainActivity;
import com.garosero.android.hobbyroadmap.main.helper.CastHelper;
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;
import com.garosero.android.hobbyroadmap.network.response.UserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoadmapFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView tv_title, tv_count, tv_percentage;
    Button bt_myRoadmap, bt_community;
    ArrayList<String> classCd;
    String LClassID, MClassID, SClassID, subClassID;


    public RoadmapFragment(ArrayList<String> classCd){
        try {
            this.LClassID = classCd.get(0);
            this.MClassID = classCd.get(1);
            this.SClassID = classCd.get(2);
            this.subClassID = classCd.get(3);
        } catch (Exception e){
            e.fillInStackTrace();
        }

        this.classCd = classCd;
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_roadmap, container, false);
        recyclerView = root.findViewById(R.id.rv_syllabus);
        tv_title = root.findViewById(R.id.tv_roadmap_title);
        tv_count = root.findViewById(R.id.tv_count);
        tv_percentage = root.findViewById(R.id.tv_percentage);
        bt_myRoadmap = root.findViewById(R.id.bt_my_roadmap);
        bt_community = root.findViewById(R.id.bt_community);

        DBHelper helper = new DBHelper(getContext(), "newdb.db", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from module_table where l_class_code='"+LClassID+"' and m_class_code='"+MClassID+"' and s_class_code='"+SClassID+"' and sub_class_code='"+subClassID+"'";
        Cursor c = db.rawQuery(sql, null);
        int subClassSize = c.getCount();
        c.moveToNext();
        tv_title.setText(c.getString(c.getColumnIndex("sub_class_name")));

        int moduleOrder = 0;
        HashMap<String, ModuleClassItem> moduleClassMap = new HashMap<>();
        do{
            ModuleClassItem mc = new ModuleClassItem(c.getString(c.getColumnIndex("module_num")), c.getString(c.getColumnIndex("module_name")), c.getString(c.getColumnIndex("module_text")));
            moduleClassMap.put(String.valueOf(moduleOrder++), mc);
        }while(c.moveToNext());

        db.close();
        tv_count.setText("학습모듈 "+subClassSize+"개");

        initRecyclerView(moduleClassMap);

        if (AppApplication.Companion.getUserData().getValue() == null) AppApplication.Companion.requestSubscribeUser();
        UserResponse userResponse = AppApplication.Companion.getUserData().getValue();

        int percentage = 0;
        for (String key : userResponse.getMyClass().keySet()){
            MyClass item = CastHelper.Companion.myClassResponseToMyClass(Objects.requireNonNull(userResponse.getMyClass().get(key)));
            // filter
            if (item.getLClassId().equals(LClassID) && item.getMClassId().equals(MClassID) &&
                    item.getSClassId().equals(SClassID) && item.getSubClassId().equals(subClassID)) {
                percentage = (int) Math.round((double) item.getModules().size() / subClassSize * 100.0);
                break;
            } // end if
        }

        tv_percentage.setText(percentage+"%");

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

    private void initRecyclerView(HashMap<String, ModuleClassItem> moduleClassMap){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SyllabusChildAdapter syllabusChildAdapter = new SyllabusChildAdapter(classCd, moduleClassMap);
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