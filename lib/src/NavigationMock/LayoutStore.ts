import _ from "lodash";
import BottomTabsNode from "./Layouts/BottomTabsNode";
import Node from "./Layouts/Node";
import ParentNode from "./Layouts/ParentNode";
import { events } from './EventsStore';
import LayoutNodeFactory from "./Layouts/LayoutNodeFactory";
import { Options } from "react-native-navigation/interfaces/Options";

const remx = require('remx');

const state = remx.state({
  layout: {},
  modals: [],
  overlays: []
});

const setters = remx.setters({
  setLayout(layout: ParentNode) {
    state.modals = [];
    state.layout = layout;
  },
  push(layoutId: string, layout: any) {
    const stack = getters.getLayoutById(layoutId).getStack();
    const child = LayoutNodeFactory.create(layout, stack);
    const currentChild = stack.getVisibleLayout();
    events.componentDidDisappear({ componentName: currentChild.data.name, componentId: currentChild.nodeId, componentType: 'Component' });
    stack.children.push(child);
  },
  pop(layoutId: string) {
    const child = getters.getLayoutById(layoutId).getStack().children.pop()
    events.componentDidDisappear({ componentName: child.data.name, componentId: child.nodeId, componentType: 'Component' });
    return child;
  },
  popTo(layoutId: string) {
    const stack = getters.getLayoutById(layoutId).getStack();
    while (stack.getVisibleLayout().nodeId != layoutId) {
      stack.children.pop();
    }
  },
  setStackRoot(layoutId: string, layout: any) {
    const stack = getters.getLayoutById(layoutId).getStack();
    stack.children = layout.map((child: any) => LayoutNodeFactory.create(child, stack));;
  },
  showOverlay(overlay: ParentNode) {
    state.overlays.push(overlay);
  },
  showModal(modal: ParentNode) {
    state.modals.push(modal);
  },
  dismissModal(componentId: string) {
    // TODO: Impelment modals behavior
    const modal = getters.getModalById(componentId);
    if (modal) {
      const child = modal.getVisibleLayout();
      events.componentDidDisappear({ componentName: child.data.name, componentId: child.nodeId, componentType: 'Component' });
      _.remove(state.modals, (modal: ParentNode) => modal.nodeId === child.nodeId);
    }
  },
  selectTabIndex(layout: BottomTabsNode, index: number) {
    layout.selectedIndex = index;
  },
  mergeOptions(componentId: string, options: Options) {
    getters.getLayoutById(componentId).data.options = _.merge(getters.getLayoutById(componentId).data.options, options);
    getters.getLayoutById(componentId).id = _.clone(getters.getLayoutById(componentId).id);
  }
});

const getters = remx.getters({
  getLayout() {
    return state.layout;
  },
  getVisibleLayout() {
    if (state.modals.length > 0) return _.last<Node>(state.modals)!.getVisibleLayout();
    else return state.layout.getVisibleLayout();
  },
  isVisibleLayout(layout: Node) {
    return getters.getVisibleLayout() && getters.getVisibleLayout().nodeId === layout.nodeId;
  },
  getModals() {
    return state.modals;
  },
  getOverlays() {
    return state.overlays;
  },
  getLayoutById(layoutId: string) {
    return (findParentNode(layoutId, state.layout) || getters.getModalById(layoutId));
  },
  getModalById(layoutId: string) {
    return _.find(state.modals, (layout) => findParentNode(layoutId, layout));
  },
  getLayoutChildren(layoutId: string) {
    return getters.getLayoutById(layoutId).children;
  },
  getStack(layoutId: string) {
    return findStack(layoutId, state.layout) || _.find(state.modals, (layout) => findStack(layoutId, layout));
  },
});

function findParentNode(layoutId: string, layout: ParentNode): any | ParentNode {
  if (layoutId === layout.nodeId) {
    return layout;
  } else if (layout.children) {
    for (let i = 0; i < layout.children.length; i += 1) {
      const child = layout.children[i];
      const result = findParentNode(layoutId, child);

      if (result !== false) {
        return result;
      }
    }
  }

  return false;
}

function findStack(layoutId: string, layout: ParentNode): any | ParentNode {
  if (layout.type === 'Stack' && _.find(layout.children, (child) => child.nodeId === layoutId)) {
    return layout;
  } else if (layout.children) {
    for (let i = 0; i < layout.children.length; i += 1) {
      const child = layout.children[i];
      const result = findStack(layoutId, child);

      if (result !== false) {
        return result;
      }
    }
  }

  return false;
}

export default {
  setters,
  getters,
};
