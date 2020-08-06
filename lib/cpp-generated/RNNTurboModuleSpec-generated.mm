
/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#include <RNNTurboModuleSpec/RNNTurboModuleSpec.h>
@implementation RCTCxxConvert (NativeRNNTurboModule_SpecSetRootLayout)
+ (RCTManagedPointer *)JS_NativeRNNTurboModule_SpecSetRootLayout:(id)json
{
  return facebook::react::managedPointer<JS::NativeRNNTurboModule::SpecSetRootLayout>(json);
}
@end
namespace facebook {
namespace react {

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "setRoot", @selector(setRoot:layout:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setDefaultOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, VoidKind, "setDefaultOptions", @selector(setDefaultOptions:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_mergeOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, VoidKind, "mergeOptions", @selector(mergeOptions:options:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_push(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "push", @selector(push:onComponentId:layout:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_pop(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "pop", @selector(pop:componentId:options:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_popTo(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "popTo", @selector(popTo:componentId:options:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_popToRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "popToRoot", @selector(popToRoot:componentId:options:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setStackRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "setStackRoot", @selector(setStackRoot:onComponentId:layout:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_showModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "showModal", @selector(showModal:layout:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissModal", @selector(dismissModal:componentId:options:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissAllModals(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissAllModals", @selector(dismissAllModals:options:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_showOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "showOverlay", @selector(showOverlay:layout:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissOverlay", @selector(dismissOverlay:componentId:resolve:reject:), args, count);
}
static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_getLaunchArgs(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "getLaunchArgs", @selector(getLaunchArgs:resolve:reject:), args, count);
}

NativeRNNTurboModuleSpecJSI::NativeRNNTurboModuleSpecJSI(id<RCTTurboModule> instance, std::shared_ptr<CallInvoker> jsInvoker, std::shared_ptr<CallInvoker> nativeInvoker, id<RCTTurboModulePerformanceLogger> perfLogger)
  : ObjCTurboModule("RNNTurboModule", instance, jsInvoker, nativeInvoker, perfLogger) {
  methodMap_["setRoot"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_setRoot};
  methodMap_["setDefaultOptions"] = MethodMetadata {1, __hostFunction_NativeRNNTurboModuleSpecJSI_setDefaultOptions};
  methodMap_["mergeOptions"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_mergeOptions};
  methodMap_["push"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_push};
  methodMap_["pop"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_pop};
  methodMap_["popTo"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_popTo};
  methodMap_["popToRoot"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_popToRoot};
  methodMap_["setStackRoot"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_setStackRoot};
  methodMap_["showModal"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_showModal};
  methodMap_["dismissModal"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissModal};
  methodMap_["dismissAllModals"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissAllModals};
  methodMap_["showOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_showOverlay};
  methodMap_["dismissOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissOverlay};
  methodMap_["getLaunchArgs"] = MethodMetadata {1, __hostFunction_NativeRNNTurboModuleSpecJSI_getLaunchArgs};
  setMethodArgConversionSelector(@"setRoot", 1, @"JS_NativeRNNTurboModule_SpecSetRootLayout:");
}


} // namespace react
} // namespace facebook
