package rezkyaulia.com.bamms_project.di.service;

import dagger.Component;
import rezkyaulia.com.bamms_project.data.service.PostActivityScheduler;
import rezkyaulia.com.bamms_project.data.service.PostActivityWorker;
import rezkyaulia.com.bamms_project.di.application.ApplicationComponent;

/**
 * Created by Rezky Aulia Pratama on 12/5/18.
 */
@PerService
@Component(dependencies = ApplicationComponent.class)
public interface ServiceComponent {

    void inject(PostActivityScheduler uploadActivityWorker);

    void inject(PostActivityWorker postActivityWorker);
}
