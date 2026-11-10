# Engineering Decisions Log - BorrowBox

This document records architectural, technical, and UI/UX decisions made during development.

## Decision 1: Project Location & Isolation
- **Context:** User requested all Android app code, configuration, and documentation be placed strictly inside `test/` so that no file touches the parent root directory.
- **Decision:** Initialized a full standalone Gradle project inside `test/`, including its own Gradle wrapper, `build.gradle`, `app/`, `local.properties`, unit tests, and documentation.

## Decision 2: Pure Java & Java 17 Compatibility
- **Context:** CSE222: Object Oriented Programming Lab requires pure Java.
- **Decision:** Set `sourceCompatibility` and `targetCompatibility` to `JavaVersion.VERSION_17`. No Kotlin plugins or Kotlin runtime dependencies are included.

## Decision 3: Offline-First Architecture & No External API Keys
- **Context:** The app must build and run on first try with zero external setup (no Google Cloud, no Firebase setup required).
- **Decision:** Implemented Room database (SQLite) with pre-seeded demo data, SharedPreferences for session tracking, repository interfaces (`*Repository`) with `Local*Repository` implementations and a `RepositoryProvider` factory.

## Decision 4: OpenStreetMap (osmdroid) & Location Simulation
- **Context:** Master prompt requires osmdroid without Google Maps SDK or API keys.
- **Decision:** Configured `osmdroid` in `BorrowBoxApp` with custom user agent and application-specific cache. Provided GPS via `FusedLocationProviderClient` with a step-by-step meeting simulator (500m -> 350m -> 180m -> 50m) for emulator testing.

## Decision 5: ZXing QR Code Generation & Scanning with Fallback
- **Context:** Real devices can scan with camera, but emulators lack real cameras.
- **Decision:** Used ZXing `QRCodeWriter` to generate QR bitmaps and `ScanContract` for live camera scanning, paired with a "Simulate Scan" button to ensure complete emulator-friendly end-to-end testing.

## Decision 6: Pricing and Late-Fee Policy Formula
- **Context:** Business rules for duration calculations and late fees.
- **Decision:** Standard rates are hourly (`৳20/hour`) or daily (`৳100/day`). Late fee policy charges `৳10 for every started 20 minutes` after the designated return time. Encapsulated via `PricingStrategy` and `LateFeePolicy`.

## Decision 7: Trust Score Engine Algorithm
- **Context:** Bounded 0–100 student trust score.
- **Decision:** Implemented formula: Base 50 + rating points (up to 30) + completed on-time rentals (up to 15) + verified student bonus (+5), minus late penalties and dispute deductions. Tested via JUnit.


## Decision 8: Facebook Marketplace-Style Home Feed Redesign
- **Context:** User requested redesigning the Home screen into a Facebook Marketplace-style visual browsing experience so students are immediately drawn to product photos when borrowing items.
- **Decision:**
  1. Replaced single-column linear layout with a 2-column grid (`GridLayoutManager(context, 2)`).
  2. Created 12 custom, scalable vector illustrations for each campus product (charger, Casio calculator, power bank, DSLR camera, HDMI cable, mouse, drawing tablet, lab coat, umbrella, USB hub, presentation pointer, Bluetooth headset).
  3. Redesigned `item_product.xml` with hero visual card, bold hero pricing (`৳20/hour`, `৳100/day`), compact 2-line title, location/distance badge, and floating status pill overlays (`Available Now`, `⭐ Trust`).
  4. Created `ProductImageHelper.java` to map product categories and names dynamically across the adapter and product details view.


