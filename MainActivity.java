package com.example.swimmingpaceclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Views
    ClockView clockView;
    TextView tvElapsed, tvLapCount, tvPace, tvInterval,
            tvReps, tvRepCounter, tvTitle;
    EditText etWorkoutName;
    Spinner spinnerPool;
    Button btnStart, btnLap, btnReset, btnPlus, btnMinus,
            btnRepPlus, btnRepMinus, btnSave, btnHistory,
            btnTheme, btnFlashColor;
    LinearLayout lapLog;
    View flashOverlay, rootLayout;

    // State
    Handler handler = new Handler();
    boolean running = false;
    boolean isDarkMode = false;
    long startTime = 0, lapStartTime = 0, pausedElapsed = 0;
    int lapCount = 0, intervalSec = 120, poolLen = 25;
    int targetReps = 4, completedReps = 0;
    float lastIntervalProgress = 0f;
    int flashColor = Color.parseColor("#00FF88"); // default green

    ArrayList<Long> lapTimes = new ArrayList<>();
    ArrayList<String> lapStrings = new ArrayList<>();
    SharedPreferences prefs;

    // Flash color options
    String[] colorNames = {"Green","Cyan","Yellow","Orange","Pink","Red","White"};
    int[] colorValues = {
            Color.parseColor("#00FF88"),
            Color.parseColor("#00FFFF"),
            Color.parseColor("#FFFF00"),
            Color.parseColor("#FF8800"),
            Color.parseColor("#FF44CC"),
            Color.parseColor("#FF2222"),
            Color.parseColor("#FFFFFF")
    };

    Runnable ticker = new Runnable() {
        @Override
        public void run() {
            if (!running) return;
            long elapsed   = pausedElapsed + (System.currentTimeMillis() - startTime);
            float totalSec = elapsed / 1000f;
            float secInMin = totalSec % 60;
            float minInMin = (totalSec % 3600) / 60f;
            float intervalProgress = (totalSec % intervalSec) / intervalSec;

            // Detect interval completion
            if (lastIntervalProgress > 0.92f && intervalProgress < 0.08f) {
                onIntervalComplete();
            }
            lastIntervalProgress = intervalProgress;

            clockView.setTime(secInMin, minInMin, intervalProgress);
            tvElapsed.setText(formatTime((long) totalSec));

            if (!lapTimes.isEmpty()) {
                float totalDist = lapTimes.size() * poolLen;
                float pace = (totalSec / totalDist) * 100;
                tvPace.setText("Pace: " + formatTime((long) pace) + "/100m");
            }
            handler.postDelayed(this, 50);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("workouts", Context.MODE_PRIVATE);

        clockView      = findViewById(R.id.clockView);
        tvElapsed      = findViewById(R.id.tvElapsed);
        tvLapCount     = findViewById(R.id.tvLapCount);
        tvPace         = findViewById(R.id.tvPace);
        tvInterval     = findViewById(R.id.tvInterval);
        tvReps         = findViewById(R.id.tvReps);
        tvRepCounter   = findViewById(R.id.tvRepCounter);
        tvTitle        = findViewById(R.id.tvTitle);
        etWorkoutName  = findViewById(R.id.etWorkoutName);
        etWorkoutName.setHintTextColor(Color.parseColor("#446688"));
        spinnerPool    = findViewById(R.id.spinnerPool);
        btnStart       = findViewById(R.id.btnStart);
        btnLap         = findViewById(R.id.btnLap);
        btnReset       = findViewById(R.id.btnReset);
        btnSave        = findViewById(R.id.btnSave);
        btnHistory     = findViewById(R.id.btnHistory);
        btnTheme       = findViewById(R.id.btnTheme);
        btnFlashColor  = findViewById(R.id.btnFlashColor);
        btnPlus        = findViewById(R.id.btnPlus);
        btnMinus       = findViewById(R.id.btnMinus);
        btnRepPlus     = findViewById(R.id.btnRepPlus);
        btnRepMinus    = findViewById(R.id.btnRepMinus);
        lapLog         = findViewById(R.id.lapLog);
        flashOverlay   = findViewById(R.id.flashOverlay);
        rootLayout     = findViewById(R.id.rootLayout);

        tvInterval.setText(intervalSec + "s");
        updateRepCounter();
        applyTheme();

        // Pool spinner
        String[] poolOptions = {"25", "50", "33"};
        ArrayAdapter<String> poolAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, poolOptions);
        poolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPool.setAdapter(poolAdapter);
        spinnerPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                poolLen = Integer.parseInt(poolOptions[pos]);
                Toast.makeText(MainActivity.this,
                        "Pool: " + poolLen + "m", Toast.LENGTH_SHORT).show();
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        // Interval
        btnMinus.setOnClickListener(v -> {
            intervalSec = Math.max(intervalSec - 5, 10);
            tvInterval.setText(intervalSec + "s");
            Toast.makeText(this, "Interval: " + intervalSec + "s", Toast.LENGTH_SHORT).show();
        });
        btnPlus.setOnClickListener(v -> {
            intervalSec = Math.min(intervalSec + 5, 600);
            tvInterval.setText(intervalSec + "s");
            Toast.makeText(this, "Interval: " + intervalSec + "s", Toast.LENGTH_SHORT).show();
        });

        // Reps
        btnRepMinus.setOnClickListener(v -> {
            if (targetReps > 1) {
                targetReps--;
                tvReps.setText(String.valueOf(targetReps));
                updateRepCounter();
            }
        });
        btnRepPlus.setOnClickListener(v -> {
            targetReps++;
            tvReps.setText(String.valueOf(targetReps));
            updateRepCounter();
        });

        // Theme toggle
        btnTheme.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            applyTheme();
        });

        // Flash color picker
        btnFlashColor.setOnClickListener(v -> showColorPicker());

        // Start / Pause
        btnStart.setOnClickListener(v -> {
            if (!running) {
                running = true;
                startTime = System.currentTimeMillis();
                if (lapStartTime == 0) lapStartTime = startTime;
                btnStart.setText("PAUSE");
                btnStart.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor("#8B6914")));
                handler.post(ticker);
            } else {
                running = false;
                pausedElapsed += System.currentTimeMillis() - startTime;
                btnStart.setText("RESUME");
                btnStart.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor("#185FA5")));
            }
        });

        // Lap
        btnLap.setOnClickListener(v -> {
            if (!running) return;
            long now     = System.currentTimeMillis();
            long lapTime = now - lapStartTime;
            lapStartTime = now;
            lapCount++;
            lapTimes.add(lapTime);
            String row = "Lap " + lapCount + " — "
                    + formatTime(lapTime / 1000) + " — " + poolLen + "m";
            lapStrings.add(row);
            tvLapCount.setText("Laps: " + lapCount);
            addLapRow(lapCount, lapTime);
            flashTop();
        });

        // Save
        btnSave.setOnClickListener(v -> saveWorkout());

        // History
        btnHistory.setOnClickListener(v -> showHistory());

        // Reset
        btnReset.setOnClickListener(v -> {
            running = false;
            handler.removeCallbacks(ticker);
            pausedElapsed = 0; lapCount = 0;
            lapStartTime = 0; completedReps = 0;
            lastIntervalProgress = 0f;
            lapTimes.clear(); lapStrings.clear();
            lapLog.removeAllViews();
            clockView.setTime(0, 0, 0);
            tvElapsed.setText("0:00");
            tvLapCount.setText("Laps: 0");
            tvPace.setText("Pace: —");
            btnStart.setText("START");
            btnStart.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#185FA5")));
            updateRepCounter();
        });
    }

    // Called every time interval completes
    private void onIntervalComplete() {
        completedReps++;
        runOnUiThread(() -> {
            updateRepCounter();
            flashTop();
            Toast.makeText(this,
                    "✅ Rep " + completedReps + " / " + targetReps + " done!",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void updateRepCounter() {
        tvRepCounter.setText("Rep: " + completedReps + " / " + targetReps);
        if (completedReps >= targetReps) {
            tvRepCounter.setTextColor(Color.parseColor("#FFD700")); // gold = done
        } else {
            tvRepCounter.setTextColor(isDarkMode
                    ? Color.parseColor("#00FF55")
                    : Color.parseColor("#00FF88"));
        }
    }

    // Flash at TOP of screen with chosen color
    private void flashTop() {
        runOnUiThread(() -> {
            flashOverlay.setBackgroundColor(flashColor);
            flashOverlay.setVisibility(View.VISIBLE);
            flashOverlay.setAlpha(0.80f);
            flashOverlay.animate()
                    .alpha(0f)
                    .setDuration(900)
                    .withEndAction(() -> flashOverlay.setVisibility(View.GONE))
                    .start();
        });
    }

    // Color picker dialog
    private void showColorPicker() {
        new AlertDialog.Builder(this)
                .setTitle("Choose Flash Color")
                .setItems(colorNames, (dialog, which) -> {
                    flashColor = colorValues[which];
                    Toast.makeText(this,
                            colorNames[which] + " selected", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    // Apply light/dark theme
    private void applyTheme() {
        if (isDarkMode) {
            // Green & Black
            rootLayout.setBackgroundColor(Color.parseColor("#000A00"));
            tvTitle.setTextColor(Color.parseColor("#00FF55"));
            tvElapsed.setTextColor(Color.parseColor("#00FF55"));
            tvLapCount.setTextColor(Color.parseColor("#00CC44"));
            tvPace.setTextColor(Color.parseColor("#AAFF00"));
            tvInterval.setTextColor(Color.parseColor("#00FF55"));
            tvRepCounter.setTextColor(Color.parseColor("#00FF55"));
            btnTheme.setText("☀️ Light");
            clockView.setDarkMode(true);
        } else {
            // Blue swim theme
            rootLayout.setBackgroundColor(Color.parseColor("#0D1B2A"));
            tvTitle.setTextColor(Color.parseColor("#B5D4F4"));
            tvElapsed.setTextColor(Color.WHITE);
            tvLapCount.setTextColor(Color.parseColor("#B5D4F4"));
            tvPace.setTextColor(Color.parseColor("#9FE1CB"));
            tvInterval.setTextColor(Color.WHITE);
            tvRepCounter.setTextColor(Color.parseColor("#00FF88"));
            btnTheme.setText("🌙 Dark");
            clockView.setDarkMode(false);
        }
    }

    private void saveWorkout() {
        if (lapStrings.isEmpty() && completedReps == 0) {
            Toast.makeText(this, "Nothing to save yet!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String name = etWorkoutName.getText().toString().trim();
            if (name.isEmpty()) name = "Unnamed Workout";
            String date = new SimpleDateFormat("dd MMM yyyy HH:mm",
                    Locale.getDefault()).format(new Date());

            JSONObject workout = new JSONObject();
            workout.put("name", name);
            workout.put("date", date);
            workout.put("reps", completedReps + "/" + targetReps);
            workout.put("interval", intervalSec + "s");
            workout.put("pool", poolLen + "m");
            JSONArray lapsArr = new JSONArray();
            for (String s : lapStrings) lapsArr.put(s);
            workout.put("laps", lapsArr);

            String existing = prefs.getString("history", "[]");
            JSONArray history = new JSONArray(existing);
            history.put(workout);
            prefs.edit().putString("history", history.toString()).apply();
            Toast.makeText(this, "✅ Workout saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Save failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showHistory() {
        try {
            String existing = prefs.getString("history", "[]");
            JSONArray history = new JSONArray(existing);
            if (history.length() == 0) {
                Toast.makeText(this, "No saved workouts yet", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = history.length() - 1; i >= 0; i--) {
                JSONObject w = history.getJSONObject(i);
                sb.append("━━━━━━━━━━━━━━━━━\n");
                sb.append("📅 ").append(w.getString("date")).append("\n");
                sb.append("🏊 ").append(w.getString("name")).append("\n");
                sb.append("✅ Reps: ").append(w.getString("reps")).append("\n");
                sb.append("⏱ Interval: ").append(w.getString("interval")).append("\n");
                sb.append("📏 Pool: ").append(w.getString("pool")).append("\n");
                JSONArray laps = w.getJSONArray("laps");
                for (int j = 0; j < laps.length(); j++)
                    sb.append("  • ").append(laps.getString(j)).append("\n");
                sb.append("\n");
            }

            ScrollView sv = new ScrollView(this);
            TextView tv = new TextView(this);
            tv.setText(sb.toString());
            tv.setTextColor(isDarkMode
                    ? Color.parseColor("#00FF55")
                    : Color.parseColor("#B5D4F4"));
            tv.setTextSize(13f);
            tv.setPadding(32, 24, 32, 24);
            tv.setBackgroundColor(isDarkMode
                    ? Color.parseColor("#000A00")
                    : Color.parseColor("#0D1B2A"));
            sv.addView(tv);

            new AlertDialog.Builder(this)
                    .setTitle("Workout History")
                    .setView(sv)
                    .setPositiveButton("Close", null)
                    .setNegativeButton("Clear All", (d, w2) -> {
                        prefs.edit().remove("history").apply();
                        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        } catch (Exception e) {
            Toast.makeText(this, "Error loading history", Toast.LENGTH_SHORT).show();
        }
    }

    void addLapRow(int num, long ms) {
        long sec = ms / 1000;
        TextView tv = new TextView(this);
        tv.setText("  Lap " + num + "   " + formatTime(sec) + "   " + poolLen + "m");
        tv.setTextColor(isDarkMode
                ? Color.parseColor("#00FF55")
                : Color.parseColor("#B5D4F4"));
        tv.setTextSize(15);
        tv.setGravity(Gravity.START);
        tv.setPadding(24, 12, 24, 12);
        tv.setBackgroundColor(num % 2 == 0
                ? (isDarkMode ? Color.parseColor("#001500") : Color.parseColor("#12283D"))
                : (isDarkMode ? Color.parseColor("#000A00") : Color.parseColor("#0D1B2A")));
        lapLog.addView(tv, 0);
    }

    String formatTime(long totalSec) {
        long m = totalSec / 60, s = totalSec % 60;
        return m + ":" + String.format("%02d", s);
    }
}