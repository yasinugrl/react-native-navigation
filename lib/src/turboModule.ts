import { TurboModule, TurboModuleRegistry } from 'react-native-tscodegen-types';

export interface ModuleSpec extends TurboModule {
  setRoot(commandId: string, layout: { root: any; modals: any[]; overlays: any[] }): Promise<any>;
  setDefaultOptions(options: Object): void;
  mergeOptions(componentId: string, options: Object): void;
  push(commandId: string, onComponentId: string, layout: Object): Promise<any>;
  pop(commandId: string, componentId: string, options?: Object): Promise<any>;
  popTo(commandId: string, componentId: string, options?: Object): Promise<any>;
  popToRoot(commandId: string, componentId: string, options?: Object): Promise<any>;
  setStackRoot(commandId: string, onComponentId: string, layout: Object): Promise<any>;
  showModal(commandId: string, layout: Object): Promise<any>;
  dismissModal(commandId: string, componentId: string, options?: Object): Promise<any>;
  dismissAllModals(commandId: string, options?: Object): Promise<any>;
  showOverlay(commandId: string, layout: Object): Promise<any>;
  dismissOverlay(commandId: string, componentId: string): Promise<any>;
  getLaunchArgs(commandId: string): Promise<any>;
}

export default TurboModuleRegistry.getEnforcing<ModuleSpec>('RNNTurboModule') as ModuleSpec;
