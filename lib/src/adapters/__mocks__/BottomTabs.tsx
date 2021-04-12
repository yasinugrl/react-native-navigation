import React, { Component } from "react";
import LayoutComponent from "./LayoutComponent";
import LayoutNode from "./LayoutNode";

interface Props {
    layoutNode: LayoutNode;
    isVisible: boolean;
}

export default class BottomTabs extends Component<Props> {
    resolveLayout() {
        return this.props.layoutNode.children.map(child => <LayoutComponent layoutNode={child} isVisible={this.props.isVisible} />)[0]
    }

    render() {
        return this.resolveLayout();
    }
}