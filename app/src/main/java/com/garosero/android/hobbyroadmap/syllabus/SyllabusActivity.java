package com.garosero.android.hobbyroadmap.syllabus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.main.MainActivity;
import com.garosero.android.hobbyroadmap.main.search.SearchFragment;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;

import java.util.ArrayList;

public class SyllabusActivity extends AppCompatActivity {
    TextView tv_roadmap_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        findViewById(R.id.bt_back_challenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SyllabusActivity.this, MainActivity.class));
                finish();
            }
        });

        replaceFragment(new RoadmapFragment(getIntent().getStringArrayListExtra("classCd")));
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}