package rezkyaulia.com.bamms_project.data.network.repository;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;

import javax.inject.Inject;

import io.reactivex.Single;

import rezkyaulia.com.bamms_project.base.BaseApi;
import rezkyaulia.com.bamms_project.di.application.NetworkInfo;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class ActivityApi extends BaseApi {

    private NetworkClient mNetworkClient;
    private String mBaseUrl;

    @Inject
    public ActivityApi(NetworkClient networkClient, @NetworkInfo String url) {
        mNetworkClient = networkClient;
        mBaseUrl = url;
    }



}
