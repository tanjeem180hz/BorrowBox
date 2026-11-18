# BorrowBox — Peer-to-Peer University Product Sharing & Rental System

BorrowBox is a native Android application built in **Java** (API 24–35) for university students in Bangladesh (currency: **৳ BDT**). Instead of purchasing expensive equipment (laptop chargers, scientific calculators, power banks, DSLR cameras) for one-off exams or lab projects, students can discover, request, and borrow gear from verified peers nearby within minutes.

---

## 🏛️ Course & Academic Alignment
* **Course:** CSE222 — Object Oriented Programming Lab
* **Institution:** Daffodil International University (DIU), Daffodil Smart City (DSC)
* **Design Philosophy:** Demonstrates real-world Object-Oriented Programming (OOP) paradigms:
  * **Encapsulation:** Clean entity access modifiers, immutable DTOs, and private fields with public accessors.
  * **Inheritance:** Base `User` class inherited by `Student` with specialized university attributes.
  * **Abstraction & Polymorphism:** `PricingStrategy` (`HourlyPricing`, `DailyPricing`), `LateFeePolicy` (`DefaultLateFeePolicy`), and repository interfaces (`UserRepository`, `ProductRepository`, `RentalRepository`).
  * **Design Patterns:** Factory Method (`RepositoryProvider`), Strategy Pattern, State Pattern (`RentalStateMachine`), Observer Pattern (LiveData).

---

## 🚀 Key Features

1. **Authentication & Session:**
   * Salted `SHA-256` password hashing with securely generated cryptographic salts.
   * Persistent student session using `SharedPreferences`.
   * Onboarding walkthrough demonstrating campus peer sharing.
2. **Product Catalog & Live Search:**
   * Live filtering and debounced search as you type.
   * Categories: *Electronics, Study, Accessories, Camera, Emergency*.
   * Pre-seeded with 12 real campus products owned by students with realistic Trust Scores.
3. **Interactive Duration Selector:**
   * Chips for 1h, 2h, 3h, and 1 Day with animated total price computation.
4. **Campus Handover Map (OpenStreetMap / osmdroid):**
   * Uses OpenStreetMap via `osmdroid` — **Zero external API keys required**.
   * Live distance HUD (e.g. `📍 350 m away`) and meeting point route polyline.
   * Step-by-step meeting simulator button (500m → 350m → 180m → 50m) for emulator demo.
5. **QR Handover Verification:**
   * ZXing `QRCodeWriter` generates verification QR code with single-use token.
   * ZXing camera scanner + emulator-friendly "Simulate Scan" fallback button.
6. **Active Rental Timer & Overdue Policy:**
   * Persistent countdown timer derived directly from end timestamps (`HH:MM:SS`).
   * Circular progress ring smoothly visualizing remaining duration.
   * Late fee engine calculating **৳10 per started 20 minutes** overdue.
7. **Return & Condition Comparison:**
   * Interactive draggable comparison slider comparing Before vs. After condition photos.
   * Checklist inspection before return confirmation.
8. **Simulated Payment & Escrow:**
   * Settle rental cost + refundable security deposit.
   * Simulated Cash, bKash (Mock), and Nagad (Mock) options.
9. **Student Trust Score Engine:**
   * Bounded 0–100 algorithm factoring rating average, on-time returns, and verification bonus.
   * Animates Trust Score (e.g. 92 → 94) upon successful 5-star review.
10. **Emergency Urgent Request:**
    * Floating action button for last-minute exam emergencies.
    * Instant high-priority dispatch sorted by proximity.
11. **Demo Switcher:**
    * Easily switch between student personas (Borrower vs. Owner) on a single device.

---

## 🏗️ Architecture Overview

```text
com.borrowbox.app
├── BorrowBoxApp.java            (Application: osmdroid config, notification channels)
├── MainActivity.java            (Single activity with NavHost & BottomNavigation)
├── data
│   ├── local                    (Room AppDatabase, DAOs, Entities, SeedData, SessionManager)
│   └── repository               (Repository interfaces + Local* implementations + RepositoryProvider)
├── model                        (User, Student, Product, Rental, Rating, Payment, Enums)
├── domain                       (PricingStrategy, LateFeePolicy, TrustScoreEngine, RentalStateMachine)
├── ui
│   ├── splash, onboarding, auth (Login, Register, Splash, Onboarding)
│   ├── home, product, emergency (Discover, Details, Add/Edit, Emergency)
│   ├── confirmed, map, handover (Rental Confirmed, osmdroid Map, QR Handover)
│   ├── timer, returnflow        (Active Countdown Timer, Return Comparison Slider)
│   ├── payment, rating          (Simulated Settlement, 5-Star Rating & Trust Engine)
│   ├── rentals, profile, settings (My Rentals tabs, Student Profile, Settings)
│   └── common                   (CircularProgressView, ComparisonSliderView, AnimatedCounter)
└── util                         (CurrencyUtils, DateUtils, SecurityUtils, QrUtils, AppExecutors)
```

---

## 🛠️ How to Build and Run

1. Open **Android Studio**.
2. Select **Open an Existing Project** and browse to `e:\GitHubProject\BorrowBox\test`.
3. Allow Gradle to sync.
4. Run on an emulator (Pixel 7 / API 24–35) or physical Android device:
   ```bash
   ./gradlew assembleDebug
   ```
5. Run unit tests:
   ```bash
   ./gradlew testDebugUnitTest
   ```
