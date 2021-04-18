import React, { Component } from "react";
import { Button, View, Text } from "react-native";
import { OptionsTopBarButton } from "react-native-navigation/interfaces/Options";
import { ComponentProps } from "./ComponentProps";
import LayoutComponent from "./LayoutComponent";
import ParentNode from "./Layouts/ParentNode";
import store from './LayoutStore';
import { events } from './EventsStore';
const { connect } = require('remx');

export const Stack = connect()(class extends Component<ComponentProps> {
    renderTopBar() {
        const topBarOptions = this.props.layoutNode.getVisibleLayout().resolveOptions().topBar;
        if (topBarOptions?.visible === false) return <View />
        else return (
            <View testID={store.getters.isVisibleLayout(this.props.layoutNode.getVisibleLayout()) ? 'TopBar_Mock' : undefined}>
                <Text>{topBarOptions?.title?.text}</Text>
                <Text>{topBarOptions?.subtitle?.text}</Text>
                {this.renderButtons(topBarOptions?.leftButtons)}
                {this.renderButtons(topBarOptions?.rightButtons)}
            </View>
        )
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
        const children = this.props.layoutNode.children;
        // console.log(children.length);
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
