package com.example.sagar.democp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

    public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mBasics, mProgram, mPatternProgram;
    private Toolbar toolbar;
    private ViewPager mMainPager;
    private DrawerLayout drawer;
    private PagerViewAdapter mPagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            doAnimation();
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mBasics = findViewById(R.id.basicsLabel);
        mProgram = findViewById(R.id.programLabel);
        mPatternProgram = findViewById(R.id.patternProgramLabel);
        mProgram.setTextColor(getColor(R.color.colorTextTabLight));
        mPatternProgram.setTextColor(getColor(R.color.colorTextTabLight));

        mMainPager = findViewById(R.id.mainPager);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mBasics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });
        mProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });
        mPatternProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTabs(int position) {
        if (position == 0) {
            mBasics.setTextColor(getColor(R.color.colorTextTabBright));

            mProgram.setTextColor(getColor(R.color.colorTextTabLight));

            mPatternProgram.setTextColor(getColor(R.color.colorTextTabLight));
        }
        if (position == 1) {
            mBasics.setTextColor(getColor(R.color.colorTextTabLight));

            mProgram.setTextColor(getColor(R.color.colorTextTabBright));

            mPatternProgram.setTextColor(getColor(R.color.colorTextTabLight));
        }
        if (position == 2) {
            mBasics.setTextColor(getColor(R.color.colorTextTabLight));

            mProgram.setTextColor(getColor(R.color.colorTextTabLight));

            mPatternProgram.setTextColor(getColor(R.color.colorTextTabBright));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_basics:
                mMainPager.setCurrentItem(0);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_program:
                mMainPager.setCurrentItem(1);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_patternProgramLabel:
                mMainPager.setCurrentItem(2);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "DemoCP");
                    String message = "\nLet me recommend you this application\n\n";
                    message = message + "https://play.google.com/store/apps/details?id=com.android.chrome"/* + this.getPackageName()*/;
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.nav_rate:
                Uri uri = Uri.parse("market://details?id=com.android.chrome"/* + this.getPackageName()*/);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.android.chrome"/* + this.getPackageName()*/)));
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void doAnimation() {
        drawer.setVisibility(View.INVISIBLE);

        ViewTreeObserver viewTreeObserver = drawer.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int cx = drawer.getWidth() / 2;
                    int cy = drawer.getHeight() / 2;

                    float finalRadius = Math.max(drawer.getWidth(), drawer.getHeight());

                    // create the animator for this view (the start radius is zero)
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(drawer, cx, cy, 0, finalRadius);
                    circularReveal.setDuration(1500).addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            SplashScreen.activity.finish();
                        }
                    });

                    // make the view visible and start the animation
                    drawer.setVisibility(View.VISIBLE);
                    circularReveal.start();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        drawer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        drawer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }
}
