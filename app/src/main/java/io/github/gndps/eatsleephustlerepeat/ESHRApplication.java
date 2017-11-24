package io.github.gndps.eatsleephustlerepeat;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Gundeep on 03/11/17.
 */

public class ESHRApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("eshr.realm").build();
        Realm.setDefaultConfiguration(config);
    }

}
