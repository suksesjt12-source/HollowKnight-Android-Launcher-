# Hollow Knight Android — Android Switch Emulator Build

This repository builds a branded Android application based on the open-source Lemon Lite Nintendo Switch emulator frontend and native emulation engine. The APK is intended as an emulator launcher for compatible, legally obtained Switch game data; it is not a native Hollow Knight port.

## App identity

- Display name: Hollow Knight Android
- Android package ID: `com.suksesjt12.hollowknight.switch`
- Target ABI: `arm64-v8a`
- Emulator engine/frontend: Lemon Lite, pinned as a Git submodule at `b1482ae3d446dc17c5bd68e8ad41ca3ee7563c42`
- Upstream: https://github.com/Ghael-V/Lemon-Project
- License: GNU GPL v3 or later; preserve upstream notices and comply with the license when redistributing.

The CI workflow applies the app label and package ID to the upstream Lite flavor before compiling. The emulator interface and engine remain based on upstream Lemon; this is not yet a separate Hollow Knight-specific game UI or a native recompilation of the Hollow Knight game.

## Build APK

GitHub Actions builds the ARM64 APK when code is pushed to `main`, or when manually started from the Actions tab. Download the artifact named `HollowKnight-Android-ARM64` from a successful workflow run.

## Device notes

- Android 12 or newer is required by the upstream mainline build.
- Mali GPU support in Lemon does not guarantee compatibility with every Mali device or game.
- Helio G100 / Mali-G57 performance has not been validated. No stable FPS target is guaranteed.

## Game data and legal use

The repository does not include Hollow Knight, other games, console firmware, encryption keys, or copyrighted game data. Selecting an NSP/XCI file alone does not make it playable. Use only game data and system files you are legally entitled to use. This project does not provide keys or instructions to bypass console protections.

## Attribution

Lemon is an independent open-source emulator project and is not affiliated with Nintendo or Team Cherry. Review the upstream GPL-3.0-or-later license and copyright notices before distributing modified builds.
