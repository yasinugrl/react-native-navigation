import React, { Component } from "react";
import { Button, View } from "react-native";
import { OptionsTopBarButton } from "react-native-navigation/interfaces/Options";
import { ComponentProps } from "./ComponentProps";
import LayoutComponent from "./LayoutComponent";
import ParentNode from "./Layouts/ParentNode";
import store from './LayoutStore';
import { events } from './EventsStore';
const { connect } = require('remx');

export const Stack = connect()(class extends Component<ComponentProps> {
    renderTopBar() {
        return (
            <View>
                {this.renderButtons(this.props.layoutNode.getVisibleLayout().resolveOptions().topBar?.leftButtons)}
                {this.renderButtons(this.props.layoutNode.getVisibleLayout().resolveOptions().topBar?.rightButtons)}
            </View>
        );
    }

    renderButtons(buttons: OptionsTopBarButton[] = []) {
        return (
            buttons.map(button => {
                return <Button
                    testID={button.id}
                    key={button.id}
                    title={button.text || ''}
                    onPress={() => events.navigationButtonPressed(
                        {
                            buttonId: button.id,
                            componentId: this.props.layoutNode.getVisibleLayout().nodeId
                        })} />
            })
        );
    }

    renderScreens() {
        const children = store.getters.getLayoutChildren(this.props.layoutNode.nodeId);
        return children.map((child: ParentNode) => {
            return <LayoutComponent key={child.nodeId} layoutNode={child} />
        })
    }

    render() {
        return (
            <View>
                {this.renderTopBar()}
                {this.renderScreens()}
            </View>
        );
    }
});
