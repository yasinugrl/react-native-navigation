import React, { Component } from "react";
import LayoutComponent from "./LayoutComponent";
import LayoutNode from "./LayoutNode";
const { connect } = require('remx');

interface Props {
    layoutNode: LayoutNode;
}

class Stack extends Component<Props> {
    render() {
        console.log('render');
        const children = this.props.layoutNode.children;
        console.log(children);
        return children.map((child, i) => {
            console.log((i === children.length - 1));
            return <LayoutComponent layoutNode={child} isVisible={(i === children.length - 1)} />
        })
    }
}

export default connect()(Stack);