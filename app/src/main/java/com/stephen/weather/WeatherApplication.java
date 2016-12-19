package com.stephen.weather;

import android.app.Application;

import com.stephen.weather.provider.ApiProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherApplication extends Application {
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        initProvider();
    }

    private void initProvider() {
        ApiProvider.setUp();
    }

    public synchronized Realm getRealm() {
        if (realm == null) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name(Constants.REALM_DATABASE_NAME)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            realm = Realm.getInstance(config);
        }
        return realm;
    }
}
