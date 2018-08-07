package rezkyaulia.com.bamms_project.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import rezkyaulia.com.bamms_project.data.model.ApiResponse;
import rezkyaulia.com.bamms_project.data.network.repository.ActivityApi;
import rezkyaulia.com.bamms_project.data.network.repository.DetailApi;
import rezkyaulia.com.bamms_project.data.network.repository.UserApi;

/**
 * Created by Rezky Aulia Pratama on 7/6/18.
 */
@Singleton
public class NetworkManager {

    private final String STATUS_OK = "200";
    private final String UNAUTHORIZED = "401";
    private final String CREATED = "201";
    private final String UPLOADED = "201";
    private final String DELETED = "201";


    @Inject
    UserApi userApi;

    @Inject
    ActivityApi activityApi;

    @Inject
    DetailApi detailApi;


    @Inject
    public NetworkManager(){}

    public UserApi getUserApi() {
        return userApi;
    }

    public ActivityApi getActivityApi() {
        return activityApi;
    }

    public DetailApi getDetailApi() {
        return detailApi;
    }

    public boolean success(ApiResponse result) {
        return STATUS_OK.equalsIgnoreCase(result.ApiStatus)
                ||CREATED.equalsIgnoreCase(result.ApiStatus)
                ||DELETED.equalsIgnoreCase(result.ApiStatus)
                ||UPLOADED.equalsIgnoreCase(result.ApiStatus)
                ;
    }
}
