package com.tamer.raed.analogclock;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView imgHour, imgMinute, imgSecond;
    ValueAnimator secondsAnimation, minutesAnimation, hoursAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAnimations();

        initClock();

        // create handler
        MyHandler myHandler = new MyHandler();

        // send message
        Message message = Message.obtain(myHandler);
        myHandler.sendMessage(message);

        // get the time when the screen rotate
        if (savedInstanceState != null) {
            secondsAnimation.setCurrentPlayTime(savedInstanceState.getLong("seconds"));
            minutesAnimation.setCurrentPlayTime(savedInstanceState.getLong("minutes"));
            hoursAnimation.setCurrentPlayTime(savedInstanceState.getLong("hours"));

        }

    }

    private void initAnimations() {
        // inflate all animations
        secondsAnimation = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.seconds_rotate_animation);
        minutesAnimation = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.minutes_rotate_animation);
        hoursAnimation = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.hours_rotate_animation);

        // set the value of animations
        secondsAnimation.addUpdateListener(valueAnimator -> imgSecond.setRotation((Float) valueAnimator.getAnimatedValue()));

        minutesAnimation.addUpdateListener(valueAnimator -> imgMinute.setRotation((Float) valueAnimator.getAnimatedValue()));

        hoursAnimation.addUpdateListener(valueAnimator -> imgHour.setRotation((Float) valueAnimator.getAnimatedValue()));
    }

    private void initClock() {
        // inflate views
        imgHour = findViewById(R.id.img_hour);
        imgMinute = findViewById(R.id.img_minute);
        imgSecond = findViewById(R.id.img_second);

        // get current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // set current time
        secondsAnimation.setCurrentPlayTime(second * 1000);
        minutesAnimation.setCurrentPlayTime(minute * 60 * 1000);
        hoursAnimation.setCurrentPlayTime(hour * 60 * 60 * 1000);
    }

    // save the time on screen rotate
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("seconds", secondsAnimation.getCurrentPlayTime());
        outState.putLong("minutes", minutesAnimation.getCurrentPlayTime());
        outState.putLong("hours", hoursAnimation.getCurrentPlayTime());
    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            secondsAnimation.start();
            minutesAnimation.start();
            hoursAnimation.start();
        }
    }
}