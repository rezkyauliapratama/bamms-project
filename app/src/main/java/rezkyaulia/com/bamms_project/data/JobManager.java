package rezkyaulia.com.bamms_project.data;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import rezkyaulia.com.bamms_project.data.service.PostActivityScheduler;
import rezkyaulia.com.bamms_project.data.service.PostActivityWorker;

/**
 * Created by Rezky Aulia Pratama on 5/7/18.
 */
@Singleton
public class JobManager {
    private final String TAG_UPLOAD_ACTIVIY_JOB = "UPLOAD_ACTIVITY";
    private final FirebaseJobDispatcher dispatcher;

    @Inject
    public JobManager(FirebaseJobDispatcher dispatcher) {

        this.dispatcher = dispatcher;
    }

    public void postActivityWorker(){
        OneTimeWorkRequest compressionWork =
                new OneTimeWorkRequest.Builder(PostActivityWorker.class)
                        .build();
        WorkManager.getInstance().enqueue(compressionWork);

    }

    public void uploadActivityJob(){
//        Bundle bundle = new Bundle();
//        bundle.putString(PushVisitDataService.ARGS1, guid);
        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(PostActivityScheduler.class)
                // uniquely identifies the job
                .setTag(TAG_UPLOAD_ACTIVIY_JOB)
                // one-off job
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 0))
                // overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
//                .setExtras(bundle)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_ANY_NETWORK
                        // only run when the device is charging
                )
                .build();

        dispatcher.mustSchedule(myJob);
    }

}

