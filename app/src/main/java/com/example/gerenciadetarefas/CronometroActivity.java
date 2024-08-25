package com.example.gerenciadetarefas;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CronometroActivity extends AppCompatActivity {

    private CircularProgressBar progressBar;
    private TextView cronometro;
    private EditText definirTempo;
    private ImageButton iniciar, reniciar, pause;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long totalTimeInMillis;
    private int touchCount = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);

        progressBar = findViewById(R.id.progressBar);
        cronometro = findViewById(R.id.textView2);
        definirTempo = findViewById(R.id.editTextNumber);
        iniciar = findViewById(R.id.imageButton2);
        reniciar = findViewById(R.id.imageButton3);
        pause = findViewById(R.id.imageButton4);

        View touchArea = findViewById(R.id.touchArea); // Área para detectar os toques

        touchArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchCount++;
                    handler.postDelayed(() -> {
                        if (touchCount == 2) {
                            if (countDownTimer == null) {
                                startTimer();
                            } else {
                                pauseTimer();
                            }
                        } else if (touchCount == 3) {
                            resetTimer();
                        }
                        touchCount = 0;
                    }, 300); // Tempo de espera para considerar a sequência de toques
                }
                return true;
            }
        });

        iniciar.setOnClickListener(v -> startTimer());
        reniciar.setOnClickListener(v -> resetTimer());
        pause.setOnClickListener(v -> pauseTimer());
    }

    private void startTimer() {
        String timeInput = definirTempo.getText().toString();

        if (!timeInput.matches("\\d{2}:\\d{2}")) {
            definirTempo.setError("Formato inválido. Use MM:SS");
            return;
        }

        try {
            String[] timeParts = timeInput.split(":");
            int minutes = Integer.parseInt(timeParts[0]);
            int seconds = Integer.parseInt(timeParts[1]);

            totalTimeInMillis = (minutes * 60 + seconds) * 1000;
            timeLeftInMillis = totalTimeInMillis;

            progressBar.setMax(100);
            progressBar.setProgress(100);

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    int progress = (int) (millisUntilFinished * 100 / totalTimeInMillis);
                    progressBar.setProgress(progress);
                    updateTimerText();
                }

                @Override
                public void onFinish() {
                    cronometro.setText("00:00");
                    progressBar.setProgress(0);
                }
            }.start();
        } catch (NumberFormatException e) {
            definirTempo.setError("Erro ao converter o tempo. Verifique a entrada.");
        }
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        cronometro.setText(timeFormatted);
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = totalTimeInMillis;
        progressBar.setProgress(100);
        updateTimerText();
        countDownTimer = null;
    }
}
