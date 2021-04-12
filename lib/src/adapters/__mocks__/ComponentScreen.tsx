import React, { Component } from "react";
import { View } from "react-native";
import { Navigation } from "react-native-navigation";
import LayoutNode from "./LayoutNode";

interface Props {
    layoutNode: LayoutNode;
    isVisible: boolean;
}

export default class ComponentScreen extends Component<Props> {
    render() {
        const Component = Navigation.store.getComponentClassForName(this.props.layoutNode.data.name)();
        return (
            <View testID={this.props.isVisible ? 'VISIBLE_SCREEN' : undefined}>
                <Component componentId={this.props.layoutNode.nodeId} />
            </View>
        );
    }
}