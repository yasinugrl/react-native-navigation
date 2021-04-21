import store from './LayoutStore';
import LayoutNodeFactory from './Layouts/LayoutNodeFactory';
import { LayoutNode } from 'react-native-navigation/commands/LayoutTreeCrawler';

export class NativeCommandsSender {
  constructor() {}

  setRoot(_commandId: string, layout: { root: any; modals: any[]; overlays: any[] }) {
    const layoutNode = LayoutNodeFactory.create(layout.root);
    store.setters.setRoot(layoutNode);
  }

  setDefaultOptions(_options: object) {
    // return this.nativeCommandsModule.setDefaultOptions(options);
  }

  mergeOptions(componentId: string, options: object) {
    store.setters.mergeOptions(componentId, options);
    // return this.nativeCommandsModule.mergeOptions(componentId, options);
  }

  push(_commandId: string, onComponentId: string, layout: LayoutNode) {
    return new Promise((resolve) => {
      store.setters.push(onComponentId, layout);
      resolve(layout.id);
    });
  }

  pop(_commandId: string, componentId: string, _options?: object) {
    return new Promise((resolve) => {
      store.setters.pop(componentId);
      resolve(componentId);
    });
  }

  popTo(_commandId: string, componentId: string, _options?: object) {
    return new Promise((resolve) => {
      store.setters.popTo(componentId);
      resolve(componentId);
    });
  }

  popToRoot(_commandId: string, componentId: string, _options?: object) {
    store.setters.popToRoot(componentId);
  }

  setStackRoot(_commandId: string, onComponentId: string, layout: object) {
    store.setters.setStackRoot(onComponentId, layout);
  }

  showModal(_commandId: string, layout: object) {
    return new Promise((resolve) => {
      const layoutNode = LayoutNodeFactory.create(layout);
      store.setters.showModal(layoutNode);
      resolve(layoutNode.nodeId);
    });
  }

  dismissModal(_commandId: string, componentId: string, _options?: object) {
    return new Promise((resolve) => {
      const modal = store.getters.getModalById(componentId).getTopParent();
      store.setters.dismissModal(componentId);
      resolve(modal.nodeId);
    });
  }

  dismissAllModals(_commandId: string, _options?: object) {
    store.setters.dismissAllModals();
  }

  showOverlay(_commandId: string, layout: object) {
    const layoutNode = LayoutNodeFactory.create(layout);
    store.setters.showOverlay(layoutNode);
  }

  dismissOverlay(_commandId: string, componentId: string) {
    store.setters.dismissOverlay(componentId);
    // return this.nativeCommandsModule.dismissOverlay(commandId, componentId);
  }

  dismissAllOverlays(_commandId: string) {
    store.setters.dismissAllOverlays();
    // return this.nativeCommandsModule.dismissAllOverlays(commandId);
  }

  getLaunchArgs(_commandId: string) {
    // return this.nativeCommandsModule.getLaunchArgs(commandId);
  }
}
