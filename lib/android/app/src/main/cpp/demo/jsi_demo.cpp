//
// Created by Ward Abbass on 3/25/21.
//
#include "jni.h"

#include "include/jsi_demo.h"
#include <android/log.h>
#include <jni.h>

extern "C" JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    __android_log_print(ANDROID_LOG_INFO, __FUNCTION__, "onLoad");
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    // Get jclass with env->FindClass.
    // Register methods with env->RegisterNatives.
    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jint JNICALL Java_com_reactnativenavigation_ndk_demo_JSIDemo_getDemoInt(JNIEnv *env, jobject thiz) {
    return 777;
}
