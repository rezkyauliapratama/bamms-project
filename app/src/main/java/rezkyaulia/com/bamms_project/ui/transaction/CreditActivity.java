package rezkyaulia.com.bamms_project.ui.transaction;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ActivityCreditBinding;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.SpinnerArrayAdapter;
import timber.log.Timber;

public class CreditActivity extends BaseActivity<ActivityCreditBinding,TransactionViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_credit;
    }

    @Override
    public TransactionViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(TransactionViewModel.class);
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

    private void initView() {

        getViewModel().getBankAccountsLD().observe(this, accountTbls -> {
            List<BankAccountTbl> accounts = new ArrayList<>();
            accounts.add(new BankAccountTbl(getString(R.string.select_account_type)));
            accounts.addAll(accountTbls);

            SpinnerArrayAdapter AccTypeSpAdapter =
                    new SpinnerArrayAdapter<>(CreditActivity.this, accounts);
            getBinding().spinnerAccount.setAdapter(AccTypeSpAdapter);
        });

     /*  */

        getBinding().buttonSave.setOnClickListener(view -> {
                int amount = Integer.valueOf(getBinding().etAmount.getText().toString());
                String name = getBinding().etDescription.getText().toString();
                BankAccountTbl bankAccountTbl = (BankAccountTbl) getBinding().spinnerAccount.getSelectedItem();

                getViewModel().proceedTransaction(name,amount,bankAccountTbl);
        });
    }

    private void initObserver(){
        getViewModel().getStatusLD().observe(this, anEnum -> {
                    if (Objects.requireNonNull(anEnum).equals(Status.LOAD_SUCCESS)){
                        finish();
                    }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                        Timber.e("Load unsuccess");
                        Snackbar.make(getBinding().getRoot(),"Sorry, cannot process your transaction. Please try again!",Snackbar.LENGTH_LONG).show();

                    }else if (anEnum.equals(Status.SHOW_PROGRESS)){
                        getBinding().layoutProgress.setVisibility(View.VISIBLE);
                    }else if (anEnum.equals(Status.HIDE_PROGRESS)){
                        getBinding().layoutProgress.setVisibility(View.GONE);
                    }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                        Snackbar.make(getBinding().getRoot(),"Cannot add new transaction",Snackbar.LENGTH_LONG).show();
                    }else if (anEnum.equals(Status.FILL_BLANK)) {
                        Snackbar.make(getBinding().getRoot(),"Please fill in all blank field",Snackbar.LENGTH_LONG).show();

                    }
                }
        );
    }


}
