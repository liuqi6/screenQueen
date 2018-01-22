package com.liuqi.screenqueen.base;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Description:
 * Author: liuqi
 * Version: 1.0
 * Create Date Time: 2018/1/22 下午4:13.
 * Update Date Time:
 *
 * @see
 */
public class ScreenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("screenRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
