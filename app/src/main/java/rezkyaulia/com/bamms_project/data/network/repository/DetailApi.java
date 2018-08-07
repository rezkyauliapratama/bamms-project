package rezkyaulia.com.bamms_project.data.network.repository;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.base.BaseApi;
import rezkyaulia.com.bamms_project.di.application.NetworkInfo;
import timber.log.Timber;

public class DetailApi extends BaseApi {
    private NetworkClient mNetworkClient;
    private String mBaseUrl;

    @Inject
    public DetailApi(NetworkClient networkClient, @NetworkInfo String url) {
        mNetworkClient = networkClient;
        mBaseUrl = url;
    }




}
