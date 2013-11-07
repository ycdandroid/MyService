LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog

LOCAL_MODULE    := MyService
LOCAL_SRC_FILES := MyService.cpp

include $(BUILD_SHARED_LIBRARY)
