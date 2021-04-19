import _ from "lodash";
import { LayoutStack } from "react-native-navigation/interfaces/Layout";
import { Options } from "../../index";
import LayoutNodeFactory from "./LayoutNodeFactory";
import Node, { NodeType } from "./Node";

export default class ParentNode extends Node {
    children: ParentNode[];

    constructor(layout: any, type: NodeType, parentNode?: ParentNode) {
        super(layout, type, parentNode);
        this.children = layout.children.map((childLayout: any) => LayoutNodeFactory.create(childLayout, this));
    }

    getVisibleLayout(): Node {
        return this.children[this.children.length - 1].getVisibleLayout();
    }

    getTopParent(): Node {
        if (this.parentNode) return this.parentNode.getTopParent();
        return this;
    }

    mergeOptions(options: Options) {
        this.data.options = _.merge(this.data.options, options);
        this.parentNode?.mergeOptions(options);
    }

    resolveOptions(): Options {
        return _.merge(_.cloneDeep(this.data.options), this.getVisibleLayout().data.options);
    }

    public getStack(): LayoutStack | undefined {
        if (this.type === 'Stack') {
            return this as LayoutStack;
        } else if (this.parentNode) {
            return this.parentNode.getStack();
        }

        return undefined;
    }
}