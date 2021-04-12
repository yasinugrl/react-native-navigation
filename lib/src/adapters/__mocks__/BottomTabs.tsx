import React, { Component } from "react";
import LayoutComponent from "./LayoutComponent";
import LayoutNode from "./LayoutNode";

interface Props {
    layoutNode: LayoutNode;
    isVisible: boolean;
}

export default class BottomTabs extends Component<Props> {
    private readonly selectedIndex: number = 0;

    render() {
        return this.props.layoutNode.children.map((child, i) => {
            return <LayoutComponent layoutNode={child} isVisible={this.props.isVisible && i === this.selectedIndex} />
        })
    }
}