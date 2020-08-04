
/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#include "RNNTurboModuleSpec.h"

namespace facebook {
namespace react {

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "setRoot", @selector(setRoot:layout:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_mergeOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "mergeOptions", @selector(mergeOptions:options:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setDefaultOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "setDefaultOptions", @selector(setDefaultOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_push(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "push", @selector(push:componentId:layout:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_pop(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "pop", @selector(pop:componentId:mergeOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_setStackRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "setStackRoot", @selector(setStackRoot:componentId:children:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_popTo(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "popTo", @selector(popTo:componentId:mergeOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_popToRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "popToRoot", @selector(popToRoot:componentId:mergeOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_showModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "showModal", @selector(showModal:layout:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissModal", @selector(dismissModal:componentId:mergeOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissAllModals(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissAllModals", @selector(dismissAllModals:mergeOptions:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_showOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "showOverlay", @selector(showOverlay:layout:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_dismissOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "dismissOverlay", @selector(dismissOverlay:componentId:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_getLaunchArgs(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "getLaunchArgs", @selector(getLaunchArgs:resolver:rejecter:), args, count);
}

static facebook::jsi::Value __hostFunction_NativeRNNTurboModuleSpecJSI_getNavigationConstants(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<ObjCTurboModule &>(turboModule)
         .invokeObjCMethod(rt, PromiseKind, "getNavigationConstants", @selector(getNavigationConstants:rejecter:), args, count);
}

NativeRNNTurboModuleSpecJSI::NativeRNNTurboModuleSpecJSI(id<RCTTurboModule> instance, std::shared_ptr<CallInvoker> jsInvoker)
  : ObjCTurboModule("RNNTurboModule", instance, jsInvoker) {
      methodMap_["setRoot"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_setRoot};
      methodMap_["mergeOptions"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_mergeOptions};
      methodMap_["setDefaultOptions"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_setDefaultOptions};
      methodMap_["push"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_push};
      methodMap_["pop"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_pop};
      methodMap_["setStackRoot"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_setStackRoot};
      methodMap_["popTo"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_popTo};
      methodMap_["popToRoot"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_popToRoot};
      methodMap_["showModal"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_showModal};
      methodMap_["dismissModal"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissModal};
      methodMap_["dismissAllModals"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissAllModals};
      methodMap_["showOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_showOverlay};
      methodMap_["dismissOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_dismissOverlay};
      methodMap_["getLaunchArgs"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_getLaunchArgs};
      methodMap_["getNavigationConstants"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleSpecJSI_getNavigationConstants};
}

} // namespace react
} // namespace facebook
