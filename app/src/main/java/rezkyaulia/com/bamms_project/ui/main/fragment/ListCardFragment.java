package rezkyaulia.com.bamms_project.ui.main.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Objects;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseFragment;
import rezkyaulia.com.bamms_project.databinding.FragmentCardBinding;
import rezkyaulia.com.bamms_project.ui.main.MainViewModel;
import rezkyaulia.com.bamms_project.ui.main.adapter.RvCardAdapter;
import rezkyaulia.com.bamms_project.util.ParameterConstant;

public class ListCardFragment extends BaseFragment<FragmentCardBinding,MainViewModel> {
    @Inject
    ParameterConstant constant;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card;
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
        ListCardFragment fragment = new ListCardFragment();
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
        getBinding().rvCardList.setAdapter(new RvCardAdapter(getViewModel(),this,constant));
        getBinding().rvCardList.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rvCardList.setVisibility(View.VISIBLE);
    }
}
