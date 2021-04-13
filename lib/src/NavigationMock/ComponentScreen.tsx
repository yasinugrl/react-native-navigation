import React, { Component } from "react";
import { View } from "react-native";
import { Navigation } from "react-native-navigation";
import { ComponentProps } from "./ComponentProps";
import store from './LayoutStore';
const { connect } = require('remx');

export const ComponentScreen = connect()(class extends Component<ComponentProps> {
    render() {
        //@ts-ignore
        const Component = Navigation.store.getComponentClassForName(this.props.layoutNode.data.name)!();
        return (
            <View testID={store.getters.isVisibleLayout(this.props.layoutNode) ? 'VISIBLE_SCREEN' : undefined}>
                <View testID={this.props.layoutNode.resolveOptions().topBar?.testID}>
                    <Component componentId={this.props.layoutNode.nodeId} />
                </View>
            </View>
        );
    }
});
