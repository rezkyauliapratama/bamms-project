package rezkyaulia.com.bamms_project.view;

/**
 * Created by Rezky Aulia Pratama on 20/6/18.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;

import rezkyaulia.com.bamms_project.R;


public class EndDrawerToggle implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private DrawerArrowDrawable arrowDrawable;
    private AppCompatImageButton toggleButton;
    private String openDrawerContentDesc;
    private String closeDrawerContentDesc;

    public EndDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                           int openDrawerContentDescRes, int closeDrawerContentDescRes) {

        this.drawerLayout = drawerLayout;
        this.openDrawerContentDesc = activity.getString(openDrawerContentDescRes);
        this.closeDrawerContentDesc = activity.getString(closeDrawerContentDescRes);

        arrowDrawable = new DrawerArrowDrawable(toolbar.getContext());
        arrowDrawable.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_END);

        toggleButton = new AppCompatImageButton(toolbar.getContext(), null,
                R.attr.toolbarNavigationButtonStyle);
        toolbar.addView(toggleButton, new LayoutParams(GravityCompat.END));
        toggleButton.setImageDrawable(arrowDrawable);
        toggleButton.setOnClickListener(v -> toggle()
        );
    }

    public void syncState() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            setPosition(1f);
        }
        else {
            setPosition(0f);
        }
    }

    public void toggle() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    public void setPosition(float position) {
        if (position == 1f) {
            arrowDrawable.setVerticalMirror(true);
            toggleButton.setContentDescription(closeDrawerContentDesc);
        }
        else if (position == 0f) {
            arrowDrawable.setVerticalMirror(false);
            toggleButton.setContentDescription(openDrawerContentDesc);
        }
        arrowDrawable.setProgress(position);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        setPosition(Math.min(1f, Math.max(0, slideOffset)));
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        setPosition(1f);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        setPosition(0f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }


}

