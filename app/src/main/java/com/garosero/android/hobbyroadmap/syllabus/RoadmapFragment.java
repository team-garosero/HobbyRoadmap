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

import com.garosero.android.hobbyroadmap.R;

public class RoadmapFragment extends Fragment {
    View root;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_roadmap, container, false);
        recyclerView = root.findViewById(R.id.rv_syllabus);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SyllabusChildAdapter());

        // btn_lookfor -> community frag
        root.findViewById(R.id.btn_lookfor).setOnClickListener(view -> {
            replaceFragment(new CommunityFragment());
        });

        return root;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}