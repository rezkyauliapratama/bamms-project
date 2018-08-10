package rezkyaulia.com.bamms_project.ui.register;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.databinding.ActivityRegisterBinding;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import rezkyaulia.com.bamms_project.util.SpinnerArrayAdapter;
import timber.log.Timber;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(RegisterViewModel .class);

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

    private void initView() {
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add(getString(R.string.select_account_type));
        accountTypes.add(new ParameterConstant().MASTERCARD);
        accountTypes.add(new ParameterConstant().VISA);
        SpinnerArrayAdapter AccTypeSpAdapter =
                new SpinnerArrayAdapter<String>(this, accountTypes);
        getBinding().spinnerAccountType.setAdapter(AccTypeSpAdapter);
        getBinding().buttonSave.setOnClickListener(view -> {
            final String name = getBinding().editTextFullname.getText().toString();
            final String username = getBinding().editTextFullname.getText().toString();
            final String password = getBinding().editTextPassword.getText().toString();
            final String retypePassword = getBinding().editTextRetypePassword.getText().toString();
            final String email = getBinding().editTextEmail.getText().toString();
            final String phone = getBinding().editTextPhone.getText().toString();
            final String address = getBinding().editTextAddress.getText().toString();
            final String accountType = (String)getBinding().spinnerAccountType.getSelectedItem();

            getViewModel().postData(name,username,password,retypePassword,email,phone,address,accountType);
        });



    }

    private void initObserver() {
        getViewModel().getStatusLD().observe(this, anEnum -> {
                    if (Objects.requireNonNull(anEnum).equals(Status.LOAD_SUCCESS)) {
                        finish();
                    } else if (anEnum.equals(Status.LOAD_UNSUCCESS)) {
                        Timber.e("Load unsuccess");
                    } else if (anEnum.equals(Status.SHOW_PROGRESS)) {
                        getBinding().layoutProgress.setVisibility(View.VISIBLE);
                    } else if (anEnum.equals(Status.HIDE_PROGRESS)) {
                        getBinding().layoutProgress.setVisibility(View.GONE);
                    }
                }
        );
    }
}
