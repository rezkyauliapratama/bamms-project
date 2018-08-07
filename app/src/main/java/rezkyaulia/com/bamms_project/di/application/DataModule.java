package rezkyaulia.com.bamms_project.di.application;

import android.content.Context;

import com.securepreferences.SecurePreferences;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;
import rezkyaulia.com.bamms_project.data.database.FacadeOpenHelper;
import rezkyaulia.com.bamms_project.data.database.entity.DaoMaster;
import rezkyaulia.com.bamms_project.data.database.entity.DaoSession;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Module
public class DataModule {

    String name;

    public DataModule(String name) {
        this.name = name;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return name;
    }

    @Provides
    FacadeOpenHelper provideFacadeOpenHelper(@DatabaseInfo String name, @ApplicationContext Context context){
        return new FacadeOpenHelper(context,name);
    }

    @Provides
    Database provideDatabase(FacadeOpenHelper facadeOpenHelper){
        return facadeOpenHelper.getWritableDb();
    }

    @Provides
    DaoSession provideDaoSession(Database database){
        return new DaoMaster(database).newSession();
    }

    @Provides
    SecurePreferences providePreferenceManager(@ApplicationContext Context context){
        return new SecurePreferences(context);
    }
}
