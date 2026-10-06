# PosePilot Architecture

Phase 1 foundation. This document records the decisions behind the code so Phase 2
(camera + pose detection) can extend the app without re-litigating them.

## Platform choice

The original brief specified Flutter. The project was re-scoped to **native Android
(Kotlin + Jetpack Compose + Material 3)** before any code was written, because:

- the target for Phase 1 is a clean Android foundation, and
- Phase 2 needs deep CameraX / ML Kit integration, which is first-class on native.

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
are expected and intentional. Upgrading AGP + compileSdk to 37 will lift the cap.

## Layers

```
core/     theme, navigation, errors, permissions, constants, utils  (no feature knowledge)
shared/   models + reusable widgets                                 (no feature knowledge)
feature/  screens and ViewModels                                    (may use core + shared)
```

Dependencies only point downwards: `feature -> shared -> core`. Feature packages do
not reference each other; all cross-screen movement goes through navigation.

## Navigation

- `core/navigation/Routes.kt` is the single source of truth for route strings and
  bottom-bar items.
- Five routes: `home`, `camera/{poseMode}`, `poses`, `history`, `profile`.
  `poseMode` has a default (`solo`) so the camera route can also be opened from the
  bottom bar without a mode.
- `PosePilotApp` owns the `Scaffold` + `NavHost`; the bottom bar highlights the
  current destination (including `camera/*` for the camera item).
- Mode cards navigate to `camera/{mode}` so the camera screen always knows its mode.
- Test tags: `nav_<route>` for bottom-bar items, `screen_<name>` for screens,
  `mode_card_<key>` for mode cards.

## State management

ViewModel + `StateFlow`, exposed as immutable `StateUi` data classes:

- survives configuration changes and process death scenarios,
- screens collect with `collectAsStateWithLifecycle()` so collection stops when the
  screen is not visible,
- UI state is a plain data class, which makes initial-state unit tests trivial
  (`HomeViewModelTest`).

Phase 1 has a single ViewModel (`HomeViewModel`); Phase 2 should follow the same
pattern per feature (one ViewModel per screen or per flow, never shared across
features).

## Theme

- `core/theme/Color.kt` defines semantic slots; `Theme.kt` maps them to light/dark
  `ColorScheme`s. Dynamic color is intentionally disabled so the brand accent stays
  stable.
- Accent: `#C8461F` (light) / `#FF8A5B` (dark) on a black/white base; no gradients.
- `ComponentStyles.kt` groups Material style objects (buttons, cards, inputs, top
  bars, navigation) so screens never hard-code colors.

## Error handling

`core/errors/AppException.kt` is a sealed hierarchy:
`PermissionDenied`, `CameraUnavailable`, `StorageUnavailable`, `ComputerVision`,
`Generic`. `Throwable.userMessage()` maps anything to a safe, user-facing string.
Phase 2 must throw typed `AppException`s from lower layers and render
`userMessage()` in the UI (snackbars/dialogs), never raw exception text.

## Permissions

`core/permissions/AppPermission.kt` declares the permission enum
(`CAMERA`, `RECORD_AUDIO`, with `requiredForCapture`) and a `PermissionStatus`
sealed interface (`Granted`, `Denied`, `PermanentlyDenied`). No permission is
requested in Phase 1 because there is no camera yet; Phase 2 wires this into
`ActivityResultContracts.RequestPermission` and shows the rationale UI.

## Placeholders and honesty rules

- Placeholder screens use `PlaceholderContent` with copy that states the feature
  arrives in Phase 2.
- No fake AI scores, no fabricated history. The empty state always shows the exact
  string "Your best score will appear here after your first session."
- The sample pose catalog is labeled "Sample catalog" wherever it renders.

## Responsive layout

`core/util/Layout.kt:gridColumnsFor()` picks 2/3/4 columns for width breakpoints.
Mode cards render as a row at `>= 600.dp` and a column below. All interactive
elements meet the 48dp minimum touch target (`AppConstants.MIN_TOUCH_TARGET_DP`).

## Testing

| Layer | Where | Tooling |
| --- | --- | --- |
| Unit | `src/test` | JUnit4 (PoseMode routing, AppException messages, HomeViewModel initial/selected state, model defaults) |
| UI / navigation | `src/androidTest` | Compose test rule `v2`, test tags; startup, home, bottom-nav flows |

Conventions:

- assert on `testTag` first, text second (text nodes may be below the fold in
  scrollables — use `performScrollTo()` before asserting/clicking them),
- tests never assert on fake scores or fabricated data,
- `connectedDebugAndroidTest` is the gate for navigation behavior; it requires a
  booted emulator.

## Conventions

- No inline code comments in source; rationale lives here and in `README.md`.
- User-facing strings live in `res/values/strings.xml`; model-layer strings stay
  plain Kotlin strings.
- Icons: core Material icons where available (`Home`, `Person`), vector drawables
  (`ic_camera`, `ic_poses`, `ic_history`, `ic_couple`, `ic_group`) otherwise; they
  are tinted by `Icon`'s tint so a single black asset works in both themes.
- Single-activity architecture; `MainActivity` only sets content.
