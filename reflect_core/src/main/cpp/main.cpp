#include "main.h"

static const char *className = "me/hhhaiai/refcore/fkhide/NativeHidden";


#define TAG "jni" // 这个是自定义的LOG的标识

extern "C" {

jstring nativeGetHelloString(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF((char *) " 反射测试工具测试【native】");
}
jint unsealNative(JNIEnv *env, jclass clazz, jint targetSdkVersion) {
    return unseal(env, targetSdkVersion);
}
static JNINativeMethod gMethods[] = {
        {"test",   "()Ljava/lang/String;", (void *) nativeGetHelloString},
        {"unseal", "(I)I",                 (jint *) unsealNative},
};

static int registerNativeMethods(JNIEnv *env) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        // LOGD("failed to load the class %s", className);
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods,
                             sizeof(gMethods) / sizeof(gMethods[0])) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        // LOGE("ERROR: GetEnv failed\n");
        goto bail;
    }

    if (registerNativeMethods(env) < 0) {
        // LOGE("ERROR: jnitest native registration failed\n");
        goto bail;
    }
    result = JNI_VERSION_1_4;

    bail:
    return result;
}

}