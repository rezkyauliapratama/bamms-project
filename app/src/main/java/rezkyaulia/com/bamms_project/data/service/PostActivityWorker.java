package rezkyaulia.com.bamms_project.data.service;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import androidx.work.Worker;
import io.reactivex.disposables.CompositeDisposable;
import rezkyaulia.com.bamms_project.base.BaseApplication;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.di.service.DaggerServiceComponent;
import rezkyaulia.com.bamms_project.di.service.ServiceComponent;
import timber.log.Timber;

public class PostActivityWorker extends Worker {

    @Inject
    public DataManager dataManager;

    ServiceComponent serviceComponent;
    CompositeDisposable compositeDisposable;

    @NonNull
    @Override
    public Result doWork() {
        if (serviceComponent == null && getApplicationContext() instanceof BaseApplication){
            serviceComponent = DaggerServiceComponent.builder()
                    .applicationComponent(((BaseApplication)getApplicationContext()).getApplicationComponent())
                    .build()   ;
        }

        serviceComponent.inject(this);

        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }

        if (serviceComponent == null){
            Timber.e("serviceComponent == null");
        }
       

        return Result.SUCCESS;

    }



    @Override
    public void onStopped(boolean cancelled) {
        super.onStopped(cancelled);
        Timber.e("onstopped");
        serviceComponent = null;
        compositeDisposable.dispose();
    }
}
