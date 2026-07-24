# 🏊 Swimming Pace Clock

A professional swimming pace clock Android app built for swimmers,
coaches and swimming clubs. Built using Java and Android Studio.

---

## 📱 About the App

Swimming Pace Clock is a training tool designed to be used on the
pool deck. The large analog clock face with thick visible hands can
be seen clearly from 25 to 50 meters away. The app helps swimmers
track their intervals, count their reps and record their workouts.

No internet needed. No login needed. Just open and swim.

---

## 📸 App Screenshots

### 🏠 Home Screen

The main interface of the Swimming Pace Clock. Users can configure workout details, number of repetitions, interval duration, pool length, and view the large analog pace clock designed for excellent visibility on the pool deck.

<img width="746" height="1600" alt="app_interface_screenshot1" src="https://github.com/user-attachments/assets/ff4d4bb2-1a14-4049-ae98-372c3c7e4c77" />


---

### ⏱️ Active Workout & Lap Tracking

Displays the running pace clock during a workout with the current timer, repetition counter, lap recording, average pace calculation, and customizable interval timer. The interface is optimized for swimmers and coaches to monitor training sessions from a distance.

<img width="746" height="1600" alt="app_interface_screenshot2" src="https://github.com/user-attachments/assets/d82bb03c-6f36-4269-addc-6d9460ad60f7" />



---

### 📜 Workout History

Shows previously saved workouts, including workout names, date and time, lap times, intervals, and completed repetitions. All workout history is stored locally on the device and can be accessed without an internet connection.

<img width="746" height="1600" alt="app_interface_screenshot3" src="https://github.com/user-attachments/assets/1429f006-2671-4326-a351-45fffb158d09" />

---
---

## ✨ Features

### Clock
- 🕐 Large analog pace clock with thick visible second and minute hands
- 🔵 Interval progress arc shown on clock face
- 🔢 All 60 second markers clearly visible
- 👁 Designed to be read from 25 to 50 meters away

### Timer and Intervals
- ⏱ Customizable intervals from 10 seconds to 600 seconds
- ➕ Easy plus and minus buttons to change interval
- 🔄 Interval progress resets automatically each cycle
- 🟢 Flash at TOP of screen every interval completion

### Rep Counter
- 🔢 Set your target reps for example 4 for a 4x100 set
- ✅ Rep counter auto increments every interval
- 🥇 Counter turns gold when all reps are completed
- 📢 Notification shows Rep 1 of 4, Rep 2 of 4 and so on

### Lap Tracking
- 🏁 LAP button records each length with split time
- 📊 Average pace per 100m calculated automatically
- 📋 Full lap log shown at bottom of screen
- 🔄 Most recent lap shown at top of log

### Flash Alert
- 🟢 Flash appears at TOP of screen every interval
- 🎨 Choose your own flash color
  - Green
  - Cyan
  - Yellow
  - Orange
  - Pink
  - Red
  - White

### Themes
- 🌙 Dark Mode — green and black theme like a scoreboard
- 🌊 Swim Mode — blue ocean theme with swimming background
- 🔄 Toggle between themes with one button

### Workout History
- 💾 Save any workout with a custom name
- 📅 Each workout saved with date and time
- 📋 View full history of all past workouts
- 🗑 Clear all history with one tap
- 📵 All data stored locally on phone — no internet needed

### Pool Settings
- 📏 Pool length selector — 25m, 50m, 33m
- 💦 Water splash resistant controls
- 🔒 Settings only change when deliberately tapped

---

## 🏊 How to Use

### Before you get in the pool

**Step 1 — Enter your workout name**
```
Tap the workout box at the top
Type your set name
Example: 4x100 Freestyle
         8x50 Backstroke
         10x100 IM
```

**Step 2 — Set how many reps**
```
Tap + or − next to Reps
Example: for 4x100 set Reps to 4
         for 8x50  set Reps to 8
```

**Step 3 — Set your interval**
```
Tap + or − next to Interval
Each tap changes by 5 seconds
Example: 2 minutes = 120 seconds
         1 minute  = 60 seconds
         1 min 30  = 90 seconds
```

**Step 4 — Set your pool length**
```
Tap the Pool dropdown
Select 25m or 50m or 33m
```

**Step 5 — Choose your theme (optional)**
```
Tap 🌙 Dark for green and black theme
Tap ☀️ Light for blue swim theme
```

**Step 6 — Choose flash color (optional)**
```
Tap 🎨 button
Select your preferred color
This color flashes at top of screen
every time interval completes
```

---

### When you are ready to swim

**Step 7 — Press START**
```
Press START when you push off the wall
The clock starts running
The interval arc starts filling up on the clock face
```

**Step 8 — Watch the clock**
```
Blue arc on clock shows interval progress
When arc completes one full circle:
  → Colored flash appears at TOP of screen
  → Rep counter increases by 1
  → Notification shows Rep 2 of 4 done!
This is your signal to push off for next rep
```

**Step 9 — Press LAP after each length**
```
Every time you touch the wall
press the LAP button
This records your split time for that length

Example lap log:
  Lap 1 → 1:32 → 25m
  Lap 2 → 1:28 → 25m
  Lap 3 → 1:35 → 25m
  Lap 4 → 1:30 → 25m
```

**Step 10 — Watch your rep counter**
```
Top of screen shows:
  Rep: 1 / 4  → completed 1 out of 4 reps
  Rep: 2 / 4  → completed 2 out of 4 reps
  Rep: 3 / 4  → completed 3 out of 4 reps
  Rep: 4 / 4  → ALL reps done — turns gold 🥇
```

