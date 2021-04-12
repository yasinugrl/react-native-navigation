import React from 'react';
import { fireEvent, render, within } from '@testing-library/react-native';

describe.only('testing that the environment is working properly', () => {
  let App;
  beforeEach(() => {
    jest.mock('./../lib/src/adapters/NativeCommandsSender', () => require('./../lib/src/adapters/__mocks__/NativeCommandsSender.tsx'));
    jest.mock('./../lib/src/adapters/NativeEventsReceiver', () => require('./../lib/src/adapters/__mocks__/NativeEventsReceiver.ts'));

    const NativeModules = require('react-native').NativeModules;
    NativeModules.KeyboardTrackingViewTempManager = {};
    NativeModules.StatusBarManager = {
      getHeight: () => 40
    }

    require('../playground/index');
    const { Application } = require('../lib/src/adapters/__mocks__/Application');
    App = render(<Application />);
  });

  function getVisibleScreen() {
    return within(App.getByTestId('VISIBLE_SCREEN'));
  }

  it('setRoot', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('STACK_BUTTON'));
  });

  it('push and pop', async () => {
    await expect(getVisibleScreen().getByTestId('Screen A')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('Screen A')).toBe(null);
    await expect(getVisibleScreen().getByTestId('Screen B')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('POP_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('Screen B')).toBe(null);
    await expect(getVisibleScreen().getByTestId('Screen A')).toBeDefined();
  });

  it('push identical components', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_BUTTON');
  });

  it('showModal', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('STACK_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_LIFECYCLE_BTN');
  });

  // it.only('setRoot -> invoke componentDidAppear', async () => {
  //   await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
  //   await getVisibleScreen().findByTestId('PUSH_BUTTON');
  // });
});