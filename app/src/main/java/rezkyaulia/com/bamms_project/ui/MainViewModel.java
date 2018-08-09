package rezkyaulia.com.bamms_project.ui;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.List;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DummyData;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;
import rezkyaulia.com.bamms_project.ui.main.Status;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/8/18.
 */
public class MainViewModel extends BaseViewModel {

    NetworkManager mNetworkManager;
    private DatabaseManager mDatabaseManager;

    private MutableLiveData<List<BankAccountTbl>> bankAccountsLD= new MutableLiveData<>();
    private MutableLiveData<List<TransactionTbl>> transactionsLD= new MutableLiveData<>();
    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();
    private MutableLiveData<BankAccountTbl> bankAccountLD= new MutableLiveData<>();


    @Inject
    public MainViewModel(DatabaseManager databaseManager, NetworkManager networkManager) {

        mDatabaseManager = databaseManager;
        this.mNetworkManager = networkManager;
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
        DummyData dummyData = new DummyData();
        bankAccountsLD.setValue(dummyData.getAccounts());
        transactionsLD.setValue(dummyData.getTransactions());
    }

    public void startActivity(BankAccountTbl bankAccountTbl){
        bankAccountLD.setValue(bankAccountTbl);
    }

    public MutableLiveData<BankAccountTbl> getBankAccountLD() {
        return bankAccountLD;
    }
}
