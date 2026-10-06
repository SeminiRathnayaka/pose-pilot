# PosePilot

Pose guidance app that helps people take better photos by suggesting poses and scoring how well a shot matches the suggested pose.

**Status: Phase 1 complete.** Phase 1 ships a clean, native Android foundation only: theme, navigation, home screen, data models, error handling, tests, and docs. No camera, pose detection, or AI features yet.

## Tech stack

- Native Android (Kotlin + Jetpack Compose + Material 3)
- Gradle 9.3.1 + Android Gradle Plugin 9.1.0
- Kotlin 2.2.10 (AGP built-in Kotlin, compose compiler plugin)
- Navigation Compose, Lifecycle ViewModel + StateFlow
- minSdk 24, compileSdk 36.1, targetSdk 36

> Deviation note: the original brief specified Flutter. Native Android was chosen instead and this is documented in `ARCHITECTURE.md`.

## What Phase 1 includes

- Material 3 theme: black/white base with a single accent color, light + dark support, no dynamic color
- Bottom navigation with 5 routes: Home, Camera, Poses, History, Profile
- Home screen: greeting, three pose-mode cards (Solo / Couple / Group), best-score card with real empty state, start-session and browse-poses actions
- Shared components: PrimaryButton, SecondaryButton, PoseModeCard, SectionHeader, EmptyState, ScoreCard, ScreenHeader, PlaceholderContent
- Plain Kotlin models: PoseMode, Pose, Photo, Session, Favorite, UserSettings
- Sealed error types (`AppException`) with user-facing messages
- Permission model (`AppPermission`, `PermissionStatus`) ready for Phase 2 camera work
- Placeholder screens for Camera, Poses, History, Profile (marked as Phase 2, no fake data)

## What is intentionally not here yet

- Camera preview, photo capture, permission flow UI
- On-device pose detection / scoring (ML Kit or similar)
- Local persistence (Room/DataStore)
- Real pose catalog (the sample catalog is clearly labeled)

## Building

```powershell
# requires JDK 17+ and Android SDK (local.properties points to it)
.\gradlew.bat assembleDebug          # debug APK -> app\build\outputs\apk\debug\
.\gradlew.bat testDebugUnitTest      # JVM unit tests
.\gradlew.bat lintDebug              # lint (report: app\build\reports\lint-results-debug.html)
.\gradlew.bat connectedDebugAndroidTest  # instrumented tests (needs emulator/device)
```

## Project structure

```
app/src/main/java/com/posepilot/app/
  MainActivity.kt
  core/
    constants/   # shared magic numbers
    errors/      # AppException + userMessage()
    navigation/  # Routes, AppBottomNavigation, PosePilotApp (NavHost)
    permissions/ # AppPermission + PermissionStatus
    theme/       # Color, Type, Theme, Spacing, ComponentStyles
    util/        # layout helpers
  feature/
    camera/ history/ home/ poses/ profile/
  shared/
    model/       # PoseMode, Pose, Photo, Session, Favorite, UserSettings
    widgets/     # reusable UI components
app/src/test/        # JVM unit tests
app/src/androidTest/ # instrumented UI tests
```

See `ARCHITECTURE.md` for design decisions and conventions.
