# AyarHesaplama (Gold Fineness Blender)

✨ Calculates how much 24K (995‰) pure gold to add to a lower-fineness alloy to reach 22K (916‰).

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-blue)
![Android](https://img.shields.io/badge/Android-ViewModel-green)
![CI](https://img.shields.io/badge/CI-Coming%20Soon-yellow)

---

## TL;DR

If your gold is below 22K, the app tells you exactly how many grams of 24K (995‰) to add to reach 916‰.

---

## Features

- Inputs: alloy weight (grams) and current fineness (permille ‰).
- One-tap calculation of 24K (995‰) addition.
- Outputs: grams of pure gold to add (“Has Gr”) and new total weight (“Total Gr”).
- Locale-friendly input (. or , for decimals).
- Built with Jetpack Compose (Material 3), ViewModel state, and a testable domain layer.
- Basic validation + Toast feedback.

---

## How it works (formula)

The app calculates how much 24K gold (995‰) needs to be added to an alloy of known weight and fineness to achieve exactly 916‰ (22K).

```kotlin
has_grams = m_takoz * (p_target/1000 - p_takoz/1000) / (p_has/1000 - p_target/1000)
```

- `m_takoz`: current alloy weight (grams)
- `p_takoz`: current fineness (‰)
- `p_target`: target fineness = 916‰
- `p_has`: fineness of pure gold = 995‰

### Behavior

- If the result is negative → clamped to `0.000 g` (already above target).
- Output is rounded to 3 decimal places.

---

## Screenshots
<img width="439" height="936" alt="image" src="https://github.com/user-attachments/assets/ee22ede8-8962-4ea9-9a32-b3e058903072" />
<img width="456" height="936" alt="image" src="https://github.com/user-attachments/assets/99a6429d-4a5e-489d-8287-996b37285692" />


---

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- ViewModel with `UiState`
- Domain layer with pure calculation logic
- Insets handling: `imePadding`, `navigationBarsPadding`

---

## Project Structure

```
app/
 └─ src/main/java/com/emrehancetin/ayarhesaplama/
    ├─ MainActivity.kt
    ├─ viewmodel/
    │   └─ MainViewModel.kt
    ├─ domain/
    │   ├─ Constants.kt
    │   └─ Calculator.kt   // calculateHasGrAndTotal(...)
    └─ ui/theme/           // colors, dimens, theme
```

---

## Getting Started

### Prerequisites

- Android Studio Giraffe (or newer)
- JDK 17
- minSdk ~24

### Build & Run

```bash
git clone https://github.com/emrehancetin/AyarHesaplama.git
cd AyarHesaplama
# Open with Android Studio
# Sync Gradle and Run on device or emulator
```

---

## Usage

1. Enter alloy weight in grams.
2. Enter current fineness in permille (‰).
3. Tap **Calculate**.
4. View:
   - **Has Gr**: grams of 24K (995‰) to add.
   - **Total Gr**: resulting total weight.

✅ Supports `.` or `,` for decimal inputs.

---

## Example Checks

| Takoz (g) | Fineness (‰) | Target (‰) | Has (‰) | Has to add (g) | New total (g) |
|-----------|---------------|-------------|----------|------------------|----------------|
| 1000      | 916           | 916         | 995      | 0.000            | 1000.000       |
| 1000      | 900           | 916         | 995      | 202.532          | 1202.532       |
| 500       | 800           | 916         | 995      | 734.177          | 1234.177       |
| 1000      | 930           | 916         | 995      | 0.000 (clamped)  | 1000.000       |

---

## Configuration

Centralized constants are located in `domain/Constants.kt`:

```kotlin
const val TARGET_AYAR_PERMILLE = 916.0
const val HAS_AYAR_PERMILLE = 995.0
const val ROUND_DECIMALS = 3
```

✅ You can change these to support different fineness standards.

---



## Notes & Disclaimer

This is a calculation tool only. It does **not** account for:
- Process loss
- Impurities
- Melting/handling factors

✅ Always verify results with shop procedures and local regulations.

---



## Contributing

PRs are welcome! Please:

- Keep all business logic in the `domain` layer.
- Keep UI declarative and composable.
- Test calculations thoroughly.

---

## License

Licensed under the MIT License. See [LICENSE](./LICENSE) for details.
