package rezkyaulia.com.bamms_project.di.application;

import javax.inject.Singleton;

import dagger.Component;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.data.JobManager;
import rezkyaulia.com.bamms_project.di.service.ServiceModule;
import rezkyaulia.com.bamms_project.di.viewmodel.ViewModelFactory;
import rezkyaulia.com.bamms_project.di.viewmodel.ViewModelModule;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import rezkyaulia.com.bamms_project.util.TimeUtils;

/**
 * Created by Rezky Aulia Pratama on 7/6/18.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        DataModule.class,
        ViewModelModule.class,
        ServiceModule.class,

})
public interface ApplicationComponent {

    public DataManager getDataManager();
    public ViewModelFactory getViewModelFactory();
    public TimeUtils getUtils();
    public ParameterConstant getConstant();
    public JobManager getJobManager();
}
