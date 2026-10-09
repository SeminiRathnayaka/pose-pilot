# PosePilot Architecture

Phase 2 premium UI. This document records the decisions behind the code so Phase 3
(camera + on-device pose detection) can extend the app without re-litigating them.

## Platform choice

The original brief specified Flutter. The project was re-scoped to **native Android
(Kotlin + Jetpack Compose + Material 3)** before any code was written, because:

- the target is a clean Android foundation, and
- Phase 3 needs deep CameraX / ML Kit integration, which is first-class on native.

No Flutter artifacts remain in the repository.

## Toolchain

| Tool | Version | Why |
| --- | --- | --- |
| Gradle | 9.3.1 | current stable, copied wrapper |
| Android Gradle Plugin | 9.1.0 | works with the built-in Kotlin plugin, no separate `kotlin-android` plugin needed |
| Kotlin | 2.2.10 | required by the `kotlin-compose` plugin alias |
| compileSdk | 36.1 | latest installed platform; AGP 9.1.0 recommends max 36 |
| minSdk / targetSdk | 24 / 36 | wide device coverage |

**Dependency ceiling:** `androidx.core` 1.19.x, `lifecycle` 2.11.x and
`navigation` 2.10.x ship AAR metadata requiring `minCompileSdk=37`, which this
AGP/installation cannot compile against. They are therefore pinned to the newest
versions that support compileSdk 36 (`core-ktx 1.18.0`, `lifecycle 2.10.0`,
`navigation-compose 2.9.8`). Lint's "newer version available" warnings for these
are expected and intentional (9 warnings, 0 errors). Upgrading AGP + compileSdk to
37 will lift the cap.

## Layers

```
core/     theme, navigation, errors, permissions, constants, utils  (no feature knowledge)
shared/   models, data stores, reusable widgets                     (no feature knowledge)
feature/  screens and ViewModels                                    (may use core + shared)
```

Dependencies only point downwards: `feature -> shared -> core`. Feature packages do
not reference each other; all cross-screen movement goes through navigation.

## Navigation

- `core/navigation/Routes.kt` is the single source of truth for route strings, the
  `pose()` / `camera()` builders, and the bottom-bar item list.
- Routes: `home`, `explore`, `pose/{poseId}`, `camera/{poseMode}?refPoseId={refPoseId}`
  (defaults `SOLO` / empty), `roulette`, `history`, `profile`, `settings`.
- `PosePilotApp` owns the `Scaffold` + `NavHost` with fade transitions; the bottom
  bar highlights the current destination and treats the center Camera item as the
  prominent primary action.
- Cross-screen actions are passed as lambdas from `PosePilotApp` into screens
  (e.g. `onOpenPose`), so feature packages stay decoupled.
- Test tags: `nav_<route>` for bottom-bar items (the item carries a stable `tag`
  with `/` and `?` stripped), `screen_<name>` for screens, `mode_card_<key>`,
  `pose_card_<id>`, `category_chip_<key>`, `camera_*`, `roulette_*`, `setting_*`,
  `button_*`.

## State management

ViewModel + `StateFlow`, exposed as immutable `StateUi` data classes:

- survives configuration changes and process death scenarios,
- screens collect with `collectAsStateWithLifecycle()` so collection stops when the
  screen is not visible,
- UI state is a plain data class, which makes initial-state unit tests trivial.

Phase 2 has three ViewModels — `HomeViewModel`, `ExploreViewModel`,
`PoseRouletteViewModel` — one per feature, never shared across features. Favorites
are owned by the process-wide `shared/data/FavoritesStore` (a `MutableStateFlow`
object) so Home, Explore, and Pose Details stay in sync; it is in-memory only and
resets on process death until Phase 3 adds persistence.

## Theme

- `core/theme/Color.kt` defines semantic slots; `Theme.kt` maps them to a single
  forced **dark-navy** `darkColorScheme`. Dynamic color is intentionally disabled
  and the theme does not react to system light/dark settings.
