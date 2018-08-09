package rezkyaulia.com.bamms_project.data;


import javax.inject.Inject;
import javax.inject.Singleton;

import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;


/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */
@Singleton
public class DataManager {

    private NetworkManager mNetworkManager;
    private PreferencesManager mPreferenceManager;
    private DatabaseManager mDatabaseManager;

    @Inject
    public DataManager(NetworkManager networkManager, PreferencesManager preferenceManager, DatabaseManager databaseManager) {
        mNetworkManager = networkManager;
        mPreferenceManager = preferenceManager;
        mDatabaseManager = databaseManager;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

    public PreferencesManager getPrefManager() {
        return mPreferenceManager;
    }

    public DatabaseManager getDbManager() {
        return mDatabaseManager;
    }

    public long getUserId(){
        return mDatabaseManager.getUserRepo().getId();
    }

    public String getUserKey(){
        return mDatabaseManager.getUserRepo().getUserKey();
    }

}
