import React, { Component } from "react";
import { View } from "react-native";
import { VISIBLE_OVERLAY } from ".";
import { ComponentProps } from "./ComponentProps";
import LayoutComponent from "./LayoutComponent";
import ParentNode from "./Layouts/ParentNode";
import store from './LayoutStore';
const { connect } = require('remx');

export const Overlays = connect()(class extends Component<ComponentProps> {
    render() {
        return (
            <View testID={VISIBLE_OVERLAY}>
                {this.renderOverlays()}
            </View>
        )
    }

    renderOverlays() {
        const children = store.getters.getOverlays();
        return children.map((child: ParentNode) => {
            return <LayoutComponent key={child.nodeId} layoutNode={child} />
        })
    }
});
