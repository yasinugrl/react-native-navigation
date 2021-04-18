import React, { Component } from "react";
import { View } from "react-native";
import { ComponentProps } from "./ComponentProps";
import LayoutComponent from "./LayoutComponent";
import ParentNode from "./Layouts/ParentNode";
import store from './LayoutStore';
const { connect } = require('remx');

export const Modals = connect()(class extends Component<ComponentProps> {
    render() {
        const children = store.getters.getModals();
        return (
            <View testID={'MODALS'}>
                {
                    children.map((child: ParentNode) => {
                        return <LayoutComponent key={child.nodeId} layoutNode={child} />
                    })
                }
            </View>
        );
    }
});
