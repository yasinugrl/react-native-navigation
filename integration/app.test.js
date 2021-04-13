import React from 'react';
import { fireEvent, render, within } from '@testing-library/react-native';
import { initNavigationMock, ApplicationGenerator } from 'react-native-navigation';

describe.only('testing that the environment is working properly', () => {
  let App;
  beforeEach(() => {
    initNavigationMock();

    const NativeModules = require('react-native').NativeModules;
    NativeModules.KeyboardTrackingViewTempManager = {};
    NativeModules.StatusBarManager = {
      getHeight: () => 40
    }

    require('../playground/index');

    const Application = ApplicationGenerator();
    App = render(<Application />);
  });

  function getVisibleScreen() {
    return within(App.getByTestId('VISIBLE_SCREEN'));
  }

  it('setRoot', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('STACK_BUTTON'));
  });

  it('push and pop', async () => {
    await expect(getVisibleScreen().getByTestId('WELCOME_SCREEN_HEADER')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('WELCOME_SCREEN_HEADER')).toBe(null);
    await expect(getVisibleScreen().getByTestId('PUSHED_SCREEN_HEADER')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('POP_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('PUSHED_SCREEN_HEADER')).toBe(null);
    await expect(getVisibleScreen().getByTestId('WELCOME_SCREEN_HEADER')).toBeDefined();
  });

  it('push identical components', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_BUTTON');
  });

  it('showModal', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('STACK_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_LIFECYCLE_BTN');
  });

  it('switch tab', async () => {
    await expect(getVisibleScreen().getByTestId('WELCOME_SCREEN_HEADER')).toBeDefined();
    await fireEvent.press(App.getByTestId('OPTIONS_TAB'));
    await expect(getVisibleScreen().queryByTestId('WELCOME_SCREEN_HEADER')).toBe(null);
    await fireEvent.press(App.getByTestId('NAVIGATION_TAB'));
    await expect(getVisibleScreen().getByTestId('NAVIGATION_SCREEN')).toBeDefined();
  });

  it('topBar button dismissModal', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('STACK_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_LIFECYCLE_BTN');
    await fireEvent.press(App.getByTestId('dismissModalButton'));
    await getVisibleScreen().findByTestId('STACK_BUTTON');
  });

  it('show overlay', async () => {
    await fireEvent.press(App.getByTestId('NAVIGATION_TAB'));
    await fireEvent.press(getVisibleScreen().getByTestId('SHOW_STATIC_EVENTS_SCREEN'));
    await fireEvent.press(getVisibleScreen().getByTestId('STATIC_EVENTS_OVERLAY_BTN'));
    await App.findByTestId('lifecycleOverlay');
  });

  it('send viewDidAppear', async () => {
    await fireEvent.press(App.getByTestId('NAVIGATION_TAB'));
    await fireEvent.press(getVisibleScreen().getByTestId('SHOW_STATIC_EVENTS_SCREEN'));
    await fireEvent.press(getVisibleScreen().getByTestId('STATIC_EVENTS_OVERLAY_BTN'));
    await App.findByTestId('lifecycleOverlay');

    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await App.findByText('componentDidAppear | Pushed | Component');
  });
});