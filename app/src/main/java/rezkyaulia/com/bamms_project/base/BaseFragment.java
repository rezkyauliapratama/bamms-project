package rezkyaulia.com.bamms_project.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.di.activity.ActivityComponent;
import rezkyaulia.com.bamms_project.di.activity.ActivityModule;
import rezkyaulia.com.bamms_project.di.activity.DaggerActivityComponent;
import rezkyaulia.com.bamms_project.di.viewmodel.ViewModelFactory;
import rezkyaulia.com.bamms_project.util.TimeUtils;


/**
 * Created by Rezky Aulia Pratama on 9/6/18.
 */

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>  extends Fragment {

    protected static final String ARG1 = "arg1";

    @Inject
    ViewModelFactory mViewModelFactory;

    @Inject
    TimeUtils timeUtils;

    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;

    private String[] bundleValue;
    private ActivityComponent mActivityComponent;


    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public abstract void inject();

    public T getBinding() {
        return mViewDataBinding;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(getActivity()))
                    .applicationComponent(BaseApplication.get(getContext()).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();

            inject();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);

        if (getArguments() != null && getArguments().containsKey(ARG1)){
            bundleValue = getArguments().getStringArray(ARG1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public ViewModelFactory getViewModelFactory() {
        return mViewModelFactory;
    }

    public TimeUtils getTimeUtils() {
        return timeUtils;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }

    public String[] getBundleValue() {
        return bundleValue;
    }



    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

}
