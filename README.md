# PosePilot

Pose guidance app that helps people take better photos by suggesting poses and scoring how well a shot matches the suggested pose.

**Status: Phase 2 complete.** Phase 2 delivers the full premium dark-navy UI: 8 screens, a reusable component library, real navigation, working pose browsing/search/favorites, and a pose-roulette flow. It remains UI-only — no camera capture, pose detection, AI scoring, or persistence yet (those are Phase 3).

## Tech stack

- Native Android (Kotlin + Jetpack Compose + Material 3)
- Gradle 9.3.1 + Android Gradle Plugin 9.1.0
- Kotlin 2.2.10 (AGP built-in Kotlin, compose compiler plugin)
- Navigation Compose, Lifecycle ViewModel + StateFlow
- minSdk 24, compileSdk 36.1, targetSdk 36
- versionCode 2 / versionName 0.2.0

> Deviation note: the original brief specified Flutter. Native Android was chosen instead and this is documented in `ARCHITECTURE.md`.

## Screens (Phase 2)

| Screen | Route | Highlights |
| --- | --- | --- |
| Home | `home` | Greeting, start-shooting CTA, 4 quick-mode cards (Solo/Couple/Group/Free), popular poses, recently used, favorites, roulette entry, photography tip |
| Explore | `explore` | Search, category chips, adaptive pose grid, empty state |
| Pose Details | `pose/{poseId}` | Thumbnail, mode/category/difficulty pills, description, numbered instructions, try-this-pose action |
| Camera | `camera/{poseMode}?refPoseId=` | Permission / preview / countdown / capturing / pose-ready / error states, top-bar UI-state switcher, expandable timer/grid/mirror/auto-capture panel, reference thumbnail |
| Pose Roulette | `roulette` | Poses-per-round (5/10/20), draw, reveal, next-pose, round reset |
| History | `history` | All / Favorites tabs, honest empty states, favorites grid |
| Profile | `profile` | Identity card, edit profile, settings/privacy/help rows, 3 stat cards, photography journey card |
| Settings | `settings` | Camera, pose guidance, appearance, privacy, and about groups; live version from PackageManager |

## Component library

- Shared widgets: `PrimaryButton`, `SecondaryButton`, `ScreenHeader`, `SectionHeader`, `EmptyStateView`, `ModeCard`, `PoseThumbnail`, `FavoriteIconButton`, `PoseCard`, `PoseCompactCard`, `CategoryChip`, `SearchBar`, `PosePilotTopBar`, `SettingsRow`, `SettingsSectionHeader`, `SettingsGroup`, `StatisticCard`, `LoadingPlaceholder`, `LoadingLine`, `ErrorStateView`
- Camera widgets: `CameraControlButton`, `CameraShutterButton`, `CameraPreviewPlaceholder`, `CameraAiStatusPill`, `CameraReferenceThumbnail`

## Core features

- Material 3 **dark-navy theme** applied regardless of system setting; forced dark status/navigation bars
- Bottom navigation with 5 routes: Home, Explore, Camera (prominent), History, Profile
- 16-pose clearly-labeled **sample catalog** (10 categories) with search + category filtering
- **Favorites** held in an in-memory `FavoritesStore` (`MutableStateFlow`)
- Full navigation graph wiring every screen, including `pose/{id}` from Explore and cards
- ViewModels with immutable UI-state data classes: `HomeViewModel`, `ExploreViewModel`, `PoseRouletteViewModel`
- Sealed `AppException` hierarchy with user-safe `userMessage()`

## What is intentionally not here yet

- Real camera preview / photo capture / persistence of photos
- On-device pose detection and scoring (ML Kit or similar)
- Local persistence (Room/DataStore); favorites reset on process death
- A real (non-sample) pose catalog

## Building

```powershell
# requires JDK 17+ and Android SDK (local.properties points to it)
.\gradlew.bat assembleDebug          # debug APK -> app\build\outputs\apk\debug\
.\gradlew.bat testDebugUnitTest      # JVM unit tests
.\gradlew.bat lintDebug              # lint (report: app\build\reports\lint-results-debug.html)
.\gradlew.bat connectedDebugAndroidTest  # instrumented tests (needs emulator/device)
```

Verification status: `assembleDebug` ✅, `testDebugUnitTest` ✅ (7 test classes), `lintDebug` ✅ 0 errors (9 intentional "newer version available" warnings), `connectedDebugAndroidTest` ✅ 15/15.

## Project structure

```
app/src/main/java/com/posepilot/app/
  MainActivity.kt
  core/
    constants/   # shared magic numbers
    errors/      # AppException + userMessage()
    navigation/  # Routes, AppBottomNavigation, PosePilotApp (NavHost)
    permissions/ # AppPermission + PermissionStatus (Phase 3 camera wiring)
    theme/       # Color, Type, Theme, Spacing, ComponentStyles
    util/        # layout helpers
  feature/
    camera/ explore/ history/ home/ pose/ profile/ roulette/ settings/
  shared/
    data/        # SamplePoses, FavoritesStore
    model/       # PoseMode, PoseCategory, Pose, Photo, Session, Favorite, UserSettings
    widgets/     # reusable UI components
app/src/test/        # JVM unit tests
app/src/androidTest/ # instrumented UI tests
```

See `ARCHITECTURE.md` for design decisions and conventions.
