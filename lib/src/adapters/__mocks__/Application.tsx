import React, { Component } from "react";
import { View } from "react-native";
import LayoutComponent from "./LayoutComponent";
import store from './LayoutStore';

const { connect } = require('remx');

export const Application = connect()(class extends Component {
    render() {
        return (
            <View>
                <LayoutComponent layoutNode={store.getters.getLayout()} />
                {/* {this.modals.map(modal => <LayoutComponent layoutNode={store.getters.getLayout()} />)} */}
            </View>
        )
    }
});
