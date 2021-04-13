import Node from "./Node";

export default class ComponentNode extends Node {
    constructor(layout: any, parentNode?: Node) {
        super(layout, 'Component', parentNode);
    }

    getVisibleLayout() {
        return this;
    }
}