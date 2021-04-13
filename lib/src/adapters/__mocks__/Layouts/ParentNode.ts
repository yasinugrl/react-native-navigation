import _ from "lodash";
import { Options } from "../../../index";
import LayoutNodeFactory from "./LayoutNodeFactory";
import Node, { NodeType } from "./Node";

export default class ParentNode extends Node {
    children: [ParentNode];

    constructor(layout: any, type: NodeType, parentNode?: Node) {
        super(layout, type, parentNode);
        this.children = layout.children.map((childLayout: any) => LayoutNodeFactory.create(childLayout, this));
    }

    getVisibleLayout(): Node {
        return this.children[this.children.length - 1].getVisibleLayout();
    }

    resolveOptions(): Options {
        return _.merge(_.clone(this.data.options), this.getVisibleLayout().data.options);
    }
}