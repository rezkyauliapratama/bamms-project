package rezkyaulia.com.bamms_project.di.activity;

import dagger.Component;
import rezkyaulia.com.bamms_project.ui.main.MainActivity;
import rezkyaulia.com.bamms_project.di.application.ApplicationComponent;import rezkyaulia.com.bamms_project.ui.detail.DetailActivity;
import rezkyaulia.com.bamms_project.ui.login.LoginActivity;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListCardFragment;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListTransactionFragment;import rezkyaulia.com.bamms_project.ui.register.RegisterActivity;import rezkyaulia.com.bamms_project.ui.transaction.CreditActivity;import rezkyaulia.com.bamms_project.ui.transaction.DebitActivity;import rezkyaulia.com.bamms_project.ui.transaction.TransferActivity;

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

    void inject(RegisterActivity registerActivity);

    void inject(CreditActivity creditActivity);

    void inject(DebitActivity debitActivity);

    void inject(TransferActivity transferActivity);
}
