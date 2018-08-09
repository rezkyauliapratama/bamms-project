package rezkyaulia.com.bamms_project.di.activity;

import dagger.Component;
import rezkyaulia.com.bamms_project.ui.MainActivity;
import rezkyaulia.com.bamms_project.di.application.ApplicationComponent;import rezkyaulia.com.bamms_project.ui.detail.DetailActivity;
import rezkyaulia.com.bamms_project.ui.login.LoginActivity;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListCardFragment;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListTransactionFragment;

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

    void inject(ListCardFragment listCardFragment);

    void inject(ListTransactionFragment listTransactionFragment);

    void inject(DetailActivity detailActivity);

    void inject(LoginActivity loginActivity);
}
