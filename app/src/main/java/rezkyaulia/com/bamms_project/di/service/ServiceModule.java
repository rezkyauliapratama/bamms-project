package rezkyaulia.com.bamms_project.di.service;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rezkyaulia.com.bamms_project.di.application.ApplicationContext;

/**
 * Created by Rezky Aulia Pratama on 5/7/18.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    FirebaseJobDispatcher provideJobDispatcher(@ApplicationContext Context context) {
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));

    }
}
