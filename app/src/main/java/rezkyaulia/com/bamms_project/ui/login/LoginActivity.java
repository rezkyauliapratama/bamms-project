package rezkyaulia.com.bamms_project.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.databinding.ActivityLoginBinding;
import rezkyaulia.com.bamms_project.ui.main.MainActivity;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.ui.register.RegisterActivity;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initObserver();
    }

    private void initObserver() {
        getViewModel().getStatusLD().observe(this, anEnum -> {
            if (Objects.requireNonNull(anEnum).equals(Status.LOAD_SUCCESS)){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                Timber.e("Load unsuccess");
            }else if (anEnum.equals(Status.SHOW_PROGRESS)){
                getBinding().layoutProgress.setVisibility(View.VISIBLE);
            }else if (anEnum.equals(Status.HIDE_PROGRESS)){
                getBinding().layoutProgress.setVisibility(View.GONE);
            }
        }
        );
    }

    void initView(){

        getBinding().textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        getBinding().button.setOnClickListener(view -> getViewModel().login(getBinding().etUsername.getText().toString(),getBinding().etPassword.getText().toString()));
    }


}
