package com.raul.rsd.android.popularmovies;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.raul.rsd.android.popularmovies.Utils.NetworkUtils;

class FloatingActionMenuConfigurator {

    // ------------------------- Attributes --------------------------

    private MainActivity mMainActivity;
    private FloatingActionMenu mFAM;

    // ------------------------- Constructor -------------------------

    FloatingActionMenuConfigurator() {
    }

    // -------------------------- Use Cases --------------------------

    void configure(MainActivity activity){
        mMainActivity = activity;
        mFAM = (FloatingActionMenu) activity.findViewById(R.id.menuFAB);

        configureMenu();
        configureChildren();
    }

    // -------------------------- FAB menu ---------------------------

    // TODO - UI Enhancement - Change filterIcon to closeIcon when opening FAM.
    private void configureMenu(){

        // Change the background depending on the fabMenu Status
        mFAM.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            int fromColor = ContextCompat.getColor(mMainActivity, R.color.transparent);
            int toColor = ContextCompat.getColor(mMainActivity, R.color.background);

            // Creation of animator to transition between the transparent color and the other one
            final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(mFAM,
                    "backgroundColor", new ArgbEvaluator(), fromColor, toColor).setDuration(100);

            @Override
            public void onMenuToggle(boolean opened) {
                if (opened)
                    backgroundColorAnimator.start();
                else
                    backgroundColorAnimator.reverse();
            }
        });

        // On click outside close the menu
        mFAM.setClosedOnTouchOutside(true);
    }

    // TODO React to Snackbars and such
//    // Make the menu react to external stimuli such as a Snackbar
//    private void menuCustomizeBehaviour(){
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fam.getLayoutParams();
//        params.setBehavior(new FloatingActionMenuBehavior());
//        fam.requestLayout();
//    }

    // -------------------------- FAB child --------------------------

    private void configureChildren(){

        FloatingActionButton popularFAB = (FloatingActionButton) mMainActivity.findViewById(R.id.popularFAB);
        if(popularFAB != null)
            popularFAB.setOnClickListener(view -> {
                mFAM.close(false);
                mMainActivity.sActiveSort = NetworkUtils.POPULAR;
                mMainActivity.loadData();
            });

        FloatingActionButton topRatedFAB = (FloatingActionButton) mMainActivity.findViewById(R.id.topRatedFAB);
        if(topRatedFAB != null)
            topRatedFAB.setOnClickListener(view -> {
                mFAM.close(false);
                mMainActivity.sActiveSort = NetworkUtils.TOP_RATED;
                mMainActivity.loadData();
            });
    }
}