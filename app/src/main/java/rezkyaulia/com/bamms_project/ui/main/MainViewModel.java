package rezkyaulia.com.bamms_project.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.DummyData;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.model.LoginRequest;
import rezkyaulia.com.bamms_project.data.model.TransactionRequest;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.TimeUtils;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/8/18.
 */
public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<BankAccountTbl>> bankAccountsLD= new MutableLiveData<>();
    private MutableLiveData<List<TransactionTbl>> transactionsLD= new MutableLiveData<>();
    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();
    private MutableLiveData<BankAccountTbl> bankAccountLD= new MutableLiveData<>();
    private DataManager dataManager;
    private TimeUtils timeUtils;


    @Inject
    public MainViewModel(DataManager dataManager, TimeUtils timeUtils) {
        this.dataManager = dataManager;
        this.timeUtils = timeUtils;

        initialize();
    }


    public MutableLiveData<List<BankAccountTbl>> getBankAccountsLD() {
        return bankAccountsLD;
    }

    public MutableLiveData<List<TransactionTbl>> getTransactionsLD() {
        return transactionsLD;
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    void initialize(){
        Timber.e("initialize");
        Flowable<List<BankAccountTbl>> accountTblObservable = dataManager.getDbManager().getAccountRepo().getAll();

        getCompositeDisposible().add(accountTblObservable.subscribe(accountTbls -> {
            if (accountTbls != null) {
                bankAccountsLD.setValue(accountTbls);
            } else {
                throw new NullPointerException("accountTbls is null");
            }
        }, throwable -> statusLD.setValue(Status.ERROR)));

//        transactionsLD.setValue(dummyData.getTransactions());

        Calendar endCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH,-7);

        TransactionRequest transactionRequest = new TransactionRequest(timeUtils.getDateForApi(startCal.getTime()),timeUtils.getDateForApi(endCal.getTime()));
        Timber.e(new Gson().toJson(transactionRequest));
        getCompositeDisposible().add(dataManager.getNetworkManager().getTransactionApi()
            .getAllTransactionSingle(transactionRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response) && response.ApiList != null){
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

    public void startActivity(BankAccountTbl bankAccountTbl){
        bankAccountLD.setValue(bankAccountTbl);
    }

    public MutableLiveData<BankAccountTbl> getBankAccountLD() {
        return bankAccountLD;
    }
}
