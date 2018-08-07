package rezkyaulia.com.bamms_project;

import android.databinding.ObservableField;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;

/**
 * Created by Rezky Aulia Pratama on 7/8/18.
 */
public class MainViewModel extends BaseViewModel {

    NetworkManager mNetworkManager;

    private final ObservableField<String> bindResult = new ObservableField<>();
    private DatabaseManager mDatabaseManager;

    @Inject
    public MainViewModel(DatabaseManager databaseManager) {

        mDatabaseManager = databaseManager;
    }

    public ObservableField<String> getBindResult() {
        return bindResult;
    }

}
