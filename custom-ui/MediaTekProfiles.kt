package dev.lemon.lemon_emu.utils

import dev.lemon.lemon_emu.features.settings.model.BooleanSetting
import dev.lemon.lemon_emu.features.settings.model.IntSetting

/**
 * Experimental presets for Mali-G57 phones using MediaTek Helio G100/G99.
 *
 * These profiles only write settings exposed by the emulator core. They do not change
 * Android CPU governors, CPU/GPU frequencies, kernel schedulers, or thermal limits.
 */
object MediaTekProfiles {
    private fun applyCommon(resolution: Int, gpuAccuracy: Int, pipelineWorkers: Int) {
        // ResolutionSetup: 0=0.25x, 1=0.5x, 2=0.75x, 3=1x.
        IntSetting.RENDERER_RESOLUTION.setInt(resolution)

        // ScalingFilter: 1=Bilinear. Keep the presentation pass inexpensive.
        IntSetting.RENDERER_SCALING_FILTER.setInt(1)

        // AntiAliasing: 0=None.
        IntSetting.RENDERER_ANTI_ALIASING.setInt(0)

        // GpuAccuracy: 0=Low/Fast, 1=High/Accurate.
        IntSetting.RENDERER_ACCURACY.setInt(gpuAccuracy)

        // VSyncMode: 2=Fifo.
        IntSetting.RENDERER_VSYNC.setInt(2)

        // The Vulkan pipeline worker count is clamped by the core to 2..8.
        IntSetting.ANDROID_PIPELINE_WORKERS.setInt(pipelineWorkers.coerceIn(2, 8))

        BooleanSetting.RENDERER_ASYNCHRONOUS_SHADERS.setBoolean(true)
        BooleanSetting.RENDERER_USE_DISK_SHADER_CACHE.setBoolean(true)
        BooleanSetting.RENDERER_FRAME_GEN.setBoolean(false)

        NativeConfig.saveGlobalConfig()
    }

    fun applyPerformance() {
        applyCommon(
            resolution = 2,       // 0.75x
            gpuAccuracy = 0,      // Fast
            pipelineWorkers = 2   // conservative worker count for thermals
        )
    }

    fun applyCompatibility() {
        applyCommon(
            resolution = 1,       // 0.5x
            gpuAccuracy = 1,      // Accurate
            pipelineWorkers = 2
        )
    }

    fun applyCooler() {
        applyCommon(
            resolution = 1,       // 0.5x
            gpuAccuracy = 0,      // Fast
            pipelineWorkers = 2
        )
    }
}
