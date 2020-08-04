//
//  MyTurboModule.cpp
//  TurboModulePlayground
//
//  Created by Watcharachai Kanjaikaew on 22/12/2562 BE.
//  Copyright © 2562 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RNNTurboModule.h"
#import "RNNComponentViewCreator.h"
#import "RNNModalManager.h"
#import "RNNControllerFactory.h"
#import "ReactNativeNavigation.h"
#import "RNNReactRootViewCreator.h"
#import "RNNCommandsHandler.h"
#import "RNNModalManager.h"
#import "Constants.h"

using namespace facebook::react;

@implementation RNNTurboModule {
    RNNModalManager* _modalManager;
    RNNReactComponentRegistry* _componentRegistry;
    RNNCommandsHandler* _commandsHandler;
    RNNExternalComponentStore* _store;
    RNNOverlayManager *_overlayManager;
}

- (instancetype)initWithBridge:(RCTBridge *)bridge mainWindow:(UIWindow *)mainWindow nativeComponentStore:(id)nativeComponentStore {
    self = [super init];
    _store = nativeComponentStore;
    RNNEventEmitter* eventEmitter = [ReactNativeNavigation getEventEmitter];
    RNNModalManagerEventHandler* modalManagerEventHandler = [[RNNModalManagerEventHandler alloc] initWithEventEmitter:eventEmitter];
    _modalManager = [[RNNModalManager alloc] initWithBridge:bridge eventHandler:modalManagerEventHandler];
    _overlayManager = [[RNNOverlayManager alloc] init];
    id<RNNComponentViewCreator> rootViewCreator = [[RNNReactRootViewCreator alloc] initWithBridge:bridge eventEmitter:eventEmitter];
    _componentRegistry = [[RNNReactComponentRegistry alloc] initWithCreator:rootViewCreator];
    RNNControllerFactory *controllerFactory = [[RNNControllerFactory alloc] initWithRootViewCreator:rootViewCreator eventEmitter:eventEmitter store:_store componentRegistry:_componentRegistry andBridge:bridge bottomTabsAttachModeFactory:[BottomTabsAttachModeFactory new]];

    _commandsHandler = [[RNNCommandsHandler alloc] initWithControllerFactory:controllerFactory eventEmitter:eventEmitter modalManager:_modalManager overlayManager:_overlayManager mainWindow:mainWindow];
    [_commandsHandler setReadyToReceiveCommands:true];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(onJavaScriptLoaded)
                                                 name:RCTJavaScriptDidLoadNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(onJavaScriptWillLoad)
                                                 name:RCTJavaScriptWillStartLoadingNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(onBridgeWillReload)
                                                 name:RCTBridgeWillReloadNotification
                                               object:nil];
    return self;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModuleWithJsInvoker:(std::shared_ptr<facebook::react::CallInvoker>)jsInvoker {
    return std::make_shared<NativeRNNTurboModuleSpecJSI>(self, jsInvoker);
}

RCT_EXPORT_METHOD(setRoot:(NSString*)commandId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler setRoot:layout commandId:commandId completion:^(NSString* componentId) {
            resolve(componentId);
        }];
    });
}

RCT_EXPORT_METHOD(mergeOptions:(NSString*)componentId options:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler mergeOptions:componentId options:options completion:^{
            resolve(componentId);
        }];
    });
}

RCT_EXPORT_METHOD(setDefaultOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler setDefaultOptions:options completion:^{
            resolve(nil);
        }];
    });
}

RCT_EXPORT_METHOD(push:(NSString*)commandId componentId:(NSString*)componentId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler push:componentId commandId:commandId layout:layout completion:^(NSString* pushedComponentId) {
            resolve(pushedComponentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(pop:(NSString*)commandId componentId:(NSString*)componentId mergeOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler pop:componentId commandId:commandId mergeOptions:(NSDictionary*)options completion:^{
            resolve(componentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(setStackRoot:(NSString*)commandId componentId:(NSString*)componentId children:(NSArray*)children resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler setStackRoot:componentId commandId:commandId children:children completion:^{
            resolve(componentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(popTo:(NSString*)commandId componentId:(NSString*)componentId mergeOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler popTo:componentId commandId:commandId mergeOptions:options completion:^{
            resolve(componentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(popToRoot:(NSString*)commandId componentId:(NSString*)componentId mergeOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler popToRoot:componentId commandId:commandId mergeOptions:options completion:^{
            resolve(componentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(showModal:(NSString*)commandId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler showModal:layout commandId:commandId completion:^(NSString *componentId) {
            resolve(componentId);
        }];
    });
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)commandId componentId:(NSString*)componentId mergeOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler dismissModal:componentId commandId:commandId mergeOptions:options completion:^(NSString *componentId) {
            resolve(componentId);
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(dismissAllModals:(NSString*)commandId mergeOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler dismissAllModals:options commandId:commandId completion:^{
            resolve(nil);
        }];
    });
}

RCT_EXPORT_METHOD(showOverlay:(NSString*)commandId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler showOverlay:layout commandId:commandId completion:^(NSString * _Nonnull componentId) {
            resolve(componentId);
        }];
    });
}

RCT_EXPORT_METHOD(dismissOverlay:(NSString*)commandId componentId:(NSString*)componentId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        [self->_commandsHandler dismissOverlay:componentId commandId:commandId completion:^{
            resolve(@(1));
        } rejection:reject];
    });
}

RCT_EXPORT_METHOD(getLaunchArgs:(NSString*)commandId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSArray* args = [[NSProcessInfo processInfo] arguments];
    resolve(args);
}

RCT_EXPORT_METHOD(getNavigationConstants:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTExecuteOnMainQueue(^{
        resolve([Constants getConstants]);
    });
}

# pragma mark - JavaScript & Bridge Notifications

- (void)onJavaScriptWillLoad {
    [_componentRegistry clear];
}

- (void)onJavaScriptLoaded {
    [_commandsHandler setReadyToReceiveCommands:true];
    [[[ReactNativeNavigation getBridge] moduleForClass:[RNNEventEmitter class]] sendOnAppLaunched];
}

- (void)onBridgeWillReload {
    [_overlayManager dismissAllOverlays];
    [_modalManager dismissAllModalsSynchronosly];
    [_componentRegistry clear];
    UIApplication.sharedApplication.delegate.window.rootViewController = nil;
}

@end
