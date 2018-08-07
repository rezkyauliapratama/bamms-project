package rezkyaulia.com.bamms_project.base;

import javax.inject.Inject;

import okhttp3.Headers;
import rezkyaulia.com.bamms_project.data.PreferencesManager;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class BaseApi {
    @Inject
    public PreferencesManager preferencesManager;

    private String apiKey = "v:Dt4p2]$BTRyz^hrS).";

    public BaseApi() {
    }


    protected Headers getUserHeaderWithParam() {
        if (preferencesManager == null){
            throw new NullPointerException("preferences manager is null");
        }
        return new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("ApiKey", apiKey)
                .add("UserKey", preferencesManager.getUserKey())
                .build();
    }


    protected Headers getUserHeader() {
        return new Headers.Builder()
                .add("Content-Type", "application/json")
                .build();
    }

}
