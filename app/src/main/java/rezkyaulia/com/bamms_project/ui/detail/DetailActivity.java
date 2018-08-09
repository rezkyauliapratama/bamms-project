package rezkyaulia.com.bamms_project.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ActivityDetailAccountBinding;
import timber.log.Timber;

public class DetailActivity extends BaseActivity<ActivityDetailAccountBinding, DetailViewModel> {
    private BankAccountTbl bankAccountTbl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_account;
    }

    @Override
    public DetailViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(DetailViewModel.class);
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

        if (getIntent().hasExtra("bankAccount"))
            bankAccountTbl = getIntent().getParcelableExtra("bankAccount");

        Timber.e("bankAccount : " + new Gson().toJson(bankAccountTbl));
    }
}
