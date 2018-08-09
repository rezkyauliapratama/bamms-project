package rezkyaulia.com.bamms_project.ui.register;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.database.entity.UserTbl;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import timber.log.Timber;

public class RegisterViewModel extends BaseViewModel {

    private DataManager dataManager;
    private ParameterConstant constant;

    private MutableLiveData<UserTbl> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Enum> statusLD = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(DataManager dataManager,ParameterConstant constant) {
        this.dataManager = dataManager;
        this.constant = constant;
    }

    public LiveData<UserTbl> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Enum> getStatusLD() {
        return statusLD;
    }

    public void postData(String name, String username, String password, String retypePassword, String email, String phone, String address, String accountType) {
        UserTbl userTbl = new UserTbl();
        userTbl.setName(name);
        userTbl.setUsername(username);
        userTbl.setPassword(password);
        userTbl.setEmail(email);
        userTbl.setPhone(phone);
        userTbl.setAddress(address);
        userTbl.setTypeCode(accountType);
        userTbl.setRoleCode(constant.CUSTOMER);

        statusLD.setValue(Status.SHOW_PROGRESS);
        getCompositeDisposible().add(dataManager.getNetworkManager().getUserApi()
                .userCreateSingle(userTbl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.e("onsuccess : "+ new Gson().toJson(response));
                    if (dataManager.getNetworkManager().success(response)){

                        statusLD.setValue(Status.LOAD_SUCCESS);
                        statusLD.setValue(Status.HIDE_PROGRESS);



                    }


                }, throwable -> {
                    Timber.e(throwable,"onerror");

                    statusLD.setValue(Status.LOAD_UNSUCCESS);
                    statusLD.setValue(Status.HIDE_PROGRESS);
                }));
    }
}
