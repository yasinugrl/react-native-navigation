import ParentNode from "./ParentNode";

export default class ComponentNode extends ParentNode {
    constructor(layout: any, parentNode?: ParentNode) {
        super(layout, 'Component', parentNode);
    }

    getVisibleLayout() {
        return this;
    }
}