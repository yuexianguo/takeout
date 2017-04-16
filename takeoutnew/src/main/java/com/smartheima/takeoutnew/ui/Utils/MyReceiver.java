package com.smartheima.takeoutnew.ui.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/4/16.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d(TAG, "jpush:自定义消息 "+message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "jpush: 额外消息"+extras);


        //a.通知数据源新消息来了（班长把新任务贴到黑板上）
        HashMap<String,String> data = processExtra(extras);
        OrderObservable.getInstance().newMsgComing(data);
    }
    private HashMap<String,String> processExtra(String extras) {

        try {
            JSONObject jsonObject = new JSONObject(extras);
            String orderId = jsonObject.getString("orderId");
            String type = jsonObject.getString("type");
            HashMap<String,String> map = new HashMap<>();
            map.put("orderId",orderId);
            map.put("type",type);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
