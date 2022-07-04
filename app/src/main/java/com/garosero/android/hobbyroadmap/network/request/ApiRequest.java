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

public class ApiRequest extends BaseRequest{
    private final String API_KEY = "CLaq8zUYyGtiDmwvSX0dVOYdP10C7dBQzSXaV9j%2BW%2BErU5N0jzyAomUqqPnzBWCR8YggYr6%2FBEbCvksX2BQCsw%3D%3D";
    String TAG = "ApiRequest";
    HashMap<String, String> moduleMap = new HashMap<>();


    LClassItem lClass;

    @Override
    public String getTAG(){
        return this.TAG;
    }

    @Override
    public void request() {

        String moduleName = ""; //todo 혹시 쓸 수도 있다 &modulNm=사업
        // todo 대분류코드ncsLclasCd는 외부에서 받아와야 함->유저가 화면에서 선택하면 넘어오는 것
        String queryUrl = "http://apis.data.go.kr/B490007/ncsStudyModule/openapi21?serviceKey="+API_KEY
                +"&pageNo="+"1"+"&numOfRows="+"34"+"&returnType=xml"+"&ncsLclasCd="+"02"+moduleName;
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

            moduleMap = new HashMap<>();

            // todo 대분류 코드/이름은 외부에서 받아오기
            lClass = new LClassItem("01", "사업관리");

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//get tag name

                        if(tag.equals("row")) {
                            moduleMap = new HashMap<>();
                        }
//                        else if(tag.equals("ncsLclasCd")){ // 대분류코드
//                            xpp.next();
//                            hashMap.put("LclassCode", xpp.getText());
//                        } else if(tag.equals("ncsLclasCdnm")) { // 대분류코드명
//                            xpp.next();
//                            hashMap.put("LclassName", xpp.getText());
//                        }
                        else if(tag.equals("ncsMclasCd")){ // 중분류코드
                            xpp.next();
                            moduleMap.put("mClassCode", xpp.getText());
                        } else if(tag.equals("ncsMclasCdnm")){ // 중분류코드명
                            xpp.next();
                            moduleMap.put("mClassName", xpp.getText());
                        } else if(tag.equals("ncsSclasCd")){ // 소분류코드
                            xpp.next();
                            moduleMap.put("sClassCode", xpp.getText());
                        }else if(tag.equals("ncsSclasCdnm")){ // 소분류코드명
                            xpp.next();
                            moduleMap.put("sClassName", xpp.getText());
                        }else if(tag.equals("ncsSubdCd")){ // 세분류코드
                            xpp.next();
                            moduleMap.put("subClassCode", xpp.getText());
                        }else if(tag.equals("ncsSubdCdnm")){ // 세분류코드명
                            xpp.next();
                            moduleMap.put("subClassName", xpp.getText());
                        }else if(tag.equals("learnModulSeq")){ // 학습모듈번호
                            xpp.next();
                            moduleMap.put("moduleNum", xpp.getText());
                        }else if(tag.equals("learnModulName")){ // 학습모듈명
                            xpp.next();
                            moduleMap.put("moduleName", xpp.getText());
                        }else if(tag.equals("learnModulText")){ // 학습모듈내용
                            xpp.next();
                            moduleMap.put("moduleText", xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("row")) {
                            parsingLClass();
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

    public void parsingLClass(){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode")) == null){
            lClass.getmClassMap().put(moduleMap.get("mClassCode"), new MClassItem(moduleMap.get("mClassName")));
        }

        parsingMClass();
    }

    public void parsingMClass(){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode")) == null){
            lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                    .getsClassMap().put(moduleMap.get("sClassCode"), new SClassItem(moduleMap.get("sClassName")));
        }

        parsingSClass();
    }

    public void parsingSClass(){
        if(lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode"))
                .getSubClassMap().get(moduleMap.get("subClassCode")) == null){
            lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                    .getsClassMap().get(moduleMap.get("sClassCode"))
                    .getSubClassMap().put(moduleMap.get("subClassCode"),new SubClassItem(moduleMap.get("subClassName")));
        }

        parsingSubAndModule();
    }

    public void parsingSubAndModule(){
        ModuleClassItem mc = new ModuleClassItem(moduleMap.get("moduleNum"), moduleMap.get("moduleName"), moduleMap.get("moduleText"));

        lClass.getmClassMap().get(moduleMap.get("mClassCode"))
                .getsClassMap().get(moduleMap.get("sClassCode"))
                .getSubClassMap().get(moduleMap.get("subClassCode"))
                .getModuleClassMap().put(moduleMap.get("moduleNum"),mc);

    }
}