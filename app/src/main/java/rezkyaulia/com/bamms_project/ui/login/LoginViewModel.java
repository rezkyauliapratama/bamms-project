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
        initialize();
    }

    void initialize(){
        if (dataManager.getPrefManager().getUserKey().length() > 0){
            statusLD.setValue(Status.LOAD_SUCCESS);
        }
    }
    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public void login(CharSequence username, CharSequence password) {
        Timber.e("login");
        Timber.e("username : "+username);
        Timber.e("password : "+password);

        if (username.length() > 0 && password.length()>0){
            statusLD.setValue(Status.SHOW_PROGRESS);
            Timber.e("!username.isEmpty() && ! password.isEmpty()");
            LoginRequest loginRequest = new LoginRequest(String.valueOf(username),String.valueOf(password));
            getCompositeDisposible().add(dataManager.getNetworkManager().getUserApi()
                    .loginSingle(loginRequest).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Timber.e("onsuccess : "+ new Gson().toJson(response));
                        if (dataManager.getNetworkManager().success(response)){

                            dataManager.getDbManager().getUserRepo().removeAll();
                            dataManager.getDbManager().getParameterRepo().removeAll();

                            dataManager.getDbManager().getUserRepo().add(response.ApiValue.getUserTbl());
                            dataManager.getDbManager().getParameterRepo().add(response.ApiValue.getParameterTbl());

                            String userKey = dataManager.getUserKey();
                            if (userKey != null){
                                dataManager.getPrefManager().setUserKey(userKey);
                                statusLD.setValue(Status.LOAD_SUCCESS);
                            }else{
                                statusLD.setValue(Status.INVALID);
                            }
                            statusLD.setValue(Status.HIDE_PROGRESS);


                        }


                    }, throwable -> {
                        Timber.e(throwable,"onerror");
                        statusLD.setValue(Status.LOAD_UNSUCCESS);
                        statusLD.setValue(Status.HIDE_PROGRESS);

                    }));
        }else{
            statusLD.setValue(Status.INVALID);
            statusLD.setValue(Status.HIDE_PROGRESS);

        }

    }
}
