#include "main.h"

static const char* className = "me/hhhaiai/refcore/fkhide/NativeHidden";

#include <android/log.h>
#define TAG "jni" // 这个是自定义的LOG的标识
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

extern "C" {

	jstring nativeGetHelloString(JNIEnv *env, jobject obj) {
		return env->NewStringUTF((char *) " 反射测试工具测试【native】");
	}
	static JNINativeMethod gMethods[] = {
			{ "test", "()Ljava/lang/String;", (void *) nativeGetHelloString },
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

	jint JNI_OnLoad(JavaVM* vm, void* reserved) {
		JNIEnv* env = NULL;
		jint result = -1;
		if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
			// LOGE("ERROR: GetEnv failed\n");
			goto bail;
		}

		if (registerNativeMethods(env) < 0) {
			// LOGE("ERROR: jnitest native registration failed\n");
			goto bail;
		}
		result = JNI_VERSION_1_4;

		bail: return result;
	}

}