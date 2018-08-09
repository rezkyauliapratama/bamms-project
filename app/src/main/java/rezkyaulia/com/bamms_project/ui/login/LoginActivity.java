package rezkyaulia.com.bamms_project.ui.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.databinding.ActivityLoginBinding;
import rezkyaulia.com.bamms_project.ui.main.Status;
import timber.log.Timber;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(LoginViewModel .class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initObserver();
    }

    private void initObserver() {
        getViewModel().getStatusLD().observe(this, new Observer<Enum>() {
                    @Override
                    public void onChanged(@Nullable Enum anEnum) {
                        if (anEnum.equals(Status.LOAD_SUCCESS)){
                            Timber.e("Load Success");
                        }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                            Timber.e("Load unsuccess");
                        }
                    }
                }
        );
    }

    void initView(){
        Timber.e(getBinding().edittextUsername.getText().toString());
        String username = getBinding().edittextUsername.getText().toString();
        String password = getBinding().edittextPin.getText().toString();

        getBinding().button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewModel().login(username,password);
            }
        });
    }


}
