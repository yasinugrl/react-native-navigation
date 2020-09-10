package com.reactnativenavigation.react;

import androidx.annotation.NonNull;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.facebook.react.uimanager.ViewManager;
import com.reactnativenavigation.options.LayoutFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationPackage extends TurboReactPackage {

    private ReactNativeHost reactNativeHost;

    public NavigationPackage(final ReactNativeHost reactNativeHost) {
        this.reactNativeHost = reactNativeHost;
    }

//    @NonNull
//    @Override
//    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
//        return singletonList(new NavigationModule(
//                        reactContext,
//                        reactNativeHost.getReactInstanceManager(),
//                        new LayoutFactory(reactNativeHost.getReactInstanceManager())
//                )
//        );
//    }

    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactApplicationContext) {
        switch(name) {
            case NavigationModule.NAME:
                return new NavigationModule(
                        reactApplicationContext,
                        reactNativeHost.getReactInstanceManager(),
                        new LayoutFactory(reactNativeHost.getReactInstanceManager())
                );
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        try {
            Class<?> reactModuleInfoProviderClass =
                    Class.forName("com.facebook.react.shell.MainReactPackage$$ReactModuleInfoProvider");
            return (ReactModuleInfoProvider) reactModuleInfoProviderClass.newInstance();
        } catch (ClassNotFoundException e) {
            // In OSS case, the annotation processor does not run. We fall back on creating this by hand
            Class<? extends NativeModule>[] moduleList =
                    new Class[] {
                            NavigationModule.class,
                    };

            final Map<String, ReactModuleInfo> reactModuleInfoMap = new HashMap<>();
            for (Class<? extends NativeModule> moduleClass : moduleList) {
                ReactModule reactModule = moduleClass.getAnnotation(ReactModule.class);

                reactModuleInfoMap.put(
                        reactModule.name(),
                        new ReactModuleInfo(
                                reactModule.name(),
                                moduleClass.getName(),
                                reactModule.canOverrideExistingModule(),
                                reactModule.needsEagerInit(),
                                reactModule.hasConstants(),
                                reactModule.isCxxModule(),
                                TurboModule.class.isAssignableFrom(moduleClass)));
            }

            return () -> reactModuleInfoMap;
        } catch (InstantiationException e) {
            throw new RuntimeException(
                    "No ReactModuleInfoProvider for CoreModulesPackage$$ReactModuleInfoProvider", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "No ReactModuleInfoProvider for CoreModulesPackage$$ReactModuleInfoProvider", e);
        }
    }
}
