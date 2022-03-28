package com.garosero.android.hobbyroadmap.syllabus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.garosero.android.hobbyroadmap.R;

public class SyllabusActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        recyclerView = findViewById(R.id.rv_syllabus);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SyllabusParentAdapter syllabusParentAdapter = new SyllabusParentAdapter();
        recyclerView.setAdapter(syllabusParentAdapter);

    }
}