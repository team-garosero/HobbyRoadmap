package com.garosero.android.hobbyroadmap.main.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SearchAdapter extends FragmentStateAdapter {
    public SearchAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    private OnDataChange onDataChange;

    public void setOnDataChangeListener(OnDataChange onDataChangeListener){
        this.onDataChange = onDataChangeListener;
    }
    public interface OnDataChange {
        void onDataChange(int flag);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        Log.d("SA-Pos",position+" "+SearchFragment.classCd.size());

        SearchListFragment fragment = new SearchListFragment(position);

        // SearchListFrag->this->SearchFrag
        fragment.setOnDataChangeListener(new SearchListFragment.OnDataChange() {
            @Override
            public void onDataChange(int flag) {
//                Log.d("SA Callback",flag+"");

                onDataChange.onDataChange(flag);
            }
        });

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
