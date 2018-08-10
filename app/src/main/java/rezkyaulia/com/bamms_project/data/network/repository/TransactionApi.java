package rezkyaulia.com.bamms_project.data.network.repository;

import android.arch.persistence.room.Transaction;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;

import javax.inject.Inject;

import io.reactivex.Single;

import rezkyaulia.com.bamms_project.base.BaseApi;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.model.ApiResponse;
import rezkyaulia.com.bamms_project.data.model.TransactionRequest;
import rezkyaulia.com.bamms_project.data.model.TransferRequest;
import rezkyaulia.com.bamms_project.di.application.NetworkInfo;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class TransactionApi extends BaseApi {

    private NetworkClient mNetworkClient;
    private String mBaseUrl;

    @Inject
    public TransactionApi(NetworkClient networkClient,  @NetworkInfo String url) {
        mNetworkClient = networkClient;
        mBaseUrl = url;
    }

    public Single<Response> getAllTransactionSingle(TransactionRequest transactionRequest){
        return Single.create(emitter -> {
            try {
                Response response = postGetTransaction(transactionRequest);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    public Single<Response> getByAccountTransactionSingle(TransactionRequest transactionRequest){
        return Single.create(emitter -> {
            try {
                Response response = postGetTransactionByAccount(transactionRequest);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }


    public Single<Response> createTransactionSingle(TransactionTbl transactionTbl){
        return Single.create(emitter -> {
            try {
                Response response = postCreateTransaction(transactionTbl);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    public Single<Response> createTransferSingle(TransferRequest transferRequest){
        return Single.create(emitter -> {
            try {
                Response response = postCreateTransfer(transferRequest);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }


        });
    }

    private Response postGetTransaction(TransactionRequest req){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/transactionsByDate"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(req)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"getTransaction Error ");
        }

        return null;
    }

    private Response postGetTransactionByAccount(TransactionRequest req){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/cardTransactionsByDate"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(req)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"getAccountTransactionsByDate Error ");
        }

        return null;
    }

    private Response postCreateTransaction(TransactionTbl transactionTbl){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/transaction"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(transactionTbl)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"postCreateTransaction Error ");
        }

        return null;
    }

    private Response postCreateTransfer(TransferRequest transferRequest){
        if (mNetworkClient == null){
            throw new NullPointerException("Network client == null");
        }
        try {
            Timber.e("header : "+new Gson().toJson(getUserHeaderWithParam()));
            return mNetworkClient.withUrl(mBaseUrl.concat("/transfer"))
                    .as(Response.class)
                    .setHeaders(getUserHeaderWithParam())
                    .setJsonPojoBody(transferRequest)
                    .getSyncFuture();
        } catch (Exception e) {
            Timber.e(e,"postCreateTransaction Error ");
        }

        return null;
    }


    public class Response extends ApiResponse<TransactionTbl> {
    }

}
