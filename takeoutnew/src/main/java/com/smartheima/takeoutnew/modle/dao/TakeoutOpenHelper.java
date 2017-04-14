package com.smartheima.takeoutnew.modle.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smartheima.takeoutnew.modle.net.User;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/14.
 */

public class TakeoutOpenHelper extends OrmLiteSqliteOpenHelper {


    public TakeoutOpenHelper(Context context) {
        super(context, "takeout35.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建数据库的时候需要创建表
        try {
            TableUtils.clearTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
