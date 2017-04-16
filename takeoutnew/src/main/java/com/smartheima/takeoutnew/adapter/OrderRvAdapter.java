package com.smartheima.takeoutnew.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartheima.takeoutnew.R;
import com.smartheima.takeoutnew.modle.net.Order;
import com.smartheima.takeoutnew.ui.Utils.OrderObservable;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.smartheima.takeoutnew.ui.Utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL;
import static com.smartheima.takeoutnew.ui.Utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE;
import static com.smartheima.takeoutnew.ui.Utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL;

/**
 * Created by Administrator on 2017/4/15.
 */

public class OrderRvAdapter extends RecyclerView.Adapter implements Observer {

    private Context mContext;
    private List<Order> mOrderList;
    private static final String TAG = "OrderRvAdapter";
    public OrderRvAdapter(Context context) {
        mContext = context;
        OrderObservable.getInstance().addObserver(this);
    }


    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "update: "+"观察者收到消息了");
        HashMap<String,String> map = (HashMap<String, String>) data;
        String pushOrderId = map.get("orderId");
        String pushType = map.get("type");
        int position = -1;
        for (int i = 0; i < mOrderList.size() ; i++) {
            Order order = mOrderList.get(i);
            if(order.getId().equals(pushOrderId)){
                order.setType(pushType);
                position = i;
                notifyItemChanged(position);
            }
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = View.inflate(mContext, R.layout.item_order_item, null);*/
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item,parent,false);
         ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mOrderList!=null){
           return  mOrderList.size();
        }
        return 0;
    }

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @InjectView(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @InjectView(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @InjectView(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @InjectView(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @InjectView(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @InjectView(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;
         private Order mOrder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

         public void setData(Order order) {
                this.mOrder = order;
             mTvOrderItemSellerName.setText(order.getSeller().getName());
             mTvOrderItemType.setText(getOrderTypeInfo(order.getType()));
             //把服务器的int 类型的type转成文本信息
         }
     }

    private String getOrderTypeInfo(String type) {
        /**
         * 订单状态
         * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
         */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
                typeInfo = "已提交订单";
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
                typeInfo = "商家接单";
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
                typeInfo = "配送中";
                break;
            case OrderObservable.ORDERTYPE_SERVED:
                typeInfo = "已送达";
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
                typeInfo = "取消的订单";
                break;
        }
        return typeInfo;
    }




    private int getIndex(String type) {
        int index = -1;
//        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
            case ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
//                typeInfo = "配送中";
                index = 2;
                break;
            case OrderObservable.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }
        return index;
    }
}
