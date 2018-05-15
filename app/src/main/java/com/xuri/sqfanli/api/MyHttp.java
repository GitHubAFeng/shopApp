package com.xuri.sqfanli.api;

import android.util.Log;

import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.util.AESOperator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MyHttp {

    public static String http(RequestParams params, final CallBack mCallBack) {
        String parStr = "";
        List list = params.getStringParams();
        List<HashMap<String, String>> parList = new ArrayList<HashMap<String, String>>();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i).toString();
                str = str.replace("KeyValue", "");
                String key = str.substring(6);
                key = key.substring(0, key.indexOf("',"));

                String value = str.substring(str.indexOf("value=") + 6);
                value = value.substring(0, value.indexOf("}"));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("key", key);
                map.put("val", value);
                parList.add(map);
            }
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("key", "time_stamp");
            map.put("val", timeStamp);
            parList.add(map);
            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("key", "token");
            map1.put("val", Constant.TOKEN);
            parList.add(map1);
        }
        Collections.sort(parList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                String a = o1.get("key");
                String b = o2.get("key");
                return a.compareTo(b);
            }
        });
        for (HashMap<String, String> map : parList) {
            String key = map.get("key").toString();
            String val = map.get("val").toString();
            parStr += key + "=" + val;
        }
        if (!parStr.equals("")) {
            params.addBodyParameter("time_stamp", timeStamp);
            params.addBodyParameter("sign", AESOperator.getInstance().encrypt(parStr));
        }
        String result = "";
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MyHttp", "" + result);
                mCallBack.onCall(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("MyHttp", "" + ex.toString());
                mCallBack.onError(ex.toString());
            }


            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
        return result;
    }

    public interface CallBack {
        public void onCall(String str);

        public void onError(String str);

        public void onFinished();
    }
}
