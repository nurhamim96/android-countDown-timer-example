package com.tutorial.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private Boolean counterIsActive = false;
    private Button controllerButton;
    CountDownTimer countDownTimer;

    public void resetTimer () {
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Start");
        controllerButton.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round));
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondLeft) {
        int minutes =  (int) secondLeft / 60;
        int seconds = secondLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(minutes + ":" + secondString);
        System.out.println("SEcond String " + secondString);
    }

    public void controlTimer(View view) {

        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            controllerButton.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_red));
            countDownTimer =    new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekbar);
        timerTextView = findViewById(R.id.timerTextView);
        controllerButton = findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}