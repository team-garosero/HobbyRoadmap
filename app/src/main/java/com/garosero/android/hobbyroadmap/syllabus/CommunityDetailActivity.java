package com.garosero.android.hobbyroadmap.syllabus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.garosero.android.hobbyroadmap.R;

public class CommunityDetailActivity extends AppCompatActivity {
    TextView tv_title, tv_nickname, tv_content;
    ImageView iv_profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
                //todo 번들로 받은 닉네임 모듈명 내용 뿌려주기, btn에 리스너달기
        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        tv_nickname = findViewById(R.id.tv_nickname);
        iv_profile_img = findViewById(R.id.iv_profile_img);

        findViewById(R.id.bt_back).setOnClickListener(view -> finish());
    }
}