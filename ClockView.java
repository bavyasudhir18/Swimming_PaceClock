package com.example.swimmingpaceclock;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View {

    private Paint circlePaint, borderPaint, tickPaint, tickMajPaint,
            handPaint, secHandPaint, arcPaint, textPaint,
            dotPaint, centerPaint;
    private float seconds = 0, minutes = 0, intervalProgress = 0;

    // Theme colors
    private int faceColor      = Color.parseColor("#1A2E42");
    private int borderColor    = Color.parseColor("#378ADD");
    private int tickColor      = Color.parseColor("#5A9FD4");
    private int tickMajColor   = Color.WHITE;
    private int handColor      = Color.WHITE;
    private int secHandColor   = Color.parseColor("#00CFFF");
    private int arcColor       = Color.parseColor("#2288DD");
    private int textColor      = Color.WHITE;
    private int dotColor       = Color.parseColor("#00CFFF");
    private int centerColor    = Color.parseColor("#0D1B2A");

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public void setDarkMode(boolean dark) {
        if (dark) {
            // Green & Black theme
            faceColor    = Color.parseColor("#001A00");
            borderColor  = Color.parseColor("#00CC44");
            tickColor    = Color.parseColor("#006622");
            tickMajColor = Color.parseColor("#00FF55");
            handColor    = Color.parseColor("#00FF55");
            secHandColor = Color.parseColor("#AAFF00");
            arcColor     = Color.parseColor("#00AA33");
            textColor    = Color.parseColor("#00FF55");
            dotColor     = Color.parseColor("#AAFF00");
            centerColor  = Color.parseColor("#001A00");
        } else {
            // Blue swim theme
            faceColor    = Color.parseColor("#1A2E42");
            borderColor  = Color.parseColor("#378ADD");
            tickColor    = Color.parseColor("#5A9FD4");
            tickMajColor = Color.WHITE;
            handColor    = Color.WHITE;
            secHandColor = Color.parseColor("#00CFFF");
            arcColor     = Color.parseColor("#2288DD");
            textColor    = Color.WHITE;
            dotColor     = Color.parseColor("#00CFFF");
            centerColor  = Color.parseColor("#0D1B2A");
        }
        applyColors();
        invalidate();
    }

    private void initPaints() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickMajPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handPaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
        secHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5f);
        circlePaint.setStyle(Paint.Style.FILL);
        tickPaint.setStrokeWidth(2.5f);
        tickMajPaint.setStrokeWidth(6f);
        handPaint.setStrokeWidth(22f);
        handPaint.setStrokeCap(Paint.Cap.ROUND);
        secHandPaint.setStrokeWidth(12f);
        secHandPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(18f);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        applyColors();
    }

    private void applyColors() {
        circlePaint.setColor(faceColor);
        borderPaint.setColor(borderColor);
        tickPaint.setColor(tickColor);
        tickMajPaint.setColor(tickMajColor);
        handPaint.setColor(handColor);
        secHandPaint.setColor(secHandColor);
        arcPaint.setColor(arcColor);
        textPaint.setColor(textColor);
        dotPaint.setColor(dotColor);
        centerPaint.setColor(centerColor);
    }

    public void setTime(float seconds, float minutes, float intervalProgress) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.intervalProgress = intervalProgress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float r  = Math.min(cx, cy) - 12;

        // Face
        canvas.drawCircle(cx, cy, r, circlePaint);
        canvas.drawCircle(cx, cy, r, borderPaint);

        // Interval arc
        RectF arcRect = new RectF(cx - r + 14, cy - r + 14, cx + r - 14, cy + r - 14);
        canvas.drawArc(arcRect, -90, intervalProgress * 360, false, arcPaint);

        // Ticks
        for (int i = 0; i < 60; i++) {
            double angle = Math.toRadians(i * 6 - 90);
            boolean isMaj = i % 5 == 0;
            float inner = isMaj ? r - 32 : r - 16;
            float x1 = (float)(cx + inner * Math.cos(angle));
            float y1 = (float)(cy + inner * Math.sin(angle));
            float x2 = (float)(cx + (r - 6) * Math.cos(angle));
            float y2 = (float)(cy + (r - 6) * Math.sin(angle));
            canvas.drawLine(x1, y1, x2, y2, isMaj ? tickMajPaint : tickPaint);
        }

        // Number labels
        int[] labels = {5,10,15,20,25,30,35,40,45,50,55,60};
        for (int label : labels) {
            double a  = Math.toRadians(label * 6 - 90);
            float tx  = (float)(cx + (r - 54) * Math.cos(a));
            float ty  = (float)(cy + (r - 54) * Math.sin(a)) + 11f;
            canvas.drawText(String.valueOf(label), tx, ty, textPaint);
        }

        // Minute hand — thick and wide
        double minAngle = Math.toRadians(minutes * 6 - 90);
        canvas.drawLine(cx, cy,
                (float)(cx + r * 0.62f * Math.cos(minAngle)),
                (float)(cy + r * 0.62f * Math.sin(minAngle)), handPaint);

        // Second hand with tail
        double secAngle = Math.toRadians(seconds * 6 - 90);
        canvas.drawLine(
                (float)(cx - r * 0.22f * Math.cos(secAngle)),
                (float)(cy - r * 0.22f * Math.sin(secAngle)),
                (float)(cx + r * 0.88f * Math.cos(secAngle)),
                (float)(cy + r * 0.88f * Math.sin(secAngle)),
                secHandPaint);

        // Center dot
        canvas.drawCircle(cx, cy, 18f, dotPaint);
        canvas.drawCircle(cx, cy, 9f,  centerPaint);
    }
}