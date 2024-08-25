package com.example.gerenciadetarefas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {

    private Paint progressPaint;
    private Paint backgroundPaint;
    private int progress = 0;
    private int max = 100;
    private RectF rectF;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setColor(getResources().getColor(R.color.teal_700)); // Cor do progresso
        progressPaint.setStrokeWidth(40); // Aumentando a espessura para 40
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.teal_200)); // Cor do fundo
        backgroundPaint.setStrokeWidth(40); // Aumentando a espessura para 40
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setAntiAlias(true);

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 40; // Ajustando o raio para considerar a nova espessura

        rectF.set(40, 40, width - 40, height - 40);

        // Desenhar o círculo de fundo
        canvas.drawArc(rectF, -90, 360, false, backgroundPaint);

        // Desenhar o progresso
        float angle = 360 * progress / max;
        canvas.drawArc(rectF, -90, angle, false, progressPaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Força o redesenho da View
    }

    public void setMax(int max) {
        this.max = max;
    }

}