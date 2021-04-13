import React, { Component } from "react";
import { Button, View } from "react-native";
import LayoutComponent from "./LayoutComponent";
import { ComponentProps } from "./ComponentProps";
import store from './LayoutStore';
const { connect } = require('remx');

export const BottomTabs = connect()(class extends Component<ComponentProps> {
    renderTabBar() {
        return (
            this.props.layoutNode!.children!.map((child, i) => {
                const bottomTabOptions = child.resolveOptions().bottomTab;
                return <Button
                    testID={bottomTabOptions?.testID}
                    title={bottomTabOptions?.text || ''}
                    onPress={() => store.setters.selectTabIndex(this.props.layoutNode, i)} />
            })
        );
    }

    renderScreens() {
        return (
            this.props.layoutNode.children.map((child) => {
                return <LayoutComponent layoutNode={child} />
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