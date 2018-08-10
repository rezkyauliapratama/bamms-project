package rezkyaulia.com.bamms_project.ui.transaction;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ActivityDebitBinding;
import rezkyaulia.com.bamms_project.databinding.ActivityTransferBinding;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.util.SpinnerArrayAdapter;
import timber.log.Timber;

public class TransferActivity extends BaseActivity<ActivityTransferBinding, CreditViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    public CreditViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(CreditViewModel.class);
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
                    new SpinnerArrayAdapter<>(TransferActivity.this, accounts);
            getBinding().spinnerAccount.setAdapter(AccTypeSpAdapter);
        });



        getBinding().buttonSave.setOnClickListener(view -> {
            String destinationNumber = getBinding().etDestinationAccount.getText().toString();

            getViewModel().checkAccountNumber(destinationNumber);
        });
    }

    private void showDialogConfirmation(BankAccountTbl accountTbl){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Are you sure want to transfer to "+accountTbl.getAcountNumber()+" with name "+accountTbl.getUserTbl().getName()+" ?")

                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    int amount = Integer.valueOf(getBinding().etAmount.getText().toString());
                    String name = getBinding().etDescription.getText().toString();
                    BankAccountTbl bankAccountTbl = (BankAccountTbl) getBinding().spinnerAccount.getSelectedItem();
                    String destinationNumber = getBinding().etDestinationAccount.getText().toString();

                    getViewModel().proceedTransfer(name,amount,bankAccountTbl,destinationNumber);

                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnDismissListener(dialogInterface -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void initObserver(){

        getViewModel().getAccountTblLD().observe(this, new Observer<BankAccountTbl>() {
            @Override
            public void onChanged(@Nullable BankAccountTbl bankAccountTbl) {
                    showDialogConfirmation(Objects.requireNonNull(bankAccountTbl));
            }
        });

        getViewModel().getStatusLD().observe(this, anEnum -> {
                    if (Objects.requireNonNull(anEnum).equals(Status.LOAD_SUCCESS)){
                        finish();
                    }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){

                    }else if (anEnum.equals(Status.SHOW_PROGRESS)){
                        getBinding().layoutProgress.setVisibility(View.VISIBLE);
                    }else if (anEnum.equals(Status.HIDE_PROGRESS)){
                        getBinding().layoutProgress.setVisibility(View.GONE);
                    }else if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                        Snackbar.make(getBinding().getRoot(),"Sorry, your transfer not successfully",Snackbar.LENGTH_LONG).show();
                    }else if (anEnum.equals(Status.INSUFFIENCE_BALANCE)){
                        Snackbar.make(getBinding().getRoot(),"Insuffience balance, please change source account",Snackbar.LENGTH_LONG).show();
                    }else if (anEnum.equals(Status.ACCOUNT_NOT_FOUND)){
                        Snackbar.make(getBinding().getRoot(),"Sorry, your destination number is wrong",Snackbar.LENGTH_LONG).show();
                    }
                }
        );
    }

}
