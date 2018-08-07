package rezkyaulia.com.bamms_project.di.activity;

import dagger.Component;
import rezkyaulia.com.bamms_project.MainActivity;
import rezkyaulia.com.bamms_project.di.application.ApplicationComponent;

/**
 * Created by Rezky Aulia Pratama on 12/5/18.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class,
})
public interface ActivityComponent {

    //activity
    void inject(MainActivity mainActivity);

}
