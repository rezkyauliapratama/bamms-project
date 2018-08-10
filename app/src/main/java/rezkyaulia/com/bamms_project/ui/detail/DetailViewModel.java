package rezkyaulia.com.bamms_project.ui.detail;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.model.TransactionRequest;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.TimeUtils;
import timber.log.Timber;

public class DetailViewModel extends BaseViewModel {


    private DataManager dataManager;
    private TimeUtils timeUtils;

    private MutableLiveData<List<TransactionTbl>> transactionsLD= new MutableLiveData<>();
    private MutableLiveData<BankAccountTbl> bankAccountTblLD= new MutableLiveData<>();
    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();

    @Inject
    public DetailViewModel(DataManager dataManager, TimeUtils timeUtils) {
        this.dataManager = dataManager;
        this.timeUtils = timeUtils;
    }

    public MutableLiveData<List<TransactionTbl>> getTransactionsLD() {
        return transactionsLD;
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public MutableLiveData<BankAccountTbl> getBankAccountTblLD() {
        return bankAccountTblLD;
    }

    public void initialize(long id){
        Calendar endCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH,-7);

        Observable<BankAccountTbl> bankAccountTblObservable = dataManager.getDbManager().getAccountRepo().get(id);

        getCompositeDisposible().add(bankAccountTblObservable.subscribe(accountTbl -> {
            if (accountTbl != null) {
                bankAccountTblLD.setValue(accountTbl);
            } else {
                throw new NullPointerException("accountTbls is null");
            }
        }, throwable -> {statusLD.setValue(Status.ERROR);
            statusLD.setValue(Status.HIDE_PROGRESS);
        }));


        TransactionRequest transactionRequest = new TransactionRequest(timeUtils.getDateForApi(startCal.getTime()),timeUtils.getDateForApi(endCal.getTime()),id);
        getCompositeDisposible().add(dataManager.getNetworkManager().getTransactionApi()
                .getByAccountTransactionSingle(transactionRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response) && response.ApiList != null){
                        List<TransactionTbl>transactionTbls = new ArrayList<>();
                        transactionTbls.addAll(response.ApiList);

                        for (TransactionTbl transactionTbl :  transactionTbls){
                            Observable<ParameterTbl> parameterTblFlowable = dataManager.getDbManager().getParameterRepo().get(transactionTbl.getType());

                            getCompositeDisposible().add(parameterTblFlowable.subscribe(parameterTbl -> {
                                if (parameterTbl != null) {
                                    transactionTbl.setType_code(parameterTbl.getCode());
                                } else {
                                    throw new NullPointerException("accountTbls is null");
                                }
                            }, throwable -> {statusLD.setValue(Status.ERROR);
                                statusLD.setValue(Status.HIDE_PROGRESS);
                            }));
                        }
                        transactionsLD.setValue(response.ApiList);
                    }else{
                        statusLD.setValue(Status.LOAD_UNSUCCESS);
                        statusLD.setValue(Status.HIDE_PROGRESS);
                    }


                }, throwable -> {
                    Timber.e(throwable,"onerror");
                    statusLD.setValue(Status.LOAD_UNSUCCESS);
                    statusLD.setValue(Status.HIDE_PROGRESS);

                }));
    }
}
