#import "AppDelegate.h"

#import <React/JSCExecutorFactory.h>
#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTCxxBridgeDelegate.h>
#import <React/RCTImageLoader.h>
#import <React/RCTLocalAssetImageLoader.h>
#import <React/RCTGIFImageDecoder.h>
#import <React/RCTNetworking.h>
#import <React/RCTHTTPRequestHandler.h>
#import <React/RCTDataRequestHandler.h>
#import <React/RCTFileRequestHandler.h>
#import <ReactCommon/RCTTurboModuleManager.h>

#import <cxxreact/JSExecutor.h>
#import <ReactNativeNavigation/RNNTurboModuleProvider.h>
#import <ReactNativeNavigation/RNNTurboModule.h>
#import <ReactNativeNavigation/ReactNativeNavigation.h>
#import "RNNCustomViewController.h"

@interface AppDelegate() <RCTCxxBridgeDelegate, RCTTurboModuleManagerDelegate> {
  RCTTurboModuleManager *_turboModuleManager;
}

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
	RCTTurboModuleEnabled();
	[ReactNativeNavigation bootstrapWithDelegate:self launchOptions:launchOptions];
	[ReactNativeNavigation registerExternalComponent:@"RNNCustomComponent" callback:^UIViewController *(NSDictionary *props, RCTBridge *bridge) {
		return [[RNNCustomViewController alloc] initWithProps:props];
	}];
	
	return YES;
}

# pragma mark - RCTBridgeDelegate

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
#if DEBUG
  return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
#else
  return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif
}

- (NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	return [ReactNativeNavigation extraModulesForBridge:bridge];
}

# pragma mark - RCTCxxBridgeDelegate

- (std::unique_ptr<facebook::react::JSExecutorFactory>)jsExecutorFactoryForBridge:(RCTBridge *)bridge
{
	_turboModuleManager = [[RCTTurboModuleManager alloc] initWithBridge:bridge delegate:self];
	__weak __typeof(self) weakSelf = self;
	return std::make_unique<facebook::react::JSCExecutorFactory>([weakSelf, bridge](facebook::jsi::Runtime &runtime) {
	  if (!bridge) {
		return;
	  }
	  __typeof(self) strongSelf = weakSelf;
	  if (strongSelf) {
		[strongSelf->_turboModuleManager installJSBindingWithRuntime:&runtime];
	  }
	});
}

#pragma mark RCTTurboModuleManagerDelegate

- (Class)getModuleClassFromName:(const char *)name
{
  return facebook::react::RNNTurboModuleClassProvider(name);
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const std::string &)name instance:(id<RCTTurboModule>)instance jsInvoker:(std::shared_ptr<facebook::react::CallInvoker>)jsInvoker {
	return facebook::react::RNNTurboModuleProvider(name, instance, jsInvoker);
}

- (id<RCTTurboModule>)getModuleInstanceFromClass:(Class)moduleClass
{
  if (moduleClass == RCTImageLoader.class) {
    return [[moduleClass alloc] initWithRedirectDelegate:nil loadersProvider:^NSArray<id<RCTImageURLLoader>> *{
      return @[[RCTLocalAssetImageLoader new]];
    } decodersProvider:^NSArray<id<RCTImageDataDecoder>> *{
      return @[[RCTGIFImageDecoder new]];
    }];
  } else if (moduleClass == RCTNetworking.class) {
    return [[moduleClass alloc] initWithHandlersProvider:^NSArray<id<RCTURLRequestHandler>> *{
      return @[
        [RCTHTTPRequestHandler new],
        [RCTDataRequestHandler new],
        [RCTFileRequestHandler new],
      ];
    }];
  } else if (moduleClass == RNNTurboModule.class) {
	  return [ReactNativeNavigation createTurboModule];
  }
  // No custom initializer here.
  return [moduleClass new];
}

@end
