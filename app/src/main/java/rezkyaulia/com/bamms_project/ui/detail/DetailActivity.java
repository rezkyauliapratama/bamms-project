package rezkyaulia.com.bamms_project.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ActivityDetailAccountBinding;
import rezkyaulia.com.bamms_project.ui.login.LoginActivity;
import rezkyaulia.com.bamms_project.ui.main.MainActivity;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.TimeUtils;
import timber.log.Timber;

public class DetailActivity extends BaseActivity<ActivityDetailAccountBinding, DetailViewModel> {
    private BankAccountTbl bankAccountTbl;

    @Inject
    TimeUtils timeUtils;

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
        getViewModel().initialize(bankAccountTbl.getAccountId());
        initView();
        initObserver();
    }

    private void initView() {
        getBinding().tvCardName.setText(bankAccountTbl.getAcountNumber());
        getBinding().tvCardStaths.setText("Active");
        getBinding().tvTotalBalance.setText(String.valueOf(bankAccountTbl.getAccountBalance()));

        getBinding().rvTransactionList.setAdapter(new RvAccTransactionAdapter(this,getViewModel(),this,timeUtils));
        getBinding().rvTransactionList.setLayoutManager(new LinearLayoutManager(this));
        getBinding().rvTransactionList.setVisibility(View.VISIBLE);
    }


    private void initObserver() {
        getViewModel().getStatusLD().observe(this, anEnum -> {
                    if (Objects.requireNonNull(anEnum).equals(Status.LOAD_SUCCESS)){

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

}
