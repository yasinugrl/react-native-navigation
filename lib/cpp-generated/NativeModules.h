
/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#pragma once

#include <ReactCommon/TurboModule.h>

namespace facebook {
namespace react {

class JSI_EXPORT NativeRNNTurboModuleCxxSpecJSI : public TurboModule {
protected:
  NativeRNNTurboModuleCxxSpecJSI(std::shared_ptr<CallInvoker> jsInvoker);

public:
virtual jsi::Value setRoot(jsi::Runtime &rt, const jsi::String &commandId, const jsi::Object &layout) = 0;
virtual void setDefaultOptions(jsi::Runtime &rt, const jsi::Object &options) = 0;
virtual void mergeOptions(jsi::Runtime &rt, const jsi::String &componentId, const jsi::Object &options) = 0;
virtual jsi::Value push(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &onComponentId, const jsi::Object &layout) = 0;
virtual jsi::Value pop(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &componentId, const jsi::Object &options) = 0;
virtual jsi::Value popTo(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &componentId, const jsi::Object &options) = 0;
virtual jsi::Value popToRoot(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &componentId, const jsi::Object &options) = 0;
virtual jsi::Value setStackRoot(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &onComponentId, const jsi::Object &layout) = 0;
virtual jsi::Value showModal(jsi::Runtime &rt, const jsi::String &commandId, const jsi::Object &layout) = 0;
virtual jsi::Value dismissModal(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &componentId, const jsi::Object &options) = 0;
virtual jsi::Value dismissAllModals(jsi::Runtime &rt, const jsi::String &commandId, const jsi::Object &options) = 0;
virtual jsi::Value showOverlay(jsi::Runtime &rt, const jsi::String &commandId, const jsi::Object &layout) = 0;
virtual jsi::Value dismissOverlay(jsi::Runtime &rt, const jsi::String &commandId, const jsi::String &componentId) = 0;
virtual jsi::Value getLaunchArgs(jsi::Runtime &rt, const jsi::String &commandId) = 0;

};

} // namespace react
} // namespace facebook
