import React, { Component } from "react";
import { View } from "react-native";
import LayoutComponent from "./LayoutComponent";
import store from './LayoutStore';
import { Modals } from "./Modals";

const { connect } = require('remx');

export const Application = connect()(class extends Component {
    render() {
        return (
            <View>
                <LayoutComponent layoutNode={store.getters.getLayout()} isVisible={!store.getters.getModals().length} />
                <Modals />
            </View>
        )
    }
});
