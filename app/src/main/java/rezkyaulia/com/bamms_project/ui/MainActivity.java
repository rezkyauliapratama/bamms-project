package rezkyaulia.com.bamms_project.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.infideap.stylishwidget.view.ATextView;

import java.util.ArrayList;
import java.util.List;

import rezkyaulia.com.bamms_project.BR;
import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.base.BaseActivity;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ActivityMainBinding;
import rezkyaulia.com.bamms_project.ui.detail.DetailActivity;
import rezkyaulia.com.bamms_project.ui.main.Status;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListCardFragment;
import rezkyaulia.com.bamms_project.ui.main.fragment.ListTransactionFragment;
import rezkyaulia.com.bamms_project.view.EndDrawerToggle;
import timber.log.Timber;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel>
    implements NavigationView.OnNavigationItemSelectedListener {
    List<Fragment> fragments;
    Fragment fragment;
    private LfPagerAdapter adapter;
    private EndDrawerToggle toggle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this,getViewModelFactory()).get(MainViewModel.class);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        fragments = new ArrayList<>();
        fragment = new Fragment();

        initDrawerMenu();

        initViewPager();
        initTab();

        initObserver();


    }


    private void initObserver() {
       getViewModel().getBankAccountLD().observe(this, new Observer<BankAccountTbl>() {
           @Override
           public void onChanged(@Nullable BankAccountTbl bankAccountTbl) {
               Intent intent = new Intent(MainActivity.this, DetailActivity.class);
               intent.putExtra("bankAccount",bankAccountTbl);
               startActivity(intent);
           }
       });
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




    /*init view pager*/
    private void initTab() {
        TabLayout.Tab[] tabs = {
                getBinding().contentLayout.tabLayout.newTab().setText("Card list"),
                getBinding().contentLayout.tabLayout.newTab().setText("Transaction")

        };

        for (TabLayout.Tab tab : tabs) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setWeightSum(1);
            ATextView newTab = new ATextView(this);
            newTab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            newTab.setGravity(Gravity.CENTER);
            newTab.setMaxLines(1);
            newTab.setText(tab.getText());

            newTab.setTextColor(ContextCompat.getColor(this, (R.color.colorWhite)));

            layout.addView(newTab);

            tab.setCustomView(layout);
            getBinding().contentLayout.tabLayout.addTab(tab);
        }

        getBinding().contentLayout.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment = fragments.get(tab.getPosition());
                getBinding().contentLayout.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        Timber.e("INITVIEWPAGER");
        fragments.add(ListCardFragment.newInstance(null));
        fragments.add(ListTransactionFragment.newInstance(null));

        fragment = fragments.get(0);
        this.adapter = new LfPagerAdapter(getSupportFragmentManager());

        getBinding().contentLayout.viewPager.setAdapter(adapter);
        getBinding().contentLayout.viewPager.setPagingEnabled(false);
        getBinding().contentLayout.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(getBinding().contentLayout.tabLayout));
    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }

        protected class LfPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_ITEMS = 2;

        private FragmentManager fragmentManager;

        public LfPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentManager = fm;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return fragments.get(1);
                default:
                    return fragments.get(0);
            }
        }
    }

    /*end of init view pager*/
}
