package rezkyaulia.com.bamms_project.ui.main.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Objects;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseFragment;
import rezkyaulia.com.bamms_project.databinding.FragmentCardBinding;
import rezkyaulia.com.bamms_project.databinding.FragmentTransactionBinding;
import rezkyaulia.com.bamms_project.ui.MainViewModel;
import rezkyaulia.com.bamms_project.ui.main.adapter.RvCardAdapter;
import rezkyaulia.com.bamms_project.ui.main.adapter.RvTransactionAdapter;

public class ListTransactionFragment extends BaseFragment<FragmentTransactionBinding,MainViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_transaction;
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(Objects.requireNonNull(getActivity()), getViewModelFactory()).get(MainViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return  BR.viewModel;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }


    public static Fragment newInstance(String[] values) {
        ListTransactionFragment fragment = new ListTransactionFragment();
        if (values != null){
            Bundle args = new Bundle();
            args.putStringArray(ARG1,values);
            fragment.setArguments(args);
        }
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();


    }

    private void initView() {
        getBinding().rvTransactionList.setAdapter(new RvTransactionAdapter(getViewModel(),this));
        getBinding().rvTransactionList.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rvTransactionList.setVisibility(View.VISIBLE);
    }
}
