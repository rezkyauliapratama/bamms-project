package rezkyaulia.com.bamms_project.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Objects;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseFragment;
import rezkyaulia.com.bamms_project.databinding.FragmentTransactionBinding;
import rezkyaulia.com.bamms_project.ui.main.MainViewModel;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.ui.main.adapter.RvTransactionAdapter;
import rezkyaulia.com.bamms_project.util.TimeUtils;

public class ListTransactionFragment extends BaseFragment<FragmentTransactionBinding,MainViewModel> {

    @Inject
    TimeUtils timeUtils;

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

        getViewModel().getStatusLD().observe(Objects.requireNonNull(getActivity()), new Observer<Enum>() {
            @Override
            public void onChanged(@Nullable Enum anEnum) {
                if (anEnum.equals(Status.LOAD_UNSUCCESS)){
                    getBinding().tvNoData.setVisibility(View.VISIBLE);
                }if (anEnum.equals(Status.LOAD_SUCCESS)){
                    getBinding().tvNoData.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initView() {
        getBinding().rvTransactionList.setAdapter(new RvTransactionAdapter(getContext(),getViewModel(),this,timeUtils));
        getBinding().rvTransactionList.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rvTransactionList.setVisibility(View.VISIBLE);
    }
}
