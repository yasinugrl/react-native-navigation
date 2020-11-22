package com.reactnativenavigation.react;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;

public abstract class NavigationModuleSpec extends ReactContextBaseJavaModule implements ReactModuleWithSpec, TurboModule {

    public NavigationModuleSpec(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public abstract WritableMap getLaunchArgs(String commandId);

    @ReactMethod(isBlockingSynchronousMethod = true)
    public abstract WritableMap getNavigationConstants();

    @ReactMethod
    public abstract void setRoot(String commandId, ReadableMap rawLayoutTree, Promise promise);

    @ReactMethod
    public abstract void setDefaultOptions(ReadableMap options);

    @ReactMethod
    public abstract void mergeOptions(String onComponentId, @Nullable ReadableMap options);

    @ReactMethod
    public abstract void push(String commandId, String onComponentId, ReadableMap rawLayoutTree, Promise promise);

    @ReactMethod
    public abstract void setStackRoot(String commandId, String onComponentId, ReadableArray children, Promise promise);

    @ReactMethod
    public abstract void pop(String commandId, String componentId, @Nullable ReadableMap mergeOptions, Promise promise);

    @ReactMethod
    public abstract void popTo(String commandId, String componentId, @Nullable ReadableMap mergeOptions, Promise promise);

    @ReactMethod
    public abstract void popToRoot(String commandId, String componentId, @Nullable ReadableMap mergeOptions, Promise promise);

    @ReactMethod
    public abstract void showModal(String commandId, ReadableMap rawLayoutTree, Promise promise);

    @ReactMethod
    public abstract void dismissModal(String commandId, String componentId, @Nullable ReadableMap mergeOptions, Promise promise);

    @ReactMethod
    public abstract void dismissAllModals(String commandId, @Nullable ReadableMap mergeOptions, Promise promise);

    @ReactMethod
    public abstract void showOverlay(String commandId, ReadableMap rawLayoutTree, Promise promise);

    @ReactMethod
    public abstract void dismissOverlay(String commandId, String componentId, Promise promise);
}
