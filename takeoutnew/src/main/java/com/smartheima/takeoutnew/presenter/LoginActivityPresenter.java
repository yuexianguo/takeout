package com.smartheima.takeoutnew.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.smartheima.takeoutnew.modle.dao.TakeoutOpenHelper;
import com.smartheima.takeoutnew.modle.net.ResponseInfo;
import com.smartheima.takeoutnew.modle.net.User;
import com.smartheima.takeoutnew.ui.activity.LoginActivity;

import java.sql.SQLException;
import java.sql.Savepoint;

import retrofit2.Call;

import static com.smartheima.takeoutnew.ui.Utils.TakeoutApp.sUser;

/**
 * Created by Administrator on 2017/4/14.
 */

public class LoginActivityPresenter extends BasePresenter {
    private static final String TAG = "LoginActivityPresenter";
    private LoginActivity mLoginActivity;
    private boolean isLoginOk = false;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
    }



    public void loginByPhone(String phone, int type) {

        Call<ResponseInfo> loginCall = mService.loginByPhone(phone, type);
        loginCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String json) {
           Gson gson = new Gson();
          User user = gson.fromJson(json, User.class);
          Log.d(TAG, "parserJson: "+user.getId());
        //保存用户信息，内存、sqlite(文件、sp)
        //1.内存缓存
        sUser = user;
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(mLoginActivity);
        SQLiteDatabase db = takeoutOpenHelper.getWritableDatabase();
        //需要connection才能进行dao的数据操作
        AndroidDatabaseConnection connection = new AndroidDatabaseConnection(db,true);
        //保存点，操纵数据库的事务
        Savepoint startPoint=null;
        try {
            startPoint = connection.setSavePoint("start");
            connection.setAutoCommit(false);//手动提交事务，注意顺序
            Log.d(TAG, "parserJson: "+"进入try");
            //1，查找历史
            Dao<User,Integer> userDao = takeoutOpenHelper.getDao(User.class);
            Log.d(TAG, "parserJson: getDao"+userDao);
            //2，更新或者创建
            User oldUser = userDao.queryForId(34);
            //下面判断方便统计新老用户个数
            if(oldUser!=null){
                //老用户
                //已经有35这个用户，更新用户信息（老用户）
                userDao.update(user);
                Log.d(TAG, "login: "+"老用户登录");
            }else {
                //新用户
                userDao.create(user);
                Log.d(TAG, "login: "+"新用户登录");
            }
            // userDao.createIfNotExists(user);
            Log.d(TAG, "parserJson: "+"保存用户缓存信息成功");
            connection.commit(startPoint);
            isLoginOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "parserJson: "+"保存用户缓存信息失败");
            isLoginOk = false;
            try {
                connection.rollback(startPoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        mLoginActivity.afterLogin(isLoginOk);
    }
}
