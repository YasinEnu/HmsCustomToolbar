package com.yasin.hmstoolbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YASIN on 07,August,2019
 * Email: yasinenubd5@gmail.com
 */
public class CustomActionBarToggle implements DrawerLayout.DrawerListener {

    private final ActionBarDrawerToggle.Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private final int mOpenDrawerContentDescRes;
    private final DrawerLayout mDrawerLayout;
    boolean mDrawerIndicatorEnabled;
    private boolean mDrawerSlideAnimationEnabled;
    private DrawerArrowDrawable mSlider;


    public CustomActionBarToggle(Activity activity, DrawerLayout drawerLayout, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this(activity, (HmsUniversalToolBar) null, drawerLayout, (DrawerArrowDrawable) null, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public CustomActionBarToggle(Activity activity, DrawerLayout drawerLayout, HmsUniversalToolBar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this(activity, toolbar, drawerLayout, (DrawerArrowDrawable) null, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    CustomActionBarToggle(Activity activity, HmsUniversalToolBar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable slider, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        this.mDrawerSlideAnimationEnabled = true;
        this.mDrawerIndicatorEnabled = true;
        if (toolbar != null) {
            this.mActivityImpl = new CustomActionBarToggle.ToolbarCompatDelegate(toolbar);
            toolbar.setMenuOrBackIcon(HmsUniversalToolBar.ICON_MENU);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (CustomActionBarToggle.this.mDrawerIndicatorEnabled) {
                        CustomActionBarToggle.this.toggle();
                    }
                }
            });
        } else if (activity instanceof ActionBarDrawerToggle.DelegateProvider) {
            this.mActivityImpl = ((ActionBarDrawerToggle.DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.mActivityImpl = new CustomActionBarToggle.FrameworkActionBarDelegate(activity);
        }

        this.mDrawerLayout = drawerLayout;
        this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
        this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        if (slider == null) {
            this.mSlider = new DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext());
        } else {
            this.mSlider = slider;
        }
    }


    @Override
    public void onDrawerOpened(@NonNull View view) {

        this.setPosition(1.0F);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mCloseDrawerContentDescRes);
        }


    }


    @Override
    public void onDrawerClosed(@NonNull View view) {

        this.setPosition(0.0F);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mOpenDrawerContentDescRes);
        }

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }


    public boolean isDrawerSlideAnimationEnabled() {
        return this.mDrawerSlideAnimationEnabled;
    }


    void setActionBarDescription(int contentDescRes) {
        this.mActivityImpl.setActionBarDescription(contentDescRes);
    }

    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (this.mDrawerSlideAnimationEnabled) {
            this.setPosition(Math.min(1.0F, Math.max(0.0F, slideOffset)));
        } else {
            this.setPosition(0.0F);
        }

    }

    private void setPosition(float position) {
        if (position == 1.0F) {
            this.mSlider.setVerticalMirror(true);
        } else if (position == 0.0F) {
            this.mSlider.setVerticalMirror(false);
        }

        this.mSlider.setProgress(position);
    }

    void toggle() {
        int drawerLockMode = this.mDrawerLayout.getDrawerLockMode(8388611);
        if (this.mDrawerLayout.isDrawerVisible(8388611) && drawerLockMode != 2) {
            this.mDrawerLayout.closeDrawer(8388611);
        } else if (drawerLockMode != 1) {
            this.mDrawerLayout.openDrawer(8388611);
        }

    }

    static class ToolbarCompatDelegate implements ActionBarDrawerToggle.Delegate {
        final Toolbar mToolbar;
        final Drawable mDefaultUpIndicator;
        final CharSequence mDefaultContentDescription;

        ToolbarCompatDelegate(Toolbar toolbar) {
            this.mToolbar = toolbar;
            this.mDefaultUpIndicator = toolbar.getNavigationIcon();
            this.mDefaultContentDescription = toolbar.getNavigationContentDescription();
        }

        public void setActionBarUpIndicator(Drawable upDrawable, @StringRes int contentDescRes) {
            this.mToolbar.setNavigationIcon(upDrawable);
            this.setActionBarDescription(contentDescRes);
        }

        public void setActionBarDescription(@StringRes int contentDescRes) {
            if (contentDescRes == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(contentDescRes);
            }

        }

        public Drawable getThemeUpIndicator() {
            return this.mDefaultUpIndicator;
        }

        public Context getActionBarThemedContext() {
            return this.mToolbar.getContext();
        }

        public boolean isNavigationVisible() {
            return true;
        }
    }

    private static class FrameworkActionBarDelegate implements ActionBarDrawerToggle.Delegate {
        private final Activity mActivity;

        FrameworkActionBarDelegate(Activity activity) {
            this.mActivity = activity;
        }

        public Drawable getThemeUpIndicator() {
            TypedArray a = this.getActionBarThemedContext().obtainStyledAttributes((AttributeSet) null, new int[]{16843531}, 16843470, 0);
            Drawable result = a.getDrawable(0);
            a.recycle();
            return result;
        }

        public Context getActionBarThemedContext() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return (Context) (actionBar != null ? actionBar.getThemedContext() : this.mActivity);
        }

        public boolean isNavigationVisible() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return actionBar != null && (actionBar.getDisplayOptions() & 4) != 0;
        }

        public void setActionBarUpIndicator(Drawable themeImage, int contentDescRes) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    actionBar.setHomeAsUpIndicator(themeImage);
                    actionBar.setHomeActionContentDescription(contentDescRes);
                }
            }

        }

        public void setActionBarDescription(int contentDescRes) {
            if (Build.VERSION.SDK_INT >= 18) {
                ActionBar actionBar = this.mActivity.getActionBar();
                if (actionBar != null) {
                    actionBar.setHomeActionContentDescription(contentDescRes);
                }
            }
        }
    }
}
