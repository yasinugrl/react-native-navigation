import LayoutNode from "./LayoutNode";

const remx = require('remx');

const state = remx.state({
  layout: {},
});

const setters = remx.setters({
  setLayout(layout) {
    state.layout = layout;
  },
  setLayoutChildren(layoutId: string, children) {
    state.layout.children = children;
    // findLayout(layoutId, state.layout).children = children;
    // return findLayout(layoutId, state.layout);
  },
});

const getters = remx.getters({
  getLayout() {
    return state.layout;
  },
  getLayoutById(layoutId: string) {
    return findLayoutNode(layoutId, state.layout);
  },
  getLayoutChildren(layoutId: string) {
    // console.log(state.layout.children[0]);
    return state.layout.children || [];
    // return findLayout(layoutId, state.layout).children;
    // return findLayout(layoutId, state.layout);
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

export default {
  setters,
  getters,
};
