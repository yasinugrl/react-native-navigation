package com.reactnativenavigation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.JSIModuleProvider;
import com.facebook.react.bridge.JSIModuleSpec;
import com.facebook.react.bridge.JSIModuleType;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.turbomodule.core.TurboModuleManager;
import com.facebook.soloader.SoLoader;
import com.reactnativenavigation.options.LayoutFactory;
import com.reactnativenavigation.react.NavigationModule;
import com.reactnativenavigation.react.ReactGateway;
import com.reactnativenavigation.viewcontrollers.externalcomponent.ExternalComponentCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NavigationApplication extends Application implements ReactApplication {

    private ReactGateway reactGateway;
    public static NavigationApplication instance;
    final Map<String, ExternalComponentCreator> externalComponents = new HashMap<>();

    static {
        ReactFeatureFlags.useTurboModules = true;
    }

    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {

                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                public List<ReactPackage> getPackages() {
                    return Arrays.asList(
                            new MainReactPackage(),
                            new TurboReactPackage() {
                                public NativeModule getModule(final String name, final ReactApplicationContext reactContext) {
                                    if (NavigationModule.NAME.equals(name)) {
                                        return new NavigationModule(reactContext, getReactInstanceManager(), new LayoutFactory(getReactInstanceManager()));
                                    }

                                    return null;
                                }

                                // Note: Specialized annotation processor for @ReactModule isn't configured in OSS
                                // yet. For now, hardcode this information, though it's not necessary for most
                                // modules.
                                public ReactModuleInfoProvider getReactModuleInfoProvider() {
                                    return () -> {
                                        final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
                                        if (ReactFeatureFlags.useTurboModules) {
                                            moduleInfos.put(
                                                    NavigationModule.NAME,
                                                    new ReactModuleInfo(
                                                            NavigationModule.NAME,
                                                            NavigationModule.CLASS_NAME,
                                                            false, // canOverrideExistingModule
                                                            false, // needsEagerInit
                                                            true, // hasConstants
                                                            false, // isCxxModule
                                                            true // isTurboModule
                                                    ));
                                        }
                                        return moduleInfos;
                                    };
                                }
                            });
                }

                @Nullable
                @Override
                protected JSIModulePackage getJSIModulePackage() {
                    return (reactApplicationContext, jsContext) -> {
                        final List<JSIModuleSpec> specs = new ArrayList<>();

                        // Install the new native module system.
                        if (ReactFeatureFlags.useTurboModules) {
                            specs.add(
                                    new JSIModuleSpec() {
                                        @Override
                                        public JSIModuleType getJSIModuleType() {
                                            return JSIModuleType.TurboModuleManager;
                                        }

                                        @Override
                                        public JSIModuleProvider getJSIModuleProvider() {
                                            return () -> {
                                                final ReactInstanceManager reactInstanceManager =
                                                        getReactInstanceManager();
                                                final List<ReactPackage> packages = reactInstanceManager.getPackages();

                                                return new TurboModuleManager(
                                                        jsContext,
                                                        new RNNTurboModuleManagerDelegate(
                                                                reactApplicationContext, packages),
                                                        reactApplicationContext
                                                                .getCatalystInstance()
                                                                .getJSCallInvokerHolder(),
                                                        reactApplicationContext
                                                                .getCatalystInstance()
                                                                .getNativeCallInvokerHolder());
                                            };
                                        }
                                    });
                        }


                        return specs;
                    };
                }
            };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SoLoader.init(this, false);
        reactGateway = createReactGateway();
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    /**
     * Subclasses of NavigationApplication may override this method to create the singleton instance
     * of {@link ReactGateway}. For example, subclasses may wish to provide a custom {@link ReactNativeHost}
     * with the ReactGateway. This method will be called exactly once, in the application's {@link #onCreate()} method.
     * <p>
     * Custom {@link ReactNativeHost}s must be sure to include {@link com.reactnativenavigation.react.NavigationPackage}
     *
     * @return a singleton {@link ReactGateway}
     */
    protected ReactGateway createReactGateway() {
        return new ReactGateway(getReactNativeHost());
    }

    public ReactGateway getReactGateway() {
        return reactGateway;
    }

    /**
     * Generally no need to override this; override for custom permission handling.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    /**
     * Register a native View which can be displayed using the given {@code name}
     *
     * @param name    Unique name used to register the native view
     * @param creator Used to create the view at runtime
     */
    @SuppressWarnings("unused")
    public void registerExternalComponent(String name, ExternalComponentCreator creator) {
        if (externalComponents.containsKey(name)) {
            throw new RuntimeException("A component has already been registered with this name: " + name);
        }
        externalComponents.put(name, creator);
    }

    public final Map<String, ExternalComponentCreator> getExternalComponents() {
        return externalComponents;
    }
}
