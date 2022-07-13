package com.garosero.android.hobbyroadmap;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.garosero.android.hobbyroadmap.helper.DBHelper;
import com.garosero.android.hobbyroadmap.helper.SqlCol;
import com.garosero.android.hobbyroadmap.main.MainActivity;
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//6-7초 소요
public class ProgressActivity extends AppCompatActivity {
    final String API_KEY = "Qn4yKugqJdk4LojjaMZpGw%2BuUxilBDqMqLpXdQZB8Y57SbmFQ6KJF49K4IBkqtMR5N9ptJ6t4htHUU0WSBxrIw%3D%3D";
    final String TAG = "ProgressActivity";
    ArrayList<Integer> resourceList  = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initResourceList();
        DBHelper helper = new DBHelper(ProgressActivity.this, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        // fetch api data
        if(helper.exists(db)){
            Log.d(TAG,"DB already exists");
//            db.close();
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }

            helper.onUpgrade(db, 1, 1);
            Log.d(TAG, "create DB");
            new Thread(() -> {
                try {
//                long before = System.currentTimeMillis();
                    for (int i = 0; i < resourceList.size(); i++) {
                        Log.d(TAG, i + "");
                        sendMsg(i + 1);

                        try {

                            //String lClassCd = i < 10 ? "0" + i : i + "";
                            //String queryUrl = "https://apis.data.go.kr/B490007/ncsStudyModule/openapi21?serviceKey=" + API_KEY+ "&numOfRows=5&pageNo=1&returnType=xml&ncsLclasCd=" + lClassCd;
                            //URL url = new URL(queryUrl);
                            //InputStream is = url.openStream();

                            InputStream is = getResources().openRawResource(resourceList.get(i));

                            // xml parsing
                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            XmlPullParser xpp = factory.newPullParser();
                            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

                            String tag = "";
                            xpp.next();
                            int eventType = xpp.getEventType();

                            ContentValues values = new ContentValues();

                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_DOCUMENT:
                                        break;

                                    case XmlPullParser.START_TAG:
                                        tag = xpp.getName();//get tag name
                                        try {
                                            switch (tag) {
                                                case "row":
                                                    values = new ContentValues();
                                                    break;
                                                case "ncsLclasCd":  // 대분류코드
                                                    xpp.next();
                                                    values.put(SqlCol.l_class_code.name(), xpp.getText());
                                                    break;
                                                case "ncsLclasCdnm":  // 중분류코드명
                                                    xpp.next();
                                                    values.put(SqlCol.l_class_name.name(), xpp.getText());
                                                    break;
                                                case "ncsMclasCd":  // 중분류코드
                                                    xpp.next();
                                                    values.put(SqlCol.m_class_code.name(), xpp.getText());
                                                    break;
                                                case "ncsMclasCdnm":  // 중분류코드명
                                                    xpp.next();
                                                    values.put(SqlCol.m_class_name.name(), xpp.getText());
                                                    break;
                                                case "ncsSclasCd":  // 소분류코드
                                                    xpp.next();
                                                    values.put(SqlCol.s_class_code.name(), xpp.getText());
                                                    break;
                                                case "ncsSclasCdnm":  // 소분류코드명
                                                    xpp.next();
                                                    values.put(SqlCol.s_class_name.name(), xpp.getText());
                                                    break;
                                                case "ncsSubdCd":  // 세분류코드
                                                    xpp.next();
                                                    values.put(SqlCol.sub_class_code.name(), xpp.getText());
                                                    break;
                                                case "ncsSubdCdnm":  // 세분류코드명
                                                    xpp.next();
                                                    values.put(SqlCol.sub_class_name.name(), xpp.getText());
                                                    break;
                                                case "learnModulSeq":  // 학습모듈번호
                                                    xpp.next();
                                                    values.put(SqlCol.module_num.name(), xpp.getText());
                                                    break;
                                                case "learnModulName":  // 학습모듈명
                                                    xpp.next();
                                                    values.put(SqlCol.module_name.name(), xpp.getText());
                                                    break;
                                                case "learnModulText":  // 학습모듈내용
                                                    xpp.next();
                                                    values.put(SqlCol.module_text.name(), xpp.getText());
                                                    break;
                                            }
                                        } catch (Exception e) {
                                            Log.d(TAG, Log.getStackTraceString(e));
                                        }
                                        break;

                                    case XmlPullParser.TEXT:
                                        break;

                                    case XmlPullParser.END_TAG:
                                        tag = xpp.getName();

                                        if (tag.equals("row") && !values.isEmpty()) {
                                            db.insertWithOnConflict("module_table", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                        }
                                        break;
                                }
                                eventType = xpp.next();
                            }
                        } catch (Exception e) {
                            Log.d(TAG, Log.getStackTraceString(e));
                        }

                    }

                } catch (Exception e) {
                    Log.d(TAG, Log.getStackTraceString(e));

                } finally {
                    db.close();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();

                }
            }).start();
        }
    }

    private void initResourceList(){
        resourceList = new ArrayList<>();
        resourceList.add(R.raw.xml01);
        resourceList.add(R.raw.xml02);
        resourceList.add(R.raw.xml03);
        resourceList.add(R.raw.xml04);
        resourceList.add(R.raw.xml05);
        resourceList.add(R.raw.xml06);
        resourceList.add(R.raw.xml07);
        resourceList.add(R.raw.xml08);
        resourceList.add(R.raw.xml09);
        resourceList.add(R.raw.xml10);

        resourceList.add(R.raw.xml11);
        resourceList.add(R.raw.xml12);
        resourceList.add(R.raw.xml13);
        resourceList.add(R.raw.xml14);
        resourceList.add(R.raw.xml15);
        resourceList.add(R.raw.xml16);
        resourceList.add(R.raw.xml17);
        resourceList.add(R.raw.xml18);
        resourceList.add(R.raw.xml19);
        resourceList.add(R.raw.xml20);

        resourceList.add(R.raw.xml21);
        resourceList.add(R.raw.xml22);
        resourceList.add(R.raw.xml23);
    }

    private ProgressBar progressBar;
    private TextView tvProgress;
    private ProgressHandler progressHandler = new ProgressHandler();
    class ProgressHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (progressBar == null){
                progressBar = findViewById(R.id.pg_download);
                progressBar.setMax(resourceList.size());
            }

            if (tvProgress == null){
                tvProgress = findViewById(R.id.tv_progress);
            }

            int value = msg.getData().getInt("progress");
            progressBar.setProgress(value);
            tvProgress.setText(value+"/"+resourceList.size());
        }
    }

    private void sendMsg(int i){
        Bundle bundle = new Bundle();
        bundle.putInt("progress", i);
        Message msg = progressHandler.obtainMessage();
        msg.setData(bundle);
        progressHandler.sendMessage(msg);
    }
}