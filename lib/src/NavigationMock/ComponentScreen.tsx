import React, { Component } from 'react';
import { Button, View, Text } from 'react-native';
import { Navigation, OptionsTopBarButton } from 'react-native-navigation';
import { ComponentProps } from './ComponentProps';
import store from './LayoutStore';
import { events } from './EventsStore';
import { Overlays } from './Overlays';
import LayoutStore from './LayoutStore';
import { VisibleScreenID } from '.';

const { connect } = require('remx');

export const ComponentScreen = connect()(
  class extends Component<ComponentProps> {
    constructor(props: ComponentProps) {
      super(props);
    }

    componentDidMount() {
      events.componentDidAppear({
        componentName: this.props.layoutNode.data.name,
        componentId: this.props.layoutNode.nodeId,
        componentType: 'Component',
      });
    }

    isVisible(): boolean {
      return store.getters.isVisibleLayout(this.props.layoutNode);
    }

    renderTabBar() {
      if (!this.props.bottomTabs) return null;

      const bottomTabsOptions = this.props.bottomTabs.resolveOptions().bottomTabs;
      if (bottomTabsOptions?.visible === false) return null;
      const buttons = this.props.bottomTabs!.children!.map((child, i) => {
        const bottomTabOptions = child.resolveOptions().bottomTab;
        return (
          <Button
            key={`tab-${i}`}
            testID={bottomTabOptions?.testID}
            title={bottomTabOptions?.text || ''}
            onPress={() => store.setters.selectTabIndex(this.props.bottomTabs, i)}
          />
        );
      });

      return <View testID={bottomTabsOptions?.testID}>{buttons}</View>;
    }

    renderTopBar() {
      if (!this.props.stack) return null;

      const topBarOptions = this.props.layoutNode.resolveOptions().topBar;
      if (topBarOptions?.visible === false) return null;
      else {
        const component = topBarOptions?.title?.component;
        return (
          <View testID={topBarOptions?.testID}>
            <Text>{topBarOptions?.title?.text}</Text>
            <Text>{topBarOptions?.subtitle?.text}</Text>
            {this.renderButtons(topBarOptions?.leftButtons)}
            {this.renderButtons(topBarOptions?.rightButtons)}
            {component && this.renderComponent(component.componentId!, component.name)}
          </View>
        );
      }
    }

    renderButtons(buttons: OptionsTopBarButton[] = []) {
      return buttons.map((button) => {
        if (button.component) {
          return this.renderComponent(button.component.componentId, button.component.name, button.testID)
        }

        return (
          <Button
            testID={button.testID}
            key={button.id}
            title={button.text || ''}
            onPress={() =>
              events.navigationButtonPressed({
                buttonId: button.id,
                componentId: this.props.layoutNode.getVisibleLayout().nodeId,
              })
            }
          />
        );
      });
    }

    renderBackButton() {
      const backButtonOptions = this.props.layoutNode.resolveOptions().topBar?.backButton;
      return (
        <Button
          testID={backButtonOptions?.testID}
          title={backButtonOptions && backButtonOptions.title ? backButtonOptions.title : ''}
          onPress={() => {
            LayoutStore.setters.pop(this.props.layoutNode.nodeId)
          }}
        />
      );
    }

    renderComponent(id: string, name: string, testID?: string) {
      const Component = Navigation.store.getComponentClassForName(name)!();
      const props = Navigation.store.getPropsForId(id);
      return (
        <View testID={testID}>
          <Component key={id} {...props} componentId={id} />
        </View>
      );
    }

    render() {
      //@ts-ignore
      const Component = Navigation.store.getWrappedComponent(this.props.layoutNode.data.name);
      return (
        <View testID={this.isVisible() ? VisibleScreenID : undefined}>
          {this.props.backButton && this.renderBackButton()}
          {this.renderTopBar()}
          {this.renderTabBar()}
          {this.isVisible() && (
            <View>
              <Overlays />
            </View>
          )}
          <Component componentId={this.props.layoutNode.nodeId} />
        </View>
      );
    }
  }
);
