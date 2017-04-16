package com.smartheima.takeoutnew.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartheima.takeoutnew.modle.net.Order;
import com.smartheima.takeoutnew.modle.net.ResponseInfo;
import com.smartheima.takeoutnew.ui.fragment.OrderFragment;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/4/15.
 */

public class OrderFragmentPresenter extends BasePresenter {

    private OrderFragment mOrderFragment;
    public OrderFragmentPresenter(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    public void getOrderList(String userId){
        Call<ResponseInfo> orderCall = mService.getOrderList(userId);
        orderCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String json) {
        Gson gson = new Gson();
        List<Order> orderList = gson.fromJson(json, new TypeToken<List<Order>>() {
        }.getType());
        mOrderFragment.mOrderRvAdapter.setOrderList(orderList);
    }

}
