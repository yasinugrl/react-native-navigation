
/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#include <react/modules/RNNTurboModule/NativeModules.h>

namespace facebook {
namespace react {

static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setRoot(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->setRoot(rt, args[0].getString(rt), args[1].getObject(rt));
}

static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setDefaultOptions(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->setDefaultOptions(rt, args[0].getObject(rt));
  return jsi::Value::undefined();
}

static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_mergeOptions(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->mergeOptions(rt, args[0].getString(rt), args[1].getObject(rt));
  return jsi::Value::undefined();
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_push(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->push(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_pop(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->pop(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_popTo(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->popTo(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_popToRoot(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->popToRoot(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setStackRoot(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->setStackRoot(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_showModal(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->showModal(rt, args[0].getString(rt), args[1].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissModal(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->dismissModal(rt, args[0].getString(rt), args[1].getString(rt), args[2].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissAllModals(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->dismissAllModals(rt, args[0].getString(rt), args[1].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_showOverlay(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->showOverlay(rt, args[0].getString(rt), args[1].getObject(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissOverlay(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->dismissOverlay(rt, args[0].getString(rt), args[1].getString(rt));
}
static jsi::Value __hostFunction_NativeRNNTurboModuleCxxSpecJSI_getLaunchArgs(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeRNNTurboModuleCxxSpecJSI *>(&turboModule)->getLaunchArgs(rt, args[0].getString(rt));
}

NativeRNNTurboModuleCxxSpecJSI::NativeRNNTurboModuleCxxSpecJSI(std::shared_ptr<CallInvoker> jsInvoker)
  : TurboModule("RNNTurboModule", jsInvoker) {
  methodMap_["setRoot"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setRoot};
  methodMap_["setDefaultOptions"] = MethodMetadata {1, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setDefaultOptions};
  methodMap_["mergeOptions"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_mergeOptions};
  methodMap_["push"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_push};
  methodMap_["pop"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_pop};
  methodMap_["popTo"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_popTo};
  methodMap_["popToRoot"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_popToRoot};
  methodMap_["setStackRoot"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_setStackRoot};
  methodMap_["showModal"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_showModal};
  methodMap_["dismissModal"] = MethodMetadata {3, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissModal};
  methodMap_["dismissAllModals"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissAllModals};
  methodMap_["showOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_showOverlay};
  methodMap_["dismissOverlay"] = MethodMetadata {2, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_dismissOverlay};
  methodMap_["getLaunchArgs"] = MethodMetadata {1, __hostFunction_NativeRNNTurboModuleCxxSpecJSI_getLaunchArgs};
}


} // namespace react
} // namespace facebook
