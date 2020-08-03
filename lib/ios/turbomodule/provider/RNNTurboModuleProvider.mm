/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "RNNTurboModuleProvider.h"

#import <React/CoreModulesPlugins.h>
#import "RNNTurboModule.h"

// NOTE: This entire file should be codegen'ed.

namespace facebook {
namespace react {

Class RNNTurboModuleClassProvider(const char *name) {
  return RCTCoreModulesClassProvider(name);
}

std::shared_ptr<TurboModule> RNNTurboModuleProvider(const std::string &name, const ObjCTurboModule::InitParams &params) {
  if (name == "RNNTurboModule") {
    return std::make_shared<NativeRNNTurboModuleSpecJSI>(params);
  }
  return nullptr;
}

} // namespace react
} // namespace facebook
