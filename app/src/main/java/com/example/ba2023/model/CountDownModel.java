package com.example.ba2023.model;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CountDownModel  extends CountDownTimer {

    private static String hms;
    private static CountDownModel instance;
    private static TextView currentTextView;

    private CountDownModel(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        hms = formatTime(millisInFuture);
    }

    public static CountDownModel initInstance(long millisInFuture, long countDownInterval) {
        if (instance == null) {
            instance = new CountDownModel(millisInFuture, countDownInterval);
        }
        return instance;
    }

    public static CountDownModel getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Parameters not initialized. Initiate with initInstance");
        } else {
            return instance;
        }
    }

    public static String getFormatedTime() {
        return hms;
    }
    public static String getTimeMS() {
        return hms.substring(3);
    }

    public static TextView getCurrentTextView() {
        return currentTextView;
    }

    public static void setCurrentTextView(TextView currentTextView) {
        CountDownModel.currentTextView = currentTextView;
    }

    private String formatTime(long millis) {
        String format = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return format;
    }
    @Override
    public void onTick(long l) {

        long millis = l;
        hms = formatTime(millis);
        if (currentTextView != null) {
            currentTextView.setText(getTimeMS());
        }
    }

    @Override
    public void onFinish() {

    }
}