## Decision 9: Removal of Synthetic AI Badges & Universal Back Navigation
- **Context:** User requested removing telltale AI-generated badge styles (e.g. artificial colored "Available Now" and "⭐ Trust" pills stamped onto photo corners) and adding dedicated back buttons across all sub-screens.
- **Decision:**
  1. Removed floating badge stickers from product photos in `item_product.xml`. Product images are now 100% clean and unobstructed, matching authentic human e-commerce marketplace standards.
  2. Implemented subtle, natural typography hierarchy below the photo: bold price, 2-line title, and subtle metadata `Electronics • 350m • ★ 4.6`. Only if an item is actively rented out is a subtle `RENTED` badge shown.
  3. Redesigned `ProductDetailsFragment` with a floating circular back button (`btn_back`) over the hero image, replacing rectangular AI-style status boxes with a minimalist status row (`🟢 Available for borrow • Good condition`), and a human peer card with verified checkmark (`✔`) and circular avatar.
  4. Added dedicated back buttons with `Navigation.findNavController(v).navigateUp()` across `EmergencyFragment`, `AddEditProductFragment`, `SettingsFragment`, `QrHandoverFragment`, `TimerFragment`, `ReturnFragment`, `PaymentFragment`, `RatingFragment`, and `RentalConfirmedFragment`.


## Decision 10: Dynamic Theme Engine & High-Contrast DayNight Typography
- **Context:** Fonts were unreadable in dark mode because text colors referenced `@color/ink` without a matching `values-night/colors.xml` definition, causing dark text to blend into dark backgrounds. User requested full dynamic theme adaptation (automatic device theme sync + manual light/dark switching in settings) with crystal-clear font legibility across both themes.
- **Decision:**
  1. Created `res/values-night/colors.xml` with paired semantic color tokens (`@color/ink` = `#F8FAFC`, `@color/muted` = `#94A3B8`, `@color/surface` = `#1E293B`, `@color/background` = `#0B0F19`, `@color/brand_primary` = `#818CF8`).
  2. Updated `res/values/colors.xml` with high-contrast light theme tokens (`@color/ink` = `#0F172A`, `@color/surface` = `#FFFFFF`, `@color/background` = `#F8FAFC`).
  3. Upgraded `SessionManager.java` to support 3 dynamic theme modes: `THEME_SYSTEM` (Follow device theme), `THEME_LIGHT` (Force light), and `THEME_DARK` (Force dark).
  4. Configured `BorrowBoxApp.java` to apply the dynamic theme mode on application startup.
  5. Implemented a 3-way radio theme selector in `SettingsFragment` allowing instant switching between Automatic Device Theme, Light, and Dark modes.
  6. Verified on live Android emulator that all fonts, cards, badges, and search bars render with high contrast and legibility across both themes.


## Decision 11: Production-Grade UI Professionalization & Elimination of AI/Academic Artifacts
- **Context:** User requested removing all traces of AI-generated template styling, demo labels, and academic jargon (specifically `"Version 1.0.0 (Java OOP Lab Project)"`, emojis in settings labels `📱/☀️/🌙`, `"(Demo Mode)"`, `"(Two-Person Flow Testing)"`, `"(Mock)"`, and simulated payment text) to give BorrowBox the authentic polish of a top-tier startup product.
- **Decision:**
  1. **Purged Academic & Demo Copy:** Replaced `"Version 1.0.0 (Java OOP Lab Project)"` with clean `"Version 1.0.0"`. Removed all emojis from theme selection (`System Default`, `Light`, `Dark`). Replaced `"bKash (Mock)"` and `"Nagad (Mock)"` with `"bKash"` and `"Nagad"`. Replaced `"Auto-Accept Requests (Demo Mode)"` with `"Instant Handover Confirmation"`. Replaced `"Select Simulated Payment Method"` with `"Select Payment Method"`.
  2. **Clean Account Switcher:** Replaced clunky demo user testing headers with a clean `"SWITCH ACCOUNT"` section on the profile screen with sleek outlined student name chips (`Tanjeem`, `Sabbir`, `Himel`, `Tasnim`).
  3. **Vector Iconography Over Emojis:** Replaced all emoji markers (`📍`, `⭐`, `⚡`) with lightweight Android vector drawables (`ic_nav_map`, `ic_urgent_flash`, `ic_settings_gear`, `ic_shield`, `ic_bell`, `ic_theme`).
  4. **Settings Screen Re-architecture:** Rebuilt `fragment_settings.xml` into clean grouped card sections (`PREFERENCES`, `ACCOUNT & SECURITY`, `ABOUT`) with vector icons, Material 3 switches, and crisp typography.
  5. **Verification & Testing:** Executed full unit test suite (24 tasks, 100% passing) and verified on live Android emulator across both Light and Dark modes with zero compilation errors, zero warnings, and high-contrast text legibility.


