package com.garosero.android.hobbyroadmap.main.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.LClassItem;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.syllabus.CommunityAdapter;
import com.garosero.android.hobbyroadmap.syllabus.RoadmapFragment;
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kotlinx.coroutines.Dispatchers;

public class SearchFragment extends Fragment {
    View root;
    TabLayout tabLayout;
    SearchAdapter adapter;
    ViewPager2 pager;
    TextView tv_path;
    String text = "";

    public static ArrayList<String> classCd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);
        tabLayout = root.findViewById(R.id.tab_layout);
        tv_path = root.findViewById(R.id.tv_path);
        pager = root.findViewById(R.id.pager);
        adapter = new SearchAdapter(this);
        pager.setAdapter(adapter);
        pager.setUserInputEnabled(false); // disable swipe
        classCd = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
//                Log.d("SFH",message.getData().get("flag").toString());

                if(pager.getAdapter() != null){
                    pager.setAdapter(null);
                }
                pager.setAdapter(adapter);
                pager.setCurrentItem(message.getData().getInt("flag"));
//                Log.d("tab",message.getData().getInt("flag")+"");
                switch (classCd.size()){
                    case 0:
                        break;
                    case 1:
                        text += ApiRequest.lClass.getName();
                        break;
                    case 2:
                        text += " > "+ApiRequest.lClass.getmClassMap().get(classCd.get(1)).getName();
                        break;
                    case 3:
                        text += " > "+ApiRequest.lClass.getmClassMap().get(classCd.get(1)).getsClassMap().get(classCd.get(2)).getName();
                        break;
                }
                tv_path.setText(text);
                if(classCd.size() == 4){
                    Intent intent = new Intent(getActivity(), SyllabusActivity.class);
                    // deep copy
                    ArrayList<String> classCd2 = new ArrayList<>(classCd);
                    intent.putExtra("classCd",classCd2);
                    if(pager.getAdapter() != null){
                        pager.setAdapter(null);
                        text = "";
                    }
                    startActivity(intent);
                }
                return true;
            }
        });

        // SearchAdapter -> SearchFragment
        adapter.setOnDataChangeListener(new SearchAdapter.OnDataChange() {
            @Override
            public void onDataChange(int flag) {
//                Log.d("SF Callback",flag+"");

                Message msg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("flag",flag);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });

        final List<String> tabElements = Arrays.asList("대분류","중분류","소분류","세분류"); //flag 0, 1, 2, 3

        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position){
                tab.setText(tabElements.get(position));
                tab.view.setClickable(false);
            }
        }).attach();
//        pager.setCurrentItem(0);

        //todo 하위분류 선택 후 다시 상위분류 선택하는 것 처리
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition() != classCd.size()){
//                    for(int i = classCd.size()-1;tab.getPosition()==classCd.size();i--)
//                        classCd.remove(i);
//                    Log.d("SF tab selected",classCd.toString());
//
//                    if(pager.getAdapter() != null){
//                        pager.setAdapter(null);
//                    }
//                    pager.setAdapter(adapter);
//                    pager.setCurrentItem(tab.getPosition());
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) { //selected실행될 때 그 전의 position이 들어옴
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) { // ㅎㅏㄴ번에 두번클릭됨
//
//            }
//        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("search","onresume");
    }
}