package rezkyaulia.com.bamms_project.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.base.BaseViewModel;
import rezkyaulia.com.bamms_project.databinding.ActivityMainAdminBinding;
import rezkyaulia.com.bamms_project.ui.login.LoginActivity;
import rezkyaulia.com.bamms_project.ui.main.MainActivity;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.ui.main.adapter.RvCardAdapter;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import rezkyaulia.com.bamms_project.view.EndDrawerToggle;

public class MainAdminActvity extends BaseActivity<ActivityMainAdminBinding,MainAdminViewModel>  implements NavigationView.OnNavigationItemSelectedListener{
    private EndDrawerToggle toggle;

    @Inject
    ParameterConstant constant;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_admin;
    }

    @Override
    public MainAdminViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(MainAdminViewModel.class);

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

        initDrawerMenu();

        getViewModel().getStatusLD().observe(this, anEnum -> {
                    if (Objects.requireNonNull(anEnum).equals(Status.LOGOUT)){
                        startActivity(new Intent(MainAdminActvity.this, LoginActivity.class));
                        finish();
                    }
                }
        );
    }


    private void initView() {
        getBinding().rvListItem.setAdapter(new RvAccountManagerAdapter(getViewModel(),this,constant));
        getBinding().rvListItem.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        getBinding().rvListItem.addItemDecoration(dividerItemDecoration);
        getBinding().rvListItem.setVisibility(View.VISIBLE);
    }

    /*init navigation layout*/

    void initDrawerMenu(){
        toggle = new EndDrawerToggle(
                this, getBinding().drawerLayout, getBinding().toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public float defaultElevation = getBinding().drawerLayout.getDrawerElevation();

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);


            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (drawerView == getBinding().navView) {
                    updateOffset(slideOffset);
                    getBinding().drawerLayout.setDrawerElevation(0);
                }  else
                    getBinding().drawerLayout.setDrawerElevation(defaultElevation);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBinding().navView.setElevation(0);
        }

        getBinding().drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getBinding().navView.setNavigationItemSelectedListener(this);
        getBinding().drawerLayout.setScrimColor(Color.TRANSPARENT);


    }



    private void updateOffset(float slideOffset) {
//        Timber.e("slide off : "+slideOffset);
        getBinding().navView.setAlpha(slideOffset);
        getBinding().coordinatorLayout.setX((getBinding().navView.getWidth() + 10f) * slideOffset * -1);
        int defaultHeight = ((ViewGroup)getBinding().navView.getParent()).getHeight();
        ViewGroup.LayoutParams params
                = getBinding().coordinatorLayout.getLayoutParams();
        float diffHeight = defaultHeight * (0.1f * slideOffset);
        if (slideOffset == 0)
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        else
            params.height = (int) (defaultHeight - diffHeight);

        getBinding().coordinatorLayout.setLayoutParams(params);
        ViewCompat.setElevation(getBinding().coordinatorLayout, 20 * slideOffset);

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        showPage(id);

        getBinding().drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    private void showPage(int id) {
        if (id == R.id.nav_logout) {
            logOut();
        }
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.logout_confirmation)

                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getViewModel().logout();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
