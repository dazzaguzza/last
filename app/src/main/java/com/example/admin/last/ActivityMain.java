package com.example.admin.last;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.admin.last.bottomnavigatation.CurvedBottomNavigationView;
import com.example.admin.last.databinding.ActivityMainBinding;
import com.example.admin.last.mainfragment.FragmentBroadcastEnd;
import com.example.admin.last.broadcastIngMvp.FragmentBroadcastIng;
import com.example.admin.last.profileMvp.FragmentProfile;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;


public class ActivityMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentBroadcastIng.bus {

    private CurvedBottomNavigationView mView;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private RelativeLayout mlinId;
    PathModel outline;
    private FrameLayout mainFrame;
    Animation slide_down_animation;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mView = findViewById(R.id.customBottomBar);
        heartVector = findViewById(R.id.fab);
        heartVector1 = findViewById(R.id.fab1);
        heartVector2 = findViewById(R.id.fab2);
        mainFrame = findViewById(R.id.mainFrame);

        mlinId = findViewById(R.id.lin_id);
        mView.inflateMenu(R.menu.menu_main);
        mView.setSelectedItemId(R.id.action_ing);
        mView.getMenu().findItem(R.id.action_ing).setIcon(null);

        getSupportFragmentManager().beginTransaction().add(R.id.mainFrame, new FragmentBroadcastIng()).commit();
        //getting bottom navigation view and attaching the listener
        //BottomNavigationView navigation = findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(ActivityMain.this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                tet(6);
                // find the correct path using name
                mlinId.setX(mView.mFirstCurveControlPoint1.x + 6);
                heartVector.setVisibility(View.VISIBLE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.GONE);
                mView.getMenu().findItem(R.id.action_profile).setIcon(null);
                mView.getMenu().findItem(R.id.action_ing).setIcon(R.drawable.ic_switch_video_black_24dp);
                mView.getMenu().findItem(R.id.action_end).setIcon(R.drawable.ic_movie_creation_black_24dp);
                selectAnimation(heartVector);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new FragmentProfile()).commit();
                break;
            case R.id.action_ing:
                tet(2);
                mlinId.setX(mView.mFirstCurveControlPoint1.x + 6);
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.VISIBLE);
                heartVector2.setVisibility(View.GONE);
                mView.getMenu().findItem(R.id.action_profile).setIcon(R.drawable.ic_person_black_24dp);
                mView.getMenu().findItem(R.id.action_ing).setIcon(null);
                mView.getMenu().findItem(R.id.action_end).setIcon(R.drawable.ic_movie_creation_black_24dp);
                selectAnimation(heartVector1);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new FragmentBroadcastIng()).commit();
                break;
            case R.id.action_end:
                tet();
                mlinId.setX(mView.mFirstCurveControlPoint1.x + 6);
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.VISIBLE);
                mView.getMenu().findItem(R.id.action_profile).setIcon(R.drawable.ic_person_black_24dp);
                mView.getMenu().findItem(R.id.action_ing).setIcon(R.drawable.ic_switch_video_black_24dp);
                mView.getMenu().findItem(R.id.action_end).setIcon(null);
                selectAnimation(heartVector2);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new FragmentBroadcastEnd()).commit();
                break;
        }

        return true;
    }


    private void selectAnimation(final VectorMasterView heartVector) {

        outline = heartVector.getPathModelByName("outline");
        outline.setStrokeColor(Color.parseColor("#ffffff"));
        outline.setTrimPathEnd(0.0f);
        // initialise valueAnimator and pass start and end float values
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // set trim end value and update view
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heartVector.update();
            }
        });
        valueAnimator.start();
    }

    private void tet(int i) {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        //mNavigationBarHeight = getHeight();
        //mNavigationBarWidth = getWidth();
        // the coordinates (x,y) of the start point before curve
        mView.mFirstCurveStartPoint.set((mView.mNavigationBarWidth / i) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mView.mFirstCurveEndPoint.set(mView.mNavigationBarWidth / i, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.mSecondCurveStartPoint = mView.mFirstCurveEndPoint;
        mView.mSecondCurveEndPoint.set((mView.mNavigationBarWidth / i) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.mFirstCurveControlPoint1.set(mView.mFirstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.mFirstCurveControlPoint2.set(mView.mFirstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.mFirstCurveEndPoint.y);

        mView.mSecondCurveControlPoint1.set(mView.mSecondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.mSecondCurveStartPoint.y);
        mView.mSecondCurveControlPoint2.set(mView.mSecondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.mSecondCurveEndPoint.y);


    }

    private void tet() {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        //mNavigationBarHeight = getHeight();
        //mNavigationBarWidth = getWidth();
        // the coordinates (x,y) of the start point before curve
        mView.mFirstCurveStartPoint.set((mView.mNavigationBarWidth * 10 / 12) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mView.mFirstCurveEndPoint.set(mView.mNavigationBarWidth * 10 / 12, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.mSecondCurveStartPoint = mView.mFirstCurveEndPoint;
        mView.mSecondCurveEndPoint.set((mView.mNavigationBarWidth * 10 / 12) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.mFirstCurveControlPoint1.set(mView.mFirstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.mFirstCurveControlPoint2.set(mView.mFirstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.mFirstCurveEndPoint.y);

        mView.mSecondCurveControlPoint1.set(mView.mSecondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.mSecondCurveStartPoint.y);
        mView.mSecondCurveControlPoint2.set(mView.mSecondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.mSecondCurveEndPoint.y);
    }

    @Override
    public void bus(RecyclerView recyclerView) {



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && mView.isShown()) {

                    slideDown(mView);
                    slideDown(mlinId);
                    mView.getMenu().findItem(R.id.action_profile).setEnabled(false);
                    mView.getMenu().findItem(R.id.action_ing).setEnabled(false);
                    mView.getMenu().findItem(R.id.action_end).setEnabled(false);


                } else if (dy < 0) {

                    slideUp(mView);
                    slideUp(mlinId);
                    mView.getMenu().findItem(R.id.action_profile).setEnabled(true);
                    mView.getMenu().findItem(R.id.action_ing).setEnabled(true);
                    mView.getMenu().findItem(R.id.action_end).setEnabled(true);
                }
            }
        });
    }

    public void slideDown(final View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                600); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        }, 500);
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                600,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
}
