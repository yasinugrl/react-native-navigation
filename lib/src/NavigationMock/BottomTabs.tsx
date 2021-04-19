import React, { Component } from "react";
import { Button, View } from "react-native";
import LayoutComponent from "./LayoutComponent";
import { ComponentProps } from "./ComponentProps";
import store from './LayoutStore';
const { connect } = require('remx');

export const BottomTabs = connect()(class extends Component<ComponentProps> {

    renderTabBar() {
        const buttons = this.props.layoutNode!.children!.map((child, i) => {
            const bottomTabOptions = child.resolveOptions().bottomTab;
            return <Button
                key={`tab-${i}`}
                testID={bottomTabOptions?.testID}
                title={bottomTabOptions?.text || ''}
                onPress={() => store.setters.selectTabIndex(this.props.layoutNode, i)} />
        });

        return (
            <View testID={store.getters.isVisibleLayout(this.props.layoutNode.getVisibleLayout()) ? 'BottomTabs_Mock' : undefined}>
                {buttons}
            </View >
        );
    }

    renderScreens() {
        return (
            this.props.layoutNode.children.map((child) => {
                return <LayoutComponent key={child.nodeId} layoutNode={child} />
            })
        );
    }

    render() {
        return (
            <View>
                {this.renderScreens()}
                {this.renderTabBar()}
            </View>
        );
    }
});