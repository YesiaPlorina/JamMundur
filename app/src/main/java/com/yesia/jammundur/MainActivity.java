package com.yesia.jammundur;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;

    @BindView(R.id.tv_view_countdown)
    TextView tvViewCountdown;
    @BindView(R.id.btn_start_pause)
    Button btnStartPause;
    @BindView(R.id.btn_restart)
    Button btnRestart;

    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start_pause, R.id.btn_restart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_pause:
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                break;
            case R.id.btn_restart:
                restartTimer();
                updateCountDownText();
                break;
        }
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStartPause.setText("Start");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnRestart.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        btnStartPause.setText("pause");
        btnRestart.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {

        countDownTimer.cancel();
        mTimerRunning = false;
        btnStartPause.setText("Start");
        btnRestart.setVisibility(View.VISIBLE);
    }

    private void restartTimer() {

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnRestart.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {

        int minute = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) mTimeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minute, seconds);

        tvViewCountdown.setText(timeLeftFormatted);
    }


}
