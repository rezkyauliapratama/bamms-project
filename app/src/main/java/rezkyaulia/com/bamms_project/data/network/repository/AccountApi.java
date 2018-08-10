package rezkyaulia.com.bamms_project.data.network.repository;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;
import com.vladsch.flexmark.internal.PostProcessorManager;

import javax.inject.Inject;

import io.reactivex.Single;
import rezkyaulia.com.bamms_project.base.BaseApi;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.model.AccountRequest;
import rezkyaulia.com.bamms_project.data.model.ApiResponse;
import rezkyaulia.com.bamms_project.di.application.NetworkInfo;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class AccountApi extends BaseApi {

    private NetworkClient mNetworkClient;
    private String mBaseUrl;

    @Inject
    public AccountApi(NetworkClient networkClient, @NetworkInfo String url) {
        mNetworkClient = networkClient;
        mBaseUrl = url;
    }


    public Single<Response> getByAccountNumberSingle(AccountRequest request){
        return Single.create(emitter -> {
            try {
                Response response = postGetAccountByNumber(request);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }



    public Single<Response> getByAccountManagerSingle(){
        return Single.create(emitter -> {
            try {
                Response response = postGetAccountByManager();
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    private Response postGetAccountByNumber(AccountRequest req){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/accountByNumber"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(req)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"getAccountTransactionsByDate Error ");
        }

        return null;
    }

    private Response postGetAccountByManager(){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/accountsManager"))
                    .as(Response.class,NetworkClient.POST)
                    .setHeaders(getUserHeaderWithParam())
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"postGetAccountByManager Error ");
        }

        return null;
    }

    public class Response extends ApiResponse<BankAccountTbl> {
    }

}
