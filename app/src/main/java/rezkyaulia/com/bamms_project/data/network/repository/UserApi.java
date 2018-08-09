package rezkyaulia.com.bamms_project.data.network.repository;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;
import com.rezkyaulia.android.light_optimization_data.RequestListener.ParsedCallback;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;
import rezkyaulia.com.bamms_project.base.BaseApi;
import rezkyaulia.com.bamms_project.data.database.entity.UserTbl;
import rezkyaulia.com.bamms_project.data.model.ApiResponse;
import rezkyaulia.com.bamms_project.data.model.LoginRequest;
import rezkyaulia.com.bamms_project.data.model.LoginResponse;
import rezkyaulia.com.bamms_project.di.application.NetworkInfo;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class UserApi extends BaseApi {

    private NetworkClient mNetworkClient;
    private String mBaseUrl;

    @Inject
    public UserApi(NetworkClient networkClient, @NetworkInfo String url) {
        mNetworkClient = networkClient;
        mBaseUrl = url;
    }


    public Single<String> getUserSingle (){
        return Single.create(emitter -> {
            try {

                getUser(new ParsedCallback<String>() {
                    @Override
                    public void onCompleted(String result) {
                        emitter.onSuccess(result);

                    }

                    @Override
                    public void onFailure(IOException e) {
                        emitter.onError(e);
                    }
                });
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    public Single<Response> loginSingle(LoginRequest loginRequest){
        return Single.create(emitter -> {
            try {
               Response response = postLoginSync(loginRequest);
               emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    public Single<UserResponse> userCreateSingle(UserTbl userTbl){
        return Single.create(emitter -> {
            try {
                UserResponse response = postUserSync(userTbl);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }


    private void getUser(ParsedCallback<String> callback){


        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            mNetworkClient.withUrl(mBaseUrl.concat("user")).as(String.class)
                    .getAsFuture(callback);
        } catch (Exception e) {
            Timber.e(e,"getUser Error ");

        }
    }

    private Response postLoginSync(LoginRequest loginRequest){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/login"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(loginRequest)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"getUser Error ");
        }

        return null;
    }


    private UserResponse postUserSync(UserTbl userTbl){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/user"))
                    .as(UserResponse.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(userTbl)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"getUser Error ");
        }

        return null;
    }

    public class Response extends ApiResponse<LoginResponse> {
    }

    public class UserResponse extends ApiResponse<UserTbl> {
    }

}