---

### After your workout

**Step 11 — Press SAVE**
```
Tap SAVE button
Your workout is saved with:
  → Date and time
  → Workout name
  → Reps completed
  → Interval used
  → All lap times
```

**Step 12 — View your history**
```
Tap HISTORY button
See all your past workouts listed
Most recent workout shown first
Tap Clear All to delete all history
```

**Step 13 — Reset for next set**
```
Tap RESET to clear everything
Set up your next workout
Press START again
```

---

### Full Example — 4x100 Freestyle on 2:00

```
Setup:
  Workout name  → 4x100 Freestyle
  Reps          → 4
  Interval      → 120s (2 minutes)
  Pool          → 50m

Press START when you push off the wall

Swim 100m (2 lengths of 50m pool)
Press LAP after each 50m length

At 2:00 → flash at top of screen
         → Rep: 1 / 4
         → push off for rep 2

At 4:00 → flash at top of screen
         → Rep: 2 / 4
         → push off for rep 3

At 6:00 → flash at top of screen
         → Rep: 3 / 4
         → push off for rep 4

At 8:00 → flash at top of screen
         → Rep: 4 / 4 🥇 ALL DONE!

Press SAVE → workout saved with all splits!
```

---

### More Example Workouts

| Set | Interval | Reps | Pool |
|---|---|---|---|
| 4x100 Freestyle | 2:00 (120s) | 4 | 50m |
| 8x50 Backstroke | 1:00 (60s) | 8 | 25m |
| 10x100 IM | 2:30 (150s) | 10 | 50m |
| 16x25 Sprint | 0:30 (30s) | 16 | 25m |
| 4x200 Freestyle | 3:00 (180s) | 4 | 50m |
| 6x50 Breaststroke | 1:30 (90s) | 6 | 25m |
| 5x400 Endurance | 6:00 (360s) | 5 | 50m |

---

## 🛠 Tech Stack

| Item | Detail |
|---|---|
| Language | Java |
| Platform | Android |
| Minimum SDK | API 24 — Android 7.0 and above |
| Built with | Android Studio |
| Storage | SharedPreferences — saved locally on phone |
| Internet | Not required at all |
| Permissions | None required |

---

## 📂 Project Structure
```
app/src/main/
├── java/com/example/swimmingpaceclock/
│   ├── MainActivity.java
│   │   — Timer and interval logic
│   │   — Rep counter and auto increment
│   │   — Flash alerts at top of screen
│   │   — Theme switching dark and light
│   │   — Save and load workout history
│   │   — Lap tracking and pace calculator
│   │   — Pool length and interval settings
│   │
│   └── ClockView.java
│       — Custom analog clock drawing
│       — Thick second and minute hands
│       — Interval progress arc
│       — Tick marks and number labels
│       — Dark and light theme colors
│
└── res/
    ├── layout/
    │   └── activity_main.xml
    │       — Full screen layout
    │       — Flash overlay fixed at top
    │       — Scrollable lap log
    │       — All buttons and controls
    │
    ├── drawable/
    │   └── swim_image.jpg
    │       — Swimming pool background image
    │
    └── values/
        └── strings.xml
            — App name
```

---

## 📲 How to Install

### Option 1 — Direct APK install (easiest)

1. Go to Releases section on this GitHub page
2. Download app-release.apk
3. Transfer to your Android phone
4. Open the APK file on your phone
5. If asked tap Allow install from unknown sources
   Settings → Install unknown apps → Allow
6. Tap Install
7. Open Swimming Pace Clock from your app drawer


### Option 2 — Install via WhatsApp

1. Download app-release.apk from Releases
2. Send it to your phone via WhatsApp
3. Open the file in WhatsApp
4. Tap Install


### Option 3 — Build from source

1. Download or clone this repository
2. Open Android Studio
3. Click Open and select the project folder
4. Wait for Gradle to sync
5. Connect your Android phone via USB
6. Enable USB Debugging on phone
7. Click Run green play button
8. App installs on your phone


## 📋 Requirements

Android version   → 7.0 or higher (API 24+)
Internet          → Not needed
Storage           → Less than 10MB
Permissions       → None required
Screen size       → Works on all phone sizes


## 🏆 Perfect For

✅ Competitive swimmers
✅ Swim coaches standing on pool deck
✅ Triathlon training
✅ Masters swimming clubs
✅ School and college swim teams
✅ Open water swim training
✅ Personal lap tracking
✅ Swimming clubs and squads


## 💡 Tips

💡 Use dark mode outdoors — easier to see in sunlight
💡 Set flash color to Yellow or White for brightest flash
💡 Mount phone on pool deck at eye level for best visibility
💡 Always press SAVE after each set to keep your records
💡 Use LAP button for every single length not just each rep
💡 Interval of 0 seconds means continuous timer mode
💡 History stores unlimited workouts


## 👩‍💻 Built By

Bavya
Tech enthusiast 


## 📌 Version History

| Version | Date | Changes |
|---|---|---|
| v1.0 | June 2026 | First release — full feature app |

---

## 🤝 Contributing

Found a bug or want a new feature?
Open an issue on this GitHub page
or contact me directly


## 📄 License


Free to use for personal and coaching purposes
Swimming Pace Clock — built for the swimming community


*Built with ❤️ for swimmers by a swimmer*
