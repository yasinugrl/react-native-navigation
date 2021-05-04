import store from './LayoutStore';
import LayoutNodeFactory from './Layouts/LayoutNodeFactory';
import { LayoutNode } from 'react-native-navigation/commands/LayoutTreeCrawler';
import { events } from './EventsStore';
import _ from 'lodash';
import ComponentNode from './Layouts/ComponentNode';

export class NativeCommandsSender {
  constructor() { }

  setRoot(_commandId: string, layout: { root: any; modals: any[]; overlays: any[] }) {
    return new Promise((resolve) => {
      if (store.getters.getVisibleLayout()) {
        store.getters.getVisibleLayout().componentDidDisappear();
        store.setters.setRoot({});
      }

      const layoutNode = LayoutNodeFactory.create(layout.root);
      store.setters.setRoot(layoutNode);
      layoutNode.getVisibleLayout().componentDidAppear();
      resolve(layout.root.nodeId);
    });
  }

  setDefaultOptions(options: object) {
    store.setDefaultOptions(options);
    // return this.nativeCommandsModule.setDefaultOptions(options);
  }

  mergeOptions(componentId: string, options: object) {
    store.setters.mergeOptions(componentId, options);
    // return this.nativeCommandsModule.mergeOptions(componentId, options);
  }

  push(_commandId: string, onComponentId: string, layout: LayoutNode) {
    return new Promise((resolve) => {
      const stack = store.getters.getLayoutById(onComponentId).getStack();
      const layoutNode = LayoutNodeFactory.create(layout, stack);
      stack.getVisibleLayout().componentDidDisappear();
      store.setters.push(layoutNode, stack);
      stack.getVisibleLayout().componentDidAppear();
      resolve(stack.getVisibleLayout().nodeId);
    });

  }

  pop(_commandId: string, componentId: string, _options?: object) {
    return new Promise((resolve) => {
      const poppedChild = _.last(store.getters.getLayoutById(componentId).getStack().children) as ComponentNode;
      store.setters.pop(componentId);
      console.log(poppedChild.nodeId);

      resolve(poppedChild.nodeId);
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
      store.getters.getVisibleLayout().componentDidDisappear();
      store.setters.showModal(layoutNode);
      layoutNode.componentDidAppear();
      resolve(layoutNode.nodeId);
    });
  }

  dismissModal(_commandId: string, componentId: string, _options?: object) {
    return new Promise((resolve) => {
      const modal = store.getters.getModalById(componentId).getTopParent();
      modal.componentDidDisappear();
      store.setters.dismissModal(componentId);
      events.invokeModalDismissed({
        componentName: modal.data.name,
        componentId: modal.nodeId,
        modalsDismissed: 1,
      });
      resolve(modal.nodeId);
      store.getters.getVisibleLayout().componentDidAppear();
    });
  }

  dismissAllModals(_commandId: string, _options?: object) {
    store.setters.dismissAllModals();
  }

  showOverlay(_commandId: string, layout: object) {
    const layoutNode = LayoutNodeFactory.create(layout);
    store.setters.showOverlay(layoutNode);
    layoutNode.componentDidAppear();
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
