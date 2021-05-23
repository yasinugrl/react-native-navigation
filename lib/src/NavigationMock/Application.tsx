import React, { Component } from 'react';
import { View } from 'react-native';
import { VISIBLE_SCREEN_TEST_ID } from './ComponentScreen';
import { LayoutComponent } from './LayoutComponent';
import { LayoutStore } from './LayoutStore';
import { Modals } from './Modals';
import { Overlays, VISIBLE_OVERLAY_TEST_ID } from './Overlays';

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
          <LayoutComponent layoutNode={LayoutStore.getLayout()} />
          <Modals />
          <Overlays />
        </View>
      );
    }
  }
);

Application.VISIBLE_SCREEN_TEST_ID = VISIBLE_SCREEN_TEST_ID;
Application.VISIBLE_OVERLAY_TEST_ID = VISIBLE_OVERLAY_TEST_ID;
