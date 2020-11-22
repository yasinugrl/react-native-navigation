//
// Created by Oren Zakay on 08/10/2020.
//

#include <RNNTurboModuleSpec.h>

namespace RNN {

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_getLaunchArgs(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "getLaunchArgs",
         "(Ljava/lang/String;)Lcom/facebook/react/bridge/WritableMap;", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_getNavigationConstants(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "getNavigationConstants",
         "()Lcom/facebook/react/bridge/WritableMap;", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_setRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
          PromiseKind,
          "setRoot",
          "(Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V",
          args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_setDefaultOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "setDefaultOptions",
         "(Lcom/facebook/react/bridge/ReadableMap;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_mergeOptions(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "mergeOptions",
         "(Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_push(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "push",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_setStackRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "setStackRoot",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableArray;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_pop(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "pop",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_popTo(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "popTo",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_popToRoot(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "popToRoot",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_showModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "showModal",
         "(Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_dismissModal(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "dismissModal",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_dismissAllModals(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "dismissAllModals",
         "(Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_showOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "showOverlay",
         "(Ljava/lang/String;Lcom/facebook/react/bridge/ReadableMap;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

static facebook::jsi::Value __hostFunction_RNNTurboModuleSpec_dismissOverlay(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
  return static_cast<JavaTurboModule &>(turboModule)
         .invokeJavaMethod(rt,
         PromiseKind,
         "dismissOverlay",
         "(Ljava/lang/String;Ljava/lang/String;Lcom/facebook/react/bridge/Promise;)V", args, count);
}

RNNTurboModuleSpec::RNNTurboModuleSpec(const JavaTurboModule::InitParams &params): JavaTurboModule(params) {
  methodMap_["setRoot"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_setRoot};
  methodMap_["setDefaultOptions"] = MethodMetadata {1, __hostFunction_RNNTurboModuleSpec_setDefaultOptions};
  methodMap_["mergeOptions"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_mergeOptions};
  methodMap_["push"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_push};
  methodMap_["pop"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_pop};
  methodMap_["popTo"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_popTo};
  methodMap_["popToRoot"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_popToRoot};
  methodMap_["setStackRoot"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_setStackRoot};
  methodMap_["showModal"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_showModal};
  methodMap_["dismissModal"] = MethodMetadata {3, __hostFunction_RNNTurboModuleSpec_dismissModal};
  methodMap_["dismissAllModals"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_dismissAllModals};
  methodMap_["showOverlay"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_showOverlay};
  methodMap_["dismissOverlay"] = MethodMetadata {2, __hostFunction_RNNTurboModuleSpec_dismissOverlay};
  methodMap_["getLaunchArgs"] = MethodMetadata {1, __hostFunction_RNNTurboModuleSpec_getLaunchArgs};
}

std::shared_ptr<TurboModule> RNNTurboModuleSpec_ModuleProvider(
    const std::string moduleName,
    const JavaTurboModule::InitParams &params) {
  if (moduleName == "RNNTurboModule") {
    return std::make_shared<RNNTurboModuleSpec>(params);
  }
  return nullptr;
}
} // namespace RNN
