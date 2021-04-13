import ParentNode from './ParentNode';
import Node from './Node';

export default class StackNode extends ParentNode {
    constructor(layout: any, parentNode?: Node) {
        super(layout, 'Stack', parentNode);
    }

    getVisibleLayout() {
        return this.children[this.children.length - 1].getVisibleLayout();
    }
}