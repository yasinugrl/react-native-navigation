import _ from "lodash";
import LayoutNode from "./LayoutNode";

const remx = require('remx');

const state = remx.state({
  layout: {},
  modals: []
});

const setters = remx.setters({
  setLayout(layout: LayoutNode) {
    state.layout = layout;
  },
  push(layoutId: string, child: LayoutNode) {
    findStack(layoutId, state.layout).children.push(child);
  },
  pop(layoutId: string) {
    return getters.getStack(layoutId).children.pop();
  },
  showModal(modal: LayoutNode) {
    state.modals.push(modal);
  }
});

const getters = remx.getters({
  getLayout() {
    return state.layout;
  },
  getModals() {
    return state.modals;
  },
  getLayoutById(layoutId: string) {
    return (findLayoutNode(layoutId, state.layout) || _.find(state.modals, (layout) => findLayoutNode(layoutId, layout)));
  },
  getLayoutChildren(layoutId: string) {
    return getters.getLayoutById(layoutId).children;
  },
  getStack(layoutId: string) {
    return findStack(layoutId, state.layout) || _.find(state.modals, (layout) => findStack(layoutId, layout));
  },
});

function findLayoutNode(layoutId: string, layout: LayoutNode): any | LayoutNode {
  if (layoutId === layout.nodeId) {
    return layout;
  } else {
    for (let i = 0; i < layout.children.length; i += 1) {
      const child = layout.children[i];
      const result = findLayoutNode(layoutId, child);

      if (result !== false) {
        return result;
      }
    }
  }

  return false;
}

function findStack(layoutId: string, layout: LayoutNode): any | LayoutNode {
  if (layout.type === 'Stack' && _.find(layout.children, (child) => child.nodeId === layoutId)) {
    return layout;
  } else {
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
