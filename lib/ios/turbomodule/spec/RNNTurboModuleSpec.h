
/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

// NOTE: This entire file should be codegen'ed.

#import <vector>

#import <Foundation/Foundation.h>

#import <React/RCTBridgeModule.h>

#import <ReactCommon/RCTTurboModule.h>
#import <RCTRequired/RCTRequired.h>
#import <RCTTypeSafety/RCTTypedModuleConstants.h>
#import <React/RCTCxxConvert.h>
#import <React/RCTManagedPointer.h>
#import <RCTTypeSafety/RCTConvertHelpers.h>


@protocol NativeRNNTurboModuleSpec <RCTTurboModule>
- (void)setRoot:(NSString *)commandId
         layout:(NSDictionary *)layout
       resolver:(RCTPromiseResolveBlock)resolve
       rejecter:(RCTPromiseRejectBlock)reject;

- (void)mergeOptions:(NSString *)componentId
             options:(NSDictionary *)options
            resolver:(RCTPromiseResolveBlock)resolve
            rejecter:(RCTPromiseRejectBlock)reject;

- (void)setDefaultOptions:(NSDictionary *)options
            resolver:(RCTPromiseResolveBlock)resolve
            rejecter:(RCTPromiseRejectBlock)reject;

- (void)push:(NSString *)commandId
 componentId:(NSString *)componentId
      layout:(NSDictionary *)layout
    resolver:(RCTPromiseResolveBlock)resolve
    rejecter:(RCTPromiseRejectBlock)reject;

- (void)pop:(NSString *)commandId
componentId:(NSString *)componentId
mergeOptions:(NSDictionary *)options
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)setStackRoot:(NSString *)commandId
componentId:(NSString *)componentId
     children:(NSArray *)children
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)popTo:(NSString *)commandId
componentId:(NSString *)componentId
     mergeOptions:(NSDictionary *)options
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)popToRoot:(NSString *)commandId
componentId:(NSString *)componentId
     mergeOptions:(NSDictionary *)options
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)showModal:(NSString *)commandId
         layout:(NSDictionary *)layout
       resolver:(RCTPromiseResolveBlock)resolve
       rejecter:(RCTPromiseRejectBlock)reject;

- (void)dismissModal:(NSString *)commandId
componentId:(NSString *)componentId
     mergeOptions:(NSDictionary *)options
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)dismissAllModals:(NSString *)commandId
     mergeOptions:(NSDictionary *)options
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)showOverlay:(NSString *)commandId
  layout:(NSDictionary *)layout
resolver:(RCTPromiseResolveBlock)resolve
rejecter:(RCTPromiseRejectBlock)reject;

- (void)dismissOverlay:(NSString *)commandId
componentId:(NSString *)componentId
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)getLaunchArgs:(NSString *)commandId
   resolver:(RCTPromiseResolveBlock)resolve
   rejecter:(RCTPromiseRejectBlock)reject;

- (void)getNavigationConstants:(RCTPromiseResolveBlock)resolve
rejecter:(RCTPromiseRejectBlock)reject;

@end


namespace facebook {
namespace react {

class JSI_EXPORT NativeRNNTurboModuleSpecJSI : public ObjCTurboModule {
public:
  NativeRNNTurboModuleSpecJSI(const ObjCTurboModule::InitParams &params);
};

} // namespace react
} // namespace facebook