## Decision 12: Bottom Navigation Profile Bug Fix & Auth Backstack Cleansing
- **Context:** Clicking "Profile" in the bottom navigation bar kept users on the Home page instead of opening ProfileFragment.
- **Root Cause:**
  1. Default `NavigationUI.setupWithNavController()` automatically sets up multiple back stacks with `saveState=true` and `restoreState=true`, popping up to the graph's start destination (`splashFragment`).
  2. Because prior auth screens (`SplashFragment`, `LoginFragment`, `RegisterFragment`) pushed to `HomeFragment` without popping themselves, the saved state associated with the `nav_profile` destination contained a back stack with `HomeFragment` on top. When `NavigationUI` attempted to restore `nav_profile`, it restored that saved back stack, showing `HomeFragment`.
- **Decision:**
  1. **Custom Bottom Navigation Handler:** Replaced default `NavigationUI.setupWithNavController` with explicit `setOnItemSelectedListener` in `MainActivity.java` that navigates to the selected tab with `.setLaunchSingleTop(true).setPopUpTo(R.id.nav_home, false)`, preventing corrupted multi-stack restoration and ensuring every tab directly loads its fragment.
  2. **Backstack Popping in Auth Flow:** Added `setPopUpTo` options across `SplashFragment`, `OnboardingFragment`, `LoginFragment`, `RegisterFragment`, and `ProfileFragment` logout to ensure splash and auth screens never pollute the navigation backstack.
  3. **Direct Home Avatar Access:** Added an interactive circular avatar on the top right of `fragment_home.xml` (displaying the student's initial letter) and linked both the avatar and greeting to directly navigate to `nav_profile`.
  4. **Reactive Student State:** Upgraded `ProfileViewModel` and `HomeViewModel` to use `Transformations.switchMap` so switching accounts updates the UI reactively without requiring application restart.
  5. **Live Verification:** Verified on live Android emulator that both bottom navigation "Profile" and top avatar click navigate to `ProfileFragment` every time without fail.


## Decision 13: Bilingual Language Support (English / Bengali) & Dynamic Bottom Navigation Avatar
- **Context:**
  1. The user requested replacing the top-right profile avatar on the Home screen with a bilingual language switcher supporting English (`en`) and Bengali (`bn`).
  2. The user requested replacing the generic bottom navigation profile icon with the active student's circular profile avatar.
- **Decision:**
  1. **Bilingual Localization (`values-bn/strings.xml`):** Added complete Bengali localization covering all core strings, navigation labels, category chips, emergency actions, trust score descriptions, and settings options.
  2. **Top-Right Language Toggle:** Replaced `tv_home_avatar` on the Home screen header with an interactive pill button (`btn_language_toggle`) featuring a vector globe icon and dynamic text badge (`বাং` when in English, `EN` when in Bengali). Tapping switches language seamlessly and persists preference via `SessionManager` and AndroidX `AppCompatDelegate.setApplicationLocales()`.
  3. **Bottom Navigation Profile Avatar (`AvatarDrawableHelper`):** Created a dedicated helper that generates high-definition circular `BitmapDrawable`s branded with the student's initials and distinctive palette colors. Cleared `imageTintList` on the bottom navigation profile icon view so the avatar displays in vivid original colors without monochrome tinting.
  4. **Dynamic User State Synchronization:** Bound `MainActivity` to reactive user state changes so when switching between demo accounts (`Tanjeem`, `Sabbir`, `Himel`, `Tasnim`), the bottom navigation avatar immediately updates to reflect the active student's initial and color.
  5. **Live Verification:** Built and verified on live Android emulator (`emulator-5554`) that toggling languages updates all UI text dynamically between English and Bengali, and bottom navigation avatar accurately reflects the active user account.
