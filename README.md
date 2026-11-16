# Hollow Knight Android — Switch Emulator Build

This repository builds an Android ARM64 Nintendo Switch emulator using the open-source Lemon project as its native emulation engine and Android frontend.

## Backend

The emulator source is pinned as a Git submodule:

- Upstream project: https://github.com/Ghael-V/Lemon-Project
- Pinned source revision: `b1482ae3d446dc17c5bd68e8ad41ca3ee7563c42`
- License: GNU GPL v3 or later (see `third_party/Lemon-Project/LICENSE.txt`)

Lemon is an Android-native emulator project. Its upstream README documents Mali GPU support from version 1.1 and provides a Lite build with lighter default settings. This repository builds the Lite flavor for ARM64.

## Build APK

The GitHub Actions workflow builds the APK automatically when code is pushed to `main`, or when manually started from the Actions tab.

1. Open the **Actions** tab in this repository.
2. Open **Android Emulator APK**.
3. Press **Run workflow**.
4. When the run succeeds, open its run page and download the artifact named `HollowKnight-Switch-Emulator-Lite-ARM64`.

The build uses the upstream Android build script and installs the required Android SDK, NDK and CMake versions. The full C++ build can take substantial CI time.

## Device notes

- Target ABI: `arm64-v8a`
- Lemon's current mainline build requires Android 12 or newer. Android 14 meets the OS requirement.
- Actual game performance and compatibility depend on the device's Vulkan driver, GPU, CPU, memory and game version. Mali support does not guarantee that every Mali device or game will work well.
- Helio G100 / Mali-G57 performance has not been validated by this repository's CI. Do not assume a stable frame rate until tested on the phone.

## Game data and legal use

This repository does **not** include Hollow Knight, any other game, console firmware, encryption keys, or copyrighted game data. The emulator does not make an NSP playable merely by selecting it. Use only game data, firmware and system files you are legally entitled to use. The project does not provide keys or instructions to bypass console protections.

## License and attribution

The emulator source in the submodule retains its upstream copyright notices and is licensed under GPL-3.0-or-later. Review the upstream license and notices before distributing modified builds. Lemon is an independent project and is not affiliated with Nintendo or Team Cherry.
