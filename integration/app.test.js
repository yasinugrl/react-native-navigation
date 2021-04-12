import React from 'react';
import { fireEvent, render, within } from '@testing-library/react-native';

describe('testing that the environment is working properly', () => {
  let App;
  beforeEach(() => {
    jest.mock('./../lib/src/adapters/NativeCommandsSender', () => require('./../lib/src/adapters/__mocks__/NativeCommandsSender.tsx'));

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
    await fireEvent.press(global.component.getByTestId('STACK_BUTTON'));
  });

  it.only('push and pop', async () => {
    await expect(getVisibleScreen().getByTestId('Screen A')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('Screen A')).toBe(null);
    await expect(getVisibleScreen().getByTestId('Screen B')).toBeDefined();
    await fireEvent.press(getVisibleScreen().getByTestId('POP_BUTTON'));
    await expect(getVisibleScreen().queryByTestId('Screen B')).toBe(null);
    await expect(getVisibleScreen().getByTestId('Screen A')).toBeDefined();
  });

  it.only('push identical components', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_BUTTON');
  });

  it.only('setRoot -> invoke componentDidAppear', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId('PUSH_BUTTON'));
    await getVisibleScreen().findByTestId('PUSH_BUTTON');
  });
});