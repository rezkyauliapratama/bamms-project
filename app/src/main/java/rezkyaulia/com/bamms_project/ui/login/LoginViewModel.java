package rezkyaulia.com.bamms_project.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.database.DatabaseManager;
import rezkyaulia.com.bamms_project.data.model.LoginRequest;
import rezkyaulia.com.bamms_project.data.model.LoginResponse;
import rezkyaulia.com.bamms_project.data.network.NetworkManager;
import rezkyaulia.com.bamms_project.ui.main.Status;
import timber.log.Timber;

public class LoginViewModel extends BaseViewModel {


    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();
    private DataManager dataManager;

    @Inject
    public LoginViewModel(DataManager dataManager) {

        this.dataManager = dataManager;
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public void login(String username, String password) {
        Timber.e("login");
        Timber.e("username : "+username);
        Timber.e("password : "+password);

        if (username.length() > 0 && password.length()>0){
            Timber.e("!username.isEmpty() && ! password.isEmpty()");
            LoginRequest loginRequest = new LoginRequest(username,password);
            getCompositeDisposible().add(dataManager.getNetworkManager().getUserApi()
                    .loginSingle(loginRequest).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Timber.e("onsuccess : "+ new Gson().toJson(response));
                        statusLD.setValue(Status.LOAD_SUCCESS);

                    }, throwable -> {
                        Timber.e(throwable,"onerror");
                        statusLD.setValue(Status.LOAD_UNSUCCESS);
                    }));
        }else{
            statusLD.setValue(Status.INVALID);
        }

    }
}
