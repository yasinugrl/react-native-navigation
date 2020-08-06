
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




namespace JS {
  namespace NativeRNNTurboModule {
    struct SpecSetRootLayout {
      id<NSObject> root() const;
      facebook::react::LazyVector<id<NSObject>> modals() const;
      facebook::react::LazyVector<id<NSObject>> overlays() const;

      SpecSetRootLayout(NSDictionary *const v) : _v(v) {}
    private:
      NSDictionary *_v;
    };
  }
}

@interface RCTCxxConvert (NativeRNNTurboModule_SpecSetRootLayout)
+ (RCTManagedPointer *)JS_NativeRNNTurboModule_SpecSetRootLayout:(id)json;
@end

inline id<NSObject> JS::NativeRNNTurboModule::SpecSetRootLayout::root() const
{
  id const p = _v[@"root"];
  return p;
}


inline facebook::react::LazyVector<id<NSObject>> JS::NativeRNNTurboModule::SpecSetRootLayout::modals() const
{
  id const p = _v[@"modals"];
  return RCTBridgingToVec(p, ^id<NSObject>(id itemValue_0) { return itemValue_0; });
}


inline facebook::react::LazyVector<id<NSObject>> JS::NativeRNNTurboModule::SpecSetRootLayout::overlays() const
{
  id const p = _v[@"overlays"];
  return RCTBridgingToVec(p, ^id<NSObject>(id itemValue_0) { return itemValue_0; });
}



@protocol NativeRNNTurboModuleSpec <RCTBridgeModule, RCTTurboModule>
- (void) setRoot:(NSString *)commandId
   layout:(JS::NativeRNNTurboModule::SpecSetRootLayout&)layout
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) setDefaultOptions:(NSDictionary *)options;
- (void) mergeOptions:(NSString *)componentId
   options:(NSDictionary *)options;
- (void) push:(NSString *)commandId
   onComponentId:(NSString *)onComponentId
   layout:(NSDictionary *)layout
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) pop:(NSString *)commandId
   componentId:(NSString *)componentId
   options:(NSDictionary *)options
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) popTo:(NSString *)commandId
   componentId:(NSString *)componentId
   options:(NSDictionary *)options
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) popToRoot:(NSString *)commandId
   componentId:(NSString *)componentId
   options:(NSDictionary *)options
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) setStackRoot:(NSString *)commandId
   onComponentId:(NSString *)onComponentId
   layout:(NSDictionary *)layout
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) showModal:(NSString *)commandId
   layout:(NSDictionary *)layout
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) dismissModal:(NSString *)commandId
   componentId:(NSString *)componentId
   options:(NSDictionary *)options
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) dismissAllModals:(NSString *)commandId
   options:(NSDictionary *)options
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) showOverlay:(NSString *)commandId
   layout:(NSDictionary *)layout
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) dismissOverlay:(NSString *)commandId
   componentId:(NSString *)componentId
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
- (void) getLaunchArgs:(NSString *)commandId
   resolve:(RCTPromiseResolveBlock)resolve
   reject:(RCTPromiseRejectBlock)reject;
@end


namespace facebook {
namespace react {

class JSI_EXPORT NativeRNNTurboModuleSpecJSI : public ObjCTurboModule {
public:
  NativeRNNTurboModuleSpecJSI(id<RCTTurboModule> instance, std::shared_ptr<CallInvoker> jsInvoker, std::shared_ptr<CallInvoker> nativeInvoker, id<RCTTurboModulePerformanceLogger> perfLogger);
};

} // namespace react
} // namespace facebook
