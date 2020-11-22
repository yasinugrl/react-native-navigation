LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := rnn

PROJECT_FILES := $(wildcard $(LOCAL_PATH)/../cpp/*.cpp)

PROJECT_FILES := $(PROJECT_FILES:$(LOCAL_PATH)/%=%)

LOCAL_SRC_FILES := $(PROJECT_FILES)

LOCAL_C_INCLUDES := $(LOCAL_PATH) \$(LOCAL_PATH)/../cpp

LOCAL_CFLAGS += -DONANDROID -fexceptions -frtti

LOCAL_STATIC_LIBRARIES := libjsi callinvokerholder
LOCAL_SHARED_LIBRARIES := libhermes libfolly_json libfbjni libreactnativejni

include $(BUILD_SHARED_LIBRARY)

# start | build empty library which is needed by CallInvokerHolderImpl.java
include $(CLEAR_VARS)

LOCAL_MODULE := turbomodulejsijni
include $(BUILD_SHARED_LIBRARY)
# end

include $(LOCAL_PATH)/react/Android.mk
