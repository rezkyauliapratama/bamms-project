package rezkyaulia.com.bamms_project.di.application;

import android.content.Context;

import com.rezkyaulia.android.light_optimization_data.NetworkClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rezkyaulia.com.bamms_project.base.BaseApi;

/**
 * Created by Rezky Aulia Pratama on 7/6/18.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    NetworkClient provideHttpClient(@ApplicationContext Context context) {
        return new NetworkClient(context);
    }

    @Provides
    @NetworkInfo
    String provideBaseUrl(){
//        return "http://156.67.221.206/dont-do-framework/public/v1/";
        return "http://10.0.2.2/bamms-api/public/v1";
    }

    @Provides
    @NetworkInfo
    BaseApi provideBaseApi(){
        return new BaseApi();
    }

}
