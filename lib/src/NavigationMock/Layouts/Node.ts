import { Options } from '../../index';
import ParentNode from './ParentNode';

interface Data {
  options: Options;
  name: string;
}

export type NodeType =
  | 'Component'
  | 'ExternalComponent'
  | 'Stack'
  | 'BottomTabs'
  | 'TopTabs'
  | 'SideMenuRoot'
  | 'SideMenuLeft'
  | 'SideMenuRight'
  | 'SideMenuCenter'
  | 'SplitView';

export default class Node {
  readonly nodeId: string;
  readonly data: Data;
  readonly type: NodeType;
  parentNode?: ParentNode;

  constructor(layout: any, type: NodeType, parentNode?: ParentNode) {
    this.nodeId = layout.id;
    this.data = layout.data;
    this.parentNode = parentNode;
    this.type = type;
  }

  resolveOptions(): Options {
    return this.data.options;
  }

  getStack() {
    return this.parentNode?.getStack();
  }
}
