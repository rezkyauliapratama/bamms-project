package rezkyaulia.com.bamms_project.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.app.infideap.stylishwidget.view.Stylish;

import org.greenrobot.greendao.query.QueryBuilder;

import rezkyaulia.com.bamms_project.BuildConfig;
import rezkyaulia.com.bamms_project.di.application.ApplicationComponent;
import rezkyaulia.com.bamms_project.di.application.ApplicationModule;
import rezkyaulia.com.bamms_project.di.application.DaggerApplicationComponent;
import rezkyaulia.com.bamms_project.di.application.DataModule;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/6/18.
 */

public class BaseApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        String fontFolder = "fonts/Exo_2/Exo2-";
        Stylish.getInstance().set(
                fontFolder.concat("Regular.ttf"),
                fontFolder.concat("Medium.ttf"),
                fontFolder.concat("Italic.ttf"),
                fontFolder.concat("MediumItalic.ttf")
        );


        //Timber initialize
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
            QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
            QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
        }

        String dbName = "bamms.db";
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule(dbName))
                .build();

    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }
    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
