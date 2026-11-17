# Nightfall — Custom Android Switch Runtime

Nightfall is a custom Android launcher and configuration layer for a Switch emulation runtime. It is designed around the original Hollow Knight Switch NSP workflow, while keeping game data and Switch system files outside the APK.

## What is custom

- Original Nightfall launcher UI (not the Lemon or Lemon Lite library screen).
- Separate Android application identity: `com.suksesjt12.hollowknight.switch`.
- MediaTek G100/G99 profile menu with Performance, Compatibility and Cooler presets.
- Presets are wired to existing emulator settings: internal resolution, GPU accuracy, bilinear scaling, VSync, asynchronous shader compilation, disk shader cache and Vulkan pipeline worker count.
- Existing Android game discovery, NSP/XCI handling, native emulation bridge, input and emulation activity are reused.

## Engine and licensing

The emulation engine and its Android/JNI integration are derived from [Lemon-Project](https://github.com/Ghael-V/Lemon-Project), pinned at commit `b1482ae3d446dc17c5bd68e8ad41ca3ee7563c42`. Nightfall replaces the launcher screen and adds its own settings layer; it is **not** a clean-room emulator and it is **not** a native Hollow Knight recompilation.

Lemon-Project is licensed under GNU GPL-3.0-or-later. Any redistribution of Nightfall must preserve applicable notices, include the GPL license, and provide the corresponding source for the covered modified work. Review all upstream and third-party notices before distributing builds.

## MediaTek profiles

The presets only change options exposed by the emulator core. They do not modify CPU governor, CPU/GPU clock speeds, kernel scheduling, or Android thermal policy.

- **Performance:** 0.75x internal resolution, Fast GPU accuracy, bilinear scaling, async shaders, disk shader cache, two Vulkan pipeline workers.
- **Compatibility:** 0.5x internal resolution, High GPU accuracy, bilinear scaling, async shaders, disk shader cache, two Vulkan pipeline workers.
- **Cooler:** 0.5x internal resolution, Fast GPU accuracy, no anti-aliasing, async shaders, disk shader cache, two Vulkan pipeline workers.

The G100/G99 presets are experimental. They have not been validated against every firmware build or every Switch title. Fast GPU accuracy may produce rendering defects in some games; use Compatibility if glitches occur. No FPS or temperature target is guaranteed.

## Build

Pushes to `custom/mediatek-ui` trigger GitHub Actions. A successful build publishes the artifact `Nightfall-MediaTek-ARM64`. The build uses the upstream standard/mainline engine flavor, not Lemon Lite.

## Game data and system files

The repository and APK do not contain Hollow Knight, any other commercial game, NSP/XCI files, `prod.keys`, Switch firmware, or other protected system data. Keep your own files on-device and use only game/system files you are legally entitled to use. The app does not include keys or instructions to bypass console protections.

## Device target

- Android 12 or newer
- ARM64
- MediaTek Helio G100/G99 with Mali-G57 is the initial tuning target
- Actual compatibility and performance still need on-device testing

Nightfall is an independent community build and is not affiliated with Nintendo or Team Cherry.