- Palette: DeepNavy `#0B1220` (backgrounds), Navy `#111C2E` (cards/nav), Black
  `#05070A` (camera), DarkGray `#252B35` (controls/chips), Gray `#6B7280`
  (secondary icons), LightGray `#D1D5DB` (borders/supporting text), White `#FFFFFF`
  (primary text / shutter). No gradients, no purple/pink/bright accents.
- `Spacing.kt` centralizes `AppSpacing`, `AppRadius` (incl. pill), `AppIconSize`.
- `ComponentStyles.kt` groups Material style objects (buttons, cards, inputs, nav)
  so screens never hard-code colors.
- `MainActivity` calls `enableEdgeToEdge(...SystemBarStyle.dark...)` so status and
  navigation bars draw transparent over the navy background with light icons.

## Error handling

`core/errors/AppException.kt` is a sealed hierarchy:
`PermissionDenied`, `CameraUnavailable`, `StorageUnavailable`, `ComputerVision`,
`Generic`. `Throwable.userMessage()` maps anything to a safe, user-facing string.
`ErrorStateView` renders these in the UI (with `error_primary` / `error_secondary`
tags). Phase 3 must throw typed `AppException`s from lower layers and render
`userMessage()`, never raw exception text.

## Permissions

`core/permissions/AppPermission.kt` declares the permission enum
(`CAMERA`, `RECORD_AUDIO`, with `requiredForCapture`) and a `PermissionStatus`
sealed interface (`Granted`, `Denied`, `PermanentlyDenied`). Phase 2's camera
screen models the permission state visually but does not request a real runtime
permission; an in-memory `CameraFlowState.permissionAcknowledged` lets the UI move
past the permission screen during preview. Phase 3 wires this into
`ActivityResultContracts.RequestPermission` and the rationale UI.

## Camera preview states

`CameraScreen` is driven by a `CameraUiState` enum (Permission, Preview, Countdown,
Capturing, PoseReady, Error). A top-bar dropdown switches between these states so
the UI can be reviewed honestly without a real camera; the preview itself is a
labeled placeholder (rule-of-thirds grid + corner markers) that explicitly states
no live feed is shown yet.

## Placeholders and honesty rules

- The camera preview is a clearly labeled placeholder; no fake live feed.
- No fake AI scores, no fabricated history or statistics. Empty states always state
  exactly what will appear and when.
- The sample pose catalog (16 poses, 10 categories) is labeled "Sample data"
  wherever it renders; no stock photography is presented as real output.
- Settings rows that describe future behavior say so ("will run on your device in a
  future update").

## Responsive layout

`core/util/Layout.kt:gridColumnsFor()` picks 2/3/4 columns for width breakpoints.
Explore uses an adaptive `LazyVerticalGrid` (min 160dp). All interactive elements
meet the 48dp minimum touch target (`AppConstants.MIN_TOUCH_TARGET_DP`).

## Testing

| Layer | Where | Tooling |
| --- | --- | --- |
| Unit | `src/test` | JUnit4 — PoseMode, SamplePoses catalog/search, ExploreViewModel, RouletteViewModel, FavoritesStore, AppException, HomeViewModel |
| UI / navigation | `src/androidTest` | Compose test rule `v2`, test tags; startup, home, bottom-nav, explore, camera flow, roulette (15 tests) |

Conventions:

- assert on `testTag` first, text second (text nodes may be below the fold in
  scrollables — use `performScrollTo()` before asserting/clicking them),
- tests never assert on fake scores or fabricated data,
- instrumented tests must not mutate process-wide state in ways that leak across
  test classes (e.g. `FavoritesStore` and `CameraFlowState` are shared per process,
  so `CameraTest` is a single self-contained flow and `RouletteTest` does not
  persist a round),
- `connectedDebugAndroidTest` is the gate for navigation behavior; it requires a
  booted emulator.

## Conventions

- No inline code comments in source; rationale lives here and in `README.md`.
- User-facing strings live in `res/values/strings.xml`; model-layer strings stay
  plain Kotlin strings.
- `@StringRes` parameters are annotated `@param:StringRes` to avoid Kotlin 2.2
  annotation-target warnings.
- Icons: core Material icons where available, vector drawables otherwise; all are
  tinted so a single asset works across the palette.
- Single-activity architecture; `MainActivity` only sets content.
