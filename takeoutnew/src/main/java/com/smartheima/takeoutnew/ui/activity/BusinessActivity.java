package com.smartheima.takeoutnew.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.smartheima.takeoutnew.R;
import com.smartheima.takeoutnew.modle.net.Seller;
import com.smartheima.takeoutnew.ui.adapter.BusinessFragmentPagerAdapter;
import com.smartheima.takeoutnew.ui.fragment.CommentsFragment;
import com.smartheima.takeoutnew.ui.fragment.GoodsFragment;
import com.smartheima.takeoutnew.ui.fragment.SellersFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/16.
 */

public class BusinessActivity extends AppCompatActivity {
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton mIbMenu;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.vp)
    ViewPager mVp;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout mBottomSheetLayout;
    @InjectView(R.id.imgCart)
    ImageView mImgCart;
    @InjectView(R.id.tvSelectNum)
    TextView mTvSelectNum;
    @InjectView(R.id.tvCountPrice)
    TextView mTvCountPrice;
    @InjectView(R.id.tvSendPrice)
    TextView mTvSendPrice;
    @InjectView(R.id.tvDeliveryFee)
    TextView mTvDeliveryFee;
    @InjectView(R.id.tvSubmit)
    TextView mTvSubmit;
    @InjectView(R.id.bottom)
    LinearLayout mBottom;
    @InjectView(R.id.fl_Container)
    FrameLayout mFlContainer;

    public Seller mSeller;
    private BusinessFragmentPagerAdapter mMPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.inject(this);

        initFragment();
    }


    private List<Fragment> mFragmentList = new ArrayList<>();

    private void initFragment() {
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CommentsFragment());
        mFragmentList.add(new SellersFragment());
        mMPageAdapter = new BusinessFragmentPagerAdapter(getSupportFragmentManager());
        mMPageAdapter.setFragmentList(mFragmentList);
        mVp.setAdapter(mMPageAdapter);
        mTabs.setupWithViewPager(mVp);
    }


    @OnClick({R.id.ib_back, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tvSubmit:
                break;
        }
    }
}
