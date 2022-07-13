package com.garosero.android.hobbyroadmap.network.request;

import android.util.Log;

import com.garosero.android.hobbyroadmap.data.LClassItem;
import com.garosero.android.hobbyroadmap.data.MClassItem;
import com.garosero.android.hobbyroadmap.data.ModuleClassItem;
import com.garosero.android.hobbyroadmap.data.SClassItem;
import com.garosero.android.hobbyroadmap.data.SubClassItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class ApiRequest{

    public static LClassItem lClass;
    public static int moduleOrder = 0;

    public static void request(String lClassCd, String lClassNm) {
        final String API_KEY = "CLaq8zUYyGtiDmwvSX0dVOYdP10C7dBQzSXaV9j%2BW%2BErU5N0jzyAomUqqPnzBWCR8YggYr6%2FBEbCvksX2BQCsw%3D%3D";
        final String TAG = "ApiRequest";

        String moduleName = ""; //todo 혹시 쓸 수도 있다 &modulNm=사업
        String queryUrl = "http://apis.data.go.kr/B490007/ncsStudyModule/openapi21?serviceKey="+API_KEY
                +"&pageNo="+"1"+"&numOfRows="+"1600"+"&returnType=xml"+"&ncsLclasCd="+lClassCd+moduleName;
        try{
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            // xml parsing
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag = "";
            xpp.next();
            int eventType= xpp.getEventType();

            HashMap<String, String> moduleMap = new HashMap<>();

            lClass = new LClassItem(lClassCd, lClassNm);

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//get tag name

                        switch (tag) {
                            case "row":
                                moduleMap = new HashMap<>();
                                break;
                            case "ncsMclasCd":  // 중분류코드
                                xpp.next();
                                moduleMap.put("mClassCode", xpp.getText());
                                break;
                            case "ncsMclasCdnm":  // 중분류코드명
                                xpp.next();
                                moduleMap.put("mClassName", xpp.getText());
                                break;
                            case "ncsSclasCd":  // 소분류코드
                                xpp.next();
                                moduleMap.put("sClassCode", xpp.getText());
                                break;
                            case "ncsSclasCdnm":  // 소분류코드명
                                xpp.next();
                                moduleMap.put("sClassName", xpp.getText());
                                break;
                            case "ncsSubdCd":  // 세분류코드
                                xpp.next();
                                moduleMap.put("subClassCode", xpp.getText());
                                break;
                            case "ncsSubdCdnm":  // 세분류코드명
                                xpp.next();
                                moduleMap.put("subClassName", xpp.getText());
                                break;
                            case "learnModulSeq":  // 학습모듈번호
                                xpp.next();
                                moduleMap.put("moduleNum", xpp.getText());
                                break;
                            case "learnModulName":  // 학습모듈명
                                xpp.next();
                                moduleMap.put("moduleName", xpp.getText());
                                break;
                            case "learnModulText":  // 학습모듈내용
                                xpp.next();
                                moduleMap.put("moduleText", xpp.getText());
                                break;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("row")) {
                            parsingLClass(moduleMap);
//                            Log.d(TAG, hashMap.toString());
                        };
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e){
            Log.e(TAG, Log.getStackTraceString(e));
        }
//        Log.d(TAG,lClass.toString()); // 파싱 결과 로그
    }

    public static void parsingLClass(HashMap<String, String> moduleMap){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode")) == null){
            lClass.getmClassMap().put(moduleMap.get("mClassCode"), new MClassItem(moduleMap.get("mClassName")));
        }
        parsingMClass(moduleMap);
    }

    public static void parsingMClass(HashMap<String, String> moduleMap){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode")) == null){
            lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                    .getsClassMap().put(moduleMap.get("sClassCode"), new SClassItem(moduleMap.get("sClassName")));
        }
        parsingSClass(moduleMap);
    }

    public static void parsingSClass(HashMap<String, String> moduleMap){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode"))
                .getSubClassMap().get(moduleMap.get("subClassCode")) == null){
            lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                    .getsClassMap().get(moduleMap.get("sClassCode"))
                    .getSubClassMap().put(moduleMap.get("subClassCode"),new SubClassItem(moduleMap.get("subClassName")));
            moduleOrder = 0;
        }
        parsingSubAndModule(moduleMap);
    }

    public static void parsingSubAndModule(HashMap<String, String> moduleMap){
        ModuleClassItem mc = new ModuleClassItem(moduleMap.get("moduleNum"), moduleMap.get("moduleName"), moduleMap.get("moduleText"));

        lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode"))
                .getSubClassMap().get(moduleMap.get("subClassCode"))
                .getModuleClassMap().put(String.valueOf(moduleOrder++),mc);
    }
}