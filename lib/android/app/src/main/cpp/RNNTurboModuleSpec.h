//
// Created by Oren Zakay on 08/10/2020.
//

#pragma once

#include <ReactCommon/JavaTurboModule.h>
#include <ReactCommon/TurboModule.h>
#include <fbjni/fbjni.h>

namespace facebook {
namespace react {

class JSI_EXPORT RNNTurboModuleSpec : public JavaTurboModule {
 public:
  RNNTurboModuleSpec(const JavaTurboModule::InitParams &params);
};

std::shared_ptr<TurboModule> RNNTurboModuleSpec_ModuleProvider(
    const std::string moduleName,
    const JavaTurboModule::InitParams &params);

} // namespace react
} // namespace facebook
