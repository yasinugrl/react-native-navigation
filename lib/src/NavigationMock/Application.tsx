import React, { Component } from "react";
import { View } from "react-native";
import LayoutComponent from "./LayoutComponent";
import store from './LayoutStore';
import { Modals } from "./Modals";
import { Overlays } from "./Overlays";

const { connect } = require('remx');

export const Application = connect()(class extends Component {
    render() {
        return (
            <View testID={'Application'}>
                <LayoutComponent layoutNode={store.getters.getLayout()} />
                <Modals />
                <Overlays />
            </View>
        )
    }
});
