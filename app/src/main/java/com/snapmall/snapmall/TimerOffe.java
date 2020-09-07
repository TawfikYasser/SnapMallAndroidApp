package com.snapmall.snapmall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.snapmall.snapmall.R;
import com.snapmall.snapmall.Test;

import java.util.concurrent.TimeUnit;

public class TimerOffe {
    Context mcontext ;
    TextView textView;
    String txt;
    public TimerOffe(Context mcontext ,
            TextView textView2
            ,String textView) {
        this.mcontext = mcontext;
        this.txt = textView ;
        this.textView = textView2;
        startStop();
        setTimerValues();
        startCountDownTimer();

    }

    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private CountDownTimer countDownTimer;


    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        } else {


            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }

    private void setTimerValues() {
        int time = 0;

        time = Integer.parseInt(txt);

        timeCountInMilliSeconds = time * 3600 * 1000 ;
    }
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

               textView.setText(hmsTimeFormatter(millisUntilFinished));

            }

            @Override
            public void onFinish() {

                timerStatus = TimerStatus.STOPPED;
            }

        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }



    private String hmsTimeFormatter(long milliSeconds) {

        @SuppressLint("DefaultLocale") String hms = String.format("%03d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

}
