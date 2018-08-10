package rezkyaulia.com.bamms_project.ui.transaction;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.model.AccountRequest;
import rezkyaulia.com.bamms_project.data.model.TransferRequest;
import rezkyaulia.com.bamms_project.data.network.repository.AccountApi;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import rezkyaulia.com.bamms_project.util.TimeUtils;
import timber.log.Timber;

public class CreditViewModel extends BaseViewModel {

    private MutableLiveData<List<BankAccountTbl>> bankAccountsLD = new MutableLiveData<>();
    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();
    private MutableLiveData<BankAccountTbl> accountTblLD= new MutableLiveData<>();


    private final DataManager dataManager;
    private final TimeUtils timeUtils;
    private ParameterConstant constant;

    @Inject
    public CreditViewModel(DataManager dataManager, TimeUtils timeUtils, ParameterConstant constant) {
        this.dataManager = dataManager;
        this.timeUtils = timeUtils;
        this.constant = constant;

        initialize();
    }

    public MutableLiveData<List<BankAccountTbl>> getBankAccountsLD() {
        return bankAccountsLD;
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public MutableLiveData<BankAccountTbl> getAccountTblLD() {
        return accountTblLD;
    }

    private void initialize() {
        Flowable<List<BankAccountTbl>> accountTblObservable = dataManager.getDbManager().getAccountRepo().getAll();

        getCompositeDisposible().add(accountTblObservable.subscribe(accountTbls -> {
            Timber.e("accounttbl : "+new Gson().toJson(accountTbls));
            if (accountTbls != null) {
                bankAccountsLD.setValue(accountTbls);
            } else {
                throw new NullPointerException("accountTbls is null");
            }
        }, throwable -> statusLD.setValue(Status.ERROR)));

    }


    public void proceedTransaction(String name, int amount, BankAccountTbl bankAccountTbl) {
        Flowable<ParameterTbl> parameterTblFlowable = dataManager.getDbManager().getParameterRepo().getByCode(constant.CREDIT);

        getCompositeDisposible().add(parameterTblFlowable.subscribe(parameterTbl -> {
            if (parameterTbl != null) {
                Calendar cal = Calendar.getInstance();
                TransactionTbl transactionTbl = new TransactionTbl();
                transactionTbl.setAccountId(bankAccountTbl.getAccountId());
                transactionTbl.setAmount(amount);
                transactionTbl.setName(name);
                transactionTbl.setDate(timeUtils.getDateForApi(cal.getTime()));
                transactionTbl.setType(parameterTbl.getParameterId());


                uploadTransaction(transactionTbl);
            } else {
                throw new NullPointerException("accountTbls is null");
            }
        }, throwable -> statusLD.setValue(Status.ERROR)));


    }

    private void uploadTransaction(TransactionTbl transactionTbl) {
        getCompositeDisposible().add(dataManager.getNetworkManager().getTransactionApi()
                .createTransactionSingle(transactionTbl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response)){
                        dataManager.getDbManager().getTransactionRepo().add(response.ApiValue);
                        dataManager.getDbManager().getAccountRepo().add(response.ApiValue.getAccount());
                        statusLD.setValue(Status.HIDE_PROGRESS);
                        statusLD.setValue(Status.LOAD_SUCCESS);
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

    public void proceedDebit(String name, int amount, BankAccountTbl bankAccountTbl) {
        if (amount < bankAccountTbl.getAccountBalance()){
            Flowable<ParameterTbl> parameterTblFlowable = dataManager.getDbManager().getParameterRepo().getByCode(constant.DEBIT);

            getCompositeDisposible().add(parameterTblFlowable.subscribe(parameterTbl -> {
                if (parameterTbl != null) {
                    Calendar cal = Calendar.getInstance();
                    TransactionTbl transactionTbl = new TransactionTbl();
                    transactionTbl.setAccountId(bankAccountTbl.getAccountId());
                    transactionTbl.setAmount(amount);
                    transactionTbl.setName(name);
                    transactionTbl.setDate(timeUtils.getDateForApi(cal.getTime()));
                    transactionTbl.setType(parameterTbl.getParameterId());


                    uploadTransaction(transactionTbl);
                } else {
                    throw new NullPointerException("accountTbls is null");
                }
            }, throwable -> statusLD.setValue(Status.ERROR)));

        }else{
            statusLD.setValue(Status.INSUFFIENCE_BALANCE);
        }


    }

    public void proceedTransfer(String name, int amount, BankAccountTbl bankAccountTbl, String destinationNumber) {
        Calendar calendar = Calendar.getInstance();

        TransferRequest transferRequest = new TransferRequest(bankAccountTbl.getAcountNumber(),destinationNumber,
                timeUtils.getDateForApi(calendar.getTime()),name,amount);

        getCompositeDisposible().add(dataManager.getNetworkManager().getTransactionApi()
                .createTransferSingle(transferRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response)){

                        dataManager.getDbManager().getTransactionRepo().add(response.ApiValue);
                        dataManager.getDbManager().getAccountRepo().add(response.ApiValue.getAccount());
                        statusLD.setValue(Status.HIDE_PROGRESS);
                        statusLD.setValue(Status.LOAD_SUCCESS);
                    }else{
                        statusLD.setValue(Status.ACCOUNT_NOT_FOUND);
                        statusLD.setValue(Status.HIDE_PROGRESS);
                    }

                }, throwable -> {
                    Timber.e(throwable,"onerror");
                    statusLD.setValue(Status.LOAD_UNSUCCESS);
                    statusLD.setValue(Status.HIDE_PROGRESS);

                }));
    }

    public void checkAccountNumber(String destinationNumber) {
        AccountRequest accountRequest= new AccountRequest(destinationNumber);
        getCompositeDisposible().add(dataManager.getNetworkManager().getAccountApi()
                .getByAccountNumberSingle(accountRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response)){

                        accountTblLD.setValue(response.ApiValue);

                        statusLD.setValue(Status.HIDE_PROGRESS);
                    }else{
                        statusLD.setValue(Status.ACCOUNT_NOT_FOUND);
                        statusLD.setValue(Status.HIDE_PROGRESS);
                    }

                }, throwable -> {
                    Timber.e(throwable,"onerror");
                    statusLD.setValue(Status.LOAD_UNSUCCESS);
                    statusLD.setValue(Status.HIDE_PROGRESS);

                }));

    }
}
