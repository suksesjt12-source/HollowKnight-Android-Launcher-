#!/usr/bin/env bash
set -euo pipefail

# Build the upstream nx2android device probes as one NativeActivity shared library.
# Usage: ./build_probe.sh /path/to/Android/ndk/27.2.12479018

NDK_ROOT="${1:-${ANDROID_NDK_HOME:-}}"
if [[ -z "$NDK_ROOT" || ! -d "$NDK_ROOT/toolchains/llvm/prebuilt/linux-x86_64/bin" ]]; then
  echo "ERROR: Android NDK r27c path is missing or invalid." >&2
  exit 2
fi

SRC_ROOT="${NX2ANDROID_SOURCE_DIR:-upstream/nx2android}"
OUT_DIR="${PROBE_OUT_DIR:-build}"
TOOLCHAIN="$NDK_ROOT/toolchains/llvm/prebuilt/linux-x86_64/bin"
CC="$TOOLCHAIN/aarch64-linux-android29-clang"

for f in \
  "$SRC_ROOT/probes/cpuprobe.c" \
  "$SRC_ROOT/probes/vkprobe.c" \
  "$SRC_ROOT/probes/vkzc.c" \
  "$SRC_ROOT/probes/app/probeapp.c" \
  "$SRC_ROOT/probes/app/font5x7.h" \
  "$SRC_ROOT/probes/app/mkapk.py"; do
  if [[ ! -f "$f" ]]; then
    echo "ERROR: Required upstream file not found: $f" >&2
    exit 3
  fi
done

mkdir -p "$OUT_DIR"

"$CC" -O1 -g -fPIC -ffixed-x18 -D_GNU_SOURCE -Dmain=cpuprobe_main \
  -c "$SRC_ROOT/probes/cpuprobe.c" -o "$OUT_DIR/cpuprobe.o"

"$CC" -O1 -g -fPIC -D_GNU_SOURCE -Dmain=vkprobe_main \
  -c "$SRC_ROOT/probes/vkprobe.c" -o "$OUT_DIR/vkprobe.o"

"$CC" -O1 -g -fPIC -D_GNU_SOURCE -Dmain=vkzc_main \
  -c "$SRC_ROOT/probes/vkzc.c" -o "$OUT_DIR/vkzc.o"

"$CC" -O1 -g -fPIC -D_GNU_SOURCE \
  -I"$SRC_ROOT/probes/app" \
  -c "$SRC_ROOT/probes/app/probeapp.c" -o "$OUT_DIR/probeapp.o"

"$CC" -shared -Wl,-soname,libprobe.so \
  -o "$OUT_DIR/libprobe.so" \
  "$OUT_DIR/probeapp.o" \
  "$OUT_DIR/cpuprobe.o" \
  "$OUT_DIR/vkprobe.o" \
  "$OUT_DIR/vkzc.o" \
  -landroid -llog -lnativewindow -ldl -lvulkan -pthread

python3 "$SRC_ROOT/probes/app/mkapk.py" \
  "$OUT_DIR/libprobe.so" "$OUT_DIR/nx2a-probe.apk"

test -s "$OUT_DIR/nx2a-probe.apk"
echo "Built: $OUT_DIR/nx2a-probe.apk"
sha256sum "$OUT_DIR/nx2a-probe.apk"
