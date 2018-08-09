package rezkyaulia.com.bamms_project.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.DataManager;
import rezkyaulia.com.bamms_project.di.activity.ActivityComponent;
import rezkyaulia.com.bamms_project.di.activity.ActivityContext;
import rezkyaulia.com.bamms_project.di.activity.ActivityModule;
import rezkyaulia.com.bamms_project.di.activity.DaggerActivityComponent;
import rezkyaulia.com.bamms_project.di.viewmodel.ViewModelFactory;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 9/6/18.
 */

public abstract class BaseActivity <T extends ViewDataBinding, V extends ViewModel> extends AppCompatActivity
        implements BaseFragment.Callback{
    public final int PERMISSION_REQUEST = 10000;



    @Inject
    ViewModelFactory mViewModelFactory;

    @Inject
    DataManager mDataManager;

    @Inject
    @ActivityContext
    Context context;

    protected T mViewDataBinding;
    protected V mViewModel;

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

    public T getBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public abstract void inject();

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();

        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        performDataBinding();

        /*if (!(this instanceof InitializeActivity))
            checkAppPermission();
        */
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public ViewModelFactory getViewModelFactory() {
        return mViewModelFactory;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkAppPermission() {

        final List<String> permissions = new ArrayList<>();
        boolean showMessage = readPhoneState(permissions)
                || InternetState(permissions)
                || AccessNetworkState(permissions)
                || AccessWifiState(permissions)
                ;

//        AppPermissions.getInstance().checkAppPermission(this, permissions);

        if (permissions.size() > 0) {
            final String strings[] = new String[permissions.size()];

            if (showMessage) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(R.string.permissionrequestmessage)
                        .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        permissions.toArray(strings),
                                        PERMISSION_REQUEST);
                            }
                        });
                builder.create().show();
            } else
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(strings),
                        PERMISSION_REQUEST);
        }

    }


    private boolean InternetState(List<String> permissions) {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.INTERNET);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                return true;
            }
        }
        return false;
    }


    private boolean AccessNetworkState(List<String> permissions) {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {
                return true;
            }
        }
        return false;
    }

    private boolean AccessWifiState(List<String> permissions) {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {
                return true;
            }
        }
        return false;
    }

    private boolean readPhoneState(List<String> permissions) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                return true;
            }
        }
        return false;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public boolean isNetworkConnected() {
        return false; //NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slid_left, R.anim.do_nothing);
    }

    public void addFragment(int id, Fragment fragment, String tag) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .add(id, fragment, tag)
                    .commit();
        }catch (Exception e){
            Timber.e(e,"changeFragment error");
        }

    }


    public void changeFragment(int id, Fragment fragment, String tag) {

        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(id, fragment, tag)
                    .commit();
        } catch (Exception e) {
            Timber.e(e,"changeFragment error");
        }

    }


}
