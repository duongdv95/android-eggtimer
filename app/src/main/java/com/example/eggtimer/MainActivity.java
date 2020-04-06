package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int currentTimer = 1*30; // 1*30 = 30 seconds
    TextView timerView;
    SeekBar seekBar;
    Boolean isTimerRunning = false;
    CountDownTimer countDownTimer;
    Button timerButton;
    MediaPlayer mediaPlayer;

    public String buttonText() {
        return (isTimerRunning) ? "Stop" : "Start";
    }

    public void resetTimer() {
        seekBar.setEnabled(true);
        isTimerRunning = false;
        currentTimer = 1*30;
        timerButton.setText(buttonText());
        setTimerView();
        seekBar.setProgress(1);
    }

    public void toggleTimer(View view) {
        if(isTimerRunning) {
            resetTimer();
            countDownTimer.cancel();
            return;
        }
        isTimerRunning = true;
        timerButton.setText(buttonText());
        seekBar.setEnabled(false);

        countDownTimer = new CountDownTimer(currentTimer*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTimer--;
                setTimerView();
            }

            @Override
            public void onFinish() {
                mediaPlayer.start();
                resetTimer();
            }
        }.start();
    }

    public void setTimerView() {
        int remainder = currentTimer%60;
        String appendZero = (remainder < 10) ? "0" : "";
        String seconds = (remainder == 0) ? "00" : appendZero + remainder;
        timerView.setText(currentTimer/60 + ":" + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarmsound);

        timerButton =(Button) findViewById(R.id.timerButton);
        timerButton.setText(buttonText());

        timerView = (TextView) findViewById(R.id.timerView);
        setTimerView();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(20); // 20 * 30 = 600 seconds, increment by 30
        seekBar.setProgress(currentTimer/30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTimer = progress*30;
                setTimerView();
                Log.i("progress",progress*30 +" seconds");
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
