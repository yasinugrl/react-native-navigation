/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @flow
 * @format
 */

// 'use strict';

// @ts-ignore
import { TurboModuleRegistry } from 'react-native';

export interface NativeCommandsModule {
  setRoot(commandId: string, layout: { root: any; modals: any[]; overlays: any[] }): Promise<any>;
  setDefaultOptions(options: object): void;
  mergeOptions(componentId: string, options: object): void;
  push(commandId: string, onComponentId: string, layout: object): Promise<any>;
  pop(commandId: string, componentId: string, options?: object): Promise<any>;
  popTo(commandId: string, componentId: string, options?: object): Promise<any>;
  popToRoot(commandId: string, componentId: string, options?: object): Promise<any>;
  setStackRoot(commandId: string, onComponentId: string, layout: object): Promise<any>;
  showModal(commandId: string, layout: object): Promise<any>;
  dismissModal(commandId: string, componentId: string, options?: object): Promise<any>;
  dismissAllModals(commandId: string, options?: object): Promise<any>;
  showOverlay(commandId: string, layout: object): Promise<any>;
  dismissOverlay(commandId: string, componentId: string): Promise<any>;
  getLaunchArgs(commandId: string): Promise<any>;
}

let NativeTurboModule;
// Temp fix for unit tests
try {
  NativeTurboModule = TurboModuleRegistry.getEnforcing('RNNTurboModule');
} catch (error) {}
export default NativeTurboModule as NativeCommandsModule;
