package rezkyaulia.com.bamms_project.ui.detail;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;

public class DetailViewModel extends BaseViewModel {

    private final DatabaseManager databaseManager;
    private final NetworkManager networkManager;

    @Inject
    public DetailViewModel(DatabaseManager databaseManager, NetworkManager networkManager) {
        this.databaseManager = databaseManager;
        this.networkManager = networkManager;
    }
}
