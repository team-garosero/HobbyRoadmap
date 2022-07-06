package com.garosero.android.hobbyroadmap.main.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.MClassItem;
import com.garosero.android.hobbyroadmap.data.SClassItem;
import com.garosero.android.hobbyroadmap.data.SubClassItem;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchListFragment extends Fragment {
    View root;
    RecyclerView recycler;
    int position;
//    int flag;

    SearchListAdapter adapter;
    ArrayList<ArrayList<String>> classMap;
    DatabaseReference ref;

    private OnDataChange onDataChange;

    public void setOnDataChangeListener(OnDataChange onDataChangeListener){
        this.onDataChange = onDataChangeListener;
    }
    public interface OnDataChange {
        void onDataChange(int position);
    }

    public SearchListFragment(int position){
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search_list, container, false);
        recycler = root.findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(root.getContext()));

        adapter = new SearchListAdapter();
        classMap = new ArrayList<ArrayList<String>>(); // recylcer view에 표시할 데이터

//        Log.d("SLF-Pos",String.valueOf(position));

        // 1. 대분류 선택
        if(position == 0){
            ref = FirebaseDatabase.getInstance().getReference("L_Class");

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot snap:snapshot.getChildren()){
                            ArrayList<String> arr = new ArrayList();
                            arr.add(snap.getKey());
                            arr.add(snap.getValue().toString());
                            classMap.add(arr);
                        }
                    } else{
                        Log.e("SearchFrag","no data in firebaseDB");
                    }
                    adapter.setClassMap(classMap);
                    adapter.setFlag(position);
                    recycler.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("SearchFrag",error.toString());
                }
            };
            ref.addValueEventListener(eventListener);
        }
        else{
            // 2. 중분류 선택
            if(position == 1){
                for(Map.Entry<String, MClassItem> item : ApiRequest.lClass.getmClassMap().entrySet()){
                    ArrayList<String> arr = new ArrayList();
                    arr.add(item.getKey());
                    arr.add(item.getValue().getName());
                    classMap.add(arr);
                }
            }
            // 3. 소분류 선택
            else if(position == 2){
//                Log.d("SLF-22",ApiRequest.lClass.getmClassMap().get(SearchFragment.classCd.get(1)).getsClassMap().toString());
                if(SearchFragment.classCd.size() == 2){
                    for(Map.Entry<String, SClassItem> item : ApiRequest.lClass.getmClassMap().get(SearchFragment.classCd.get(1)).getsClassMap().entrySet()){
                        ArrayList<String> arr = new ArrayList();
                        arr.add(item.getKey());
                        arr.add(item.getValue().getName());
                        classMap.add(arr);
                    }
                } else{
                    return root;
                }
            }
            // 4. 세분류 선택
            else if(position == 3){
                if(SearchFragment.classCd.size() == 3){
                    for(Map.Entry<String, SubClassItem> item : ApiRequest.lClass.getmClassMap().get(SearchFragment.classCd.get(1)).getsClassMap().get(SearchFragment.classCd.get(2)).getSubClassMap().entrySet()){
                        ArrayList<String> arr = new ArrayList();
                        arr.add(item.getKey());
                        arr.add(item.getValue().getName());
                        classMap.add(arr);
                    }
                } else{
                    return root;
                }
            } else return root;
            // Log.d("1",classMap.toString());
            adapter.setClassMap(classMap);
            adapter.setFlag(position);
            recycler.setAdapter(adapter);
        }

        // SearchListFrag->this->SearchFrag
        adapter.setOnDataChangeListener(new SearchListAdapter.OnDataChange() {
            @Override
            public void onDataChange(int flag) {
//                Log.d("SLF Callback",flag+"");

                onDataChange.onDataChange(flag);
            }
        });

        return root;
    }
}