package com.reactnativenavigation.ndk.demo

class JSIDemo {
    external fun getDemoInt(): Int

    companion object {
        init {
            System.loadLibrary("jsi_demo");
        }
    }
}