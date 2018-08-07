package rezkyaulia.com.bamms_project.data.service;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import rezkyaulia.com.bamms_project.base.BaseApplication;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.di.service.DaggerServiceComponent;
import rezkyaulia.com.bamms_project.di.service.ServiceComponent;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 4/7/18.
 */
public class PostActivityScheduler extends JobService {

    @Inject
    public DataManager dataManager;

    ServiceComponent serviceComponent;

    @Override
    public boolean onStartJob(JobParameters job) {
        if (serviceComponent == null){
            serviceComponent = DaggerServiceComponent.builder()
                    .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                    .build()   ;
        }



        serviceComponent.inject(this);

        onUpload();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.e("On stop job");
        serviceComponent = null;
        return false;
    }

    private void onUpload(){

    }


}
