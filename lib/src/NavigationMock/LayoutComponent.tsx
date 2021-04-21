import React, { Component } from 'react';
import { View } from 'react-native';
import { BottomTabs } from './BottomTabs';
import { ComponentProps } from './ComponentProps';
import { ComponentScreen } from './ComponentScreen';
import { Stack } from './Stack';

export default class LayoutComponent extends Component<ComponentProps> {
  render() {
    switch (this.props.layoutNode.type) {
      case 'BottomTabs':
        return <BottomTabs layoutNode={this.props.layoutNode} stack={this.props.stack} />;
      case 'Stack':
        return (
          <Stack
            layoutNode={this.props.layoutNode}
            bottomTabs={this.props.bottomTabs}
            stack={this.props.stack}
          />
        );
      case 'Component':
        return (
          <ComponentScreen
            layoutNode={this.props.layoutNode}
            bottomTabs={this.props.bottomTabs}
            stack={this.props.stack}
          />
        );
    }

    return <View />;
  }
}
