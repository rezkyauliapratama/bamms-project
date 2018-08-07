package rezkyaulia.com.bamms_project.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import rezkyaulia.com.bamms_project.util.Utils;

/**
 * Created by Rezky Aulia Pratama on 7/6/18.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    Utils provideUtils(){
        return new Utils(mApplication);
    }

}
