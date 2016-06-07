package com.pptips.uianimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pptips.uianimation.view.SunView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SunView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = (SunView) findViewById(com.pptips.uianimation.R.id.mv_view);

        myView.setOnClickListener(this);
    }

    private int mProgress = 0;
    float degree = 0;
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case com.pptips.uianimation.R.id.mv_view:
                degree += 0.1;
                animate(degree);
                break;
        }
    }


    private void animate(final float progress){

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(myView,"progress",progress);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                myView.setProgress(progress);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator sa = ObjectAnimator.ofFloat(myView,"Scalee",0,2,0);
        sa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                myView.setScalee(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(objectAnimator).with(sa);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(3000);
        animatorSet.start();
    }
}
