package com.smartheima.takeoutnew.ui.Utils;

import java.util.HashMap;
import java.util.Observable;

/**
 * Created by lidongzhi on 2016/10/20.
 */
public class OrderObservable extends Observable {
    private static OrderObservable sOrderObserver = new OrderObservable();
    private OrderObservable() {
    }

    public static OrderObservable getInstance(){
        return sOrderObserver;
    }

    /* 订单状态
       * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单*/
    public static final String ORDERTYPE_UNPAYMENT = "10";
    public static final String ORDERTYPE_SUBMIT = "20";
    public static final String ORDERTYPE_RECEIVEORDER = "30";
    public static final String ORDERTYPE_DISTRIBUTION = "40";
    // 骑手状态：接单、取餐、送餐
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE = "43";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL = "46";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL = "48";

    public static final String ORDERTYPE_SERVED = "50";
    public static final String ORDERTYPE_CANCELLEDORDER = "60";

    public void newMsgComing(HashMap<String, String> data) {
        //b.通知所有观察者新消息来
        setChanged();//数据发生改变
        notifyObservers(data);//通知观察者
    }
}
