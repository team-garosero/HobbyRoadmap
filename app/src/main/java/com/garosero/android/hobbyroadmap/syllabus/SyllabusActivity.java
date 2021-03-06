package com.garosero.android.hobbyroadmap.syllabus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.main.MainActivity;

public class SyllabusActivity extends AppCompatActivity {
    TextView tv_roadmap_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        findViewById(R.id.bt_back_challenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SyllabusActivity.this, MainActivity.class);
                intent.putExtra("syllabus","myList");
                startActivity(intent);
                finish();
            }
        });

        if (getIntent().hasExtra("classCd")) {
            replaceFragment(new RoadmapFragment(getIntent().getStringArrayListExtra("classCd")));

        } else {

        } // end if
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}