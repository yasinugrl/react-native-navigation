import React, { Component } from "react";
import { View } from "react-native";
import BottomTabs from "./BottomTabs";
import ComponentScreen from "./ComponentScreen";
import LayoutNode from "./LayoutNode";
import Stack from "./Stack";

interface Props {
    layoutNode: LayoutNode;
    isVisible: boolean;
}

export default class LayoutComponent extends Component<Props> {
    render() {
        switch (this.props.layoutNode.type) {
            case 'BottomTabs':
                return <BottomTabs layoutNode={this.props.layoutNode} isVisible={this.props.isVisible} />
            case 'Stack':
                return <Stack layoutNode={this.props.layoutNode} isVisible={this.props.isVisible} />
            case 'Component':
                return <ComponentScreen layoutNode={this.props.layoutNode} isVisible={this.props.isVisible} />
        }
        return <View />
    }
}