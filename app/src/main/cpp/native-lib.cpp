#include <jni.h>
#include <string>

static bool g_core_attached = false;

extern "C"
JNIEXPORT jstring JNICALL
Java_id_suksesjt12_hollowknightlauncher_MainActivity_nativeBackendStatus(
        JNIEnv* env, jobject /* thiz */) {
    const std::string status = g_core_attached
        ? "Native bridge ready; emulator core attached"
        : "Native bridge ready; no emulator core linked";
    return env->NewStringUTF(status.c_str());
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_id_suksesjt12_hollowknightlauncher_MainActivity_nativeCoreAvailable(
        JNIEnv* /* env */, jobject /* thiz */) {
    return g_core_attached ? JNI_TRUE : JNI_FALSE;
}
