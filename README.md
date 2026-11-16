# HollowKnight Android Launcher

Prototype launcher project for Android.

## Current scope
- Landscape launcher UI
- Android Storage Access Framework file picker
- Backend status indicator
- GitHub Actions debug APK workflow

## Important limitation
This is only a launcher shell. It does not include a Nintendo Switch emulator,
game decryption, firmware, keys, or a game runtime. Selecting an NSP file does
not make the game playable. A compatible emulator backend must be integrated
separately after reviewing its license and distribution requirements.

## Build
The GitHub Actions workflow uses JDK 17, Gradle 8.9, and Android SDK.
Open the Actions tab and run the "Android APK" workflow, or push a commit
to the main branch to trigger a build. The APK is uploaded as an artifact.
