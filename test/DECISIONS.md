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
