import React, { Component } from "react";
import { View } from "react-native";
import { Navigation } from "react-native-navigation";
import { ComponentProps } from "./ComponentProps";
import store from './LayoutStore';
import { events } from './EventsStore';
const { connect } = require('remx');

export const ComponentScreen = connect()(class extends Component<ComponentProps> {
    constructor(props: ComponentProps) {
        super(props);
    }

    componentDidMount() {
        events.componentDidAppear({ componentName: this.props.layoutNode.data.name, componentId: this.props.layoutNode.nodeId, componentType: 'Component' });
    }

    // componentDidUnmount() {
    //     events.componentDidDisappear({ componentName: this.props.layoutNode.data.name, componentId: this.props.layoutNode.nodeId, componentType: 'Component' });
    // }

    render() {
        //@ts-ignore
        const Component = Navigation.store.getWrappedComponent(this.props.layoutNode.data.name);
        return (
            <View testID={store.getters.isVisibleLayout(this.props.layoutNode) ? 'VISIBLE_SCREEN' : undefined}>
                <View testID={this.props.layoutNode.resolveOptions().topBar?.testID}>
                    <Component componentId={this.props.layoutNode.nodeId} />
                </View>
            </View>
        );
    }
});
