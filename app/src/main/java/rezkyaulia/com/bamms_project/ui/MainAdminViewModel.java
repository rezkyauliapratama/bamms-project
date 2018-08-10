package rezkyaulia.com.bamms_project.ui;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.ui.main.Status;
import timber.log.Timber;

public class MainAdminViewModel extends BaseViewModel {

    private final DataManager dataManager;

    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();
    private MutableLiveData<List<BankAccountTbl>> accountTblsLD = new MutableLiveData<>();

    @Inject
    public MainAdminViewModel(DataManager dataManager) {

        this.dataManager = dataManager;
        initialize();
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public MutableLiveData<List<BankAccountTbl>> getAccountTblsLD() {
        return accountTblsLD;
    }

    private void initialize(){
        statusLD.setValue(Status.SHOW_PROGRESS);

        getCompositeDisposible().add(dataManager.getNetworkManager().getAccountApi()
                .getByAccountManagerSingle().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response)){

                        if (response.ApiList != null){
                            accountTblsLD.setValue(response.ApiList);
                        }
                        statusLD.setValue(Status.HIDE_PROGRESS);
                    }


                }, throwable -> {
                    Timber.e(throwable,"onerror");
                    statusLD.setValue(Status.LOAD_UNSUCCESS);
                    statusLD.setValue(Status.HIDE_PROGRESS);

                }));
    }

    public void logout() {
        dataManager.getPrefManager().setUserKey(null);
        dataManager.getDbManager().getUserRepo().removeAll();
        dataManager.getDbManager().getAccountRepo().removeAll();
        dataManager.getDbManager().getTransactionRepo().removeAll();
        dataManager.getDbManager().getParameterRepo().removeAll();

        statusLD.setValue(Status.LOGOUT);
    }
}
