import React, { Component } from 'react';
import { View } from 'react-native';
import LayoutComponent from './LayoutComponent';
import store from './LayoutStore';
import { Modals } from './Modals';
import { Overlays } from './Overlays';

const { connect } = require('remx');

interface ApplicationProps {
  entryPoint: () => void;
}

export const Application = connect()(
  class extends Component<ApplicationProps> {
    constructor(props: ApplicationProps) {
      super(props);
      props.entryPoint();
    }

    render() {
      return (
        <View testID={'Application'}>
          <LayoutComponent layoutNode={store.getters.getLayout()} />
          <Modals />
          <Overlays />
        </View>
      );
    }
  }
);
