import React, { Component } from "react";
import { ComponentProps } from "./ComponentProps";
import LayoutComponent from "./LayoutComponent";
import ParentNode from "./Layouts/ParentNode";
import store from './LayoutStore';
const { connect } = require('remx');

export const Overlays = connect()(class extends Component<ComponentProps> {
    render() {
        const children = store.getters.getOverlays();
        return children.map((child: ParentNode) => {
            return <LayoutComponent layoutNode={child} />
        })
    }
});
