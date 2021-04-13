import React from 'react';
import { fireEvent, render, within } from '@testing-library/react-native';
import { initNavigationMock, ApplicationGenerator } from 'react-native-navigation';
import testIDs from '../playground/src/testIDs';

describe.only('testing that the environment is working properly', () => {
  let App;
  beforeEach(() => {
    initNavigationMock();

    const NativeModules = require('react-native').NativeModules;
    NativeModules.KeyboardTrackingViewTempManager = {};
    NativeModules.StatusBarManager = {
      getHeight: () => 40,
    };

    require('../playground/index');

    const Application = ApplicationGenerator();
    App = render(<Application />);
  });

  function getVisibleScreen() {
    return within(App.getByTestId('VISIBLE_SCREEN'));
  }

  function findElementById(id) {
    let element;
    if (getVisibleScreen().queryByTestId(id) !== null) element = getVisibleScreen().getByTestId(id);
    else element = App.getByTestId(id);
    return element;
  }

  function elementById(id) {
    const element = findElementById(id);
    return {
      tap: () => {
        fireEvent.press(element);
      },
    };
  }

  function assertElementById(id) {
    expect(findElementById(id)).toBeDefined();
  }

  it('setRoot', async () => {
    await elementById('STACK_BUTTON').tap();
  });

  it('push and pop', async () => {
    await assertElementById(testIDs.WELCOME_SCREEN_HEADER);
    await elementById(testIDs.PUSH_BTN).tap();
    await expect(getVisibleScreen().queryByTestId(testIDs.WELCOME_SCREEN_HEADER)).toBe(null);
    await expect(getVisibleScreen().queryByTestId(testIDs.PUSHED_SCREEN_HEADER)).toBeDefined();
    await elementById(testIDs.POP_BTN).tap();
    await expect(getVisibleScreen().queryByTestId(testIDs.PUSHED_SCREEN_HEADER)).toBe(null);
    await expect(getVisibleScreen().getByTestId(testIDs.WELCOME_SCREEN_HEADER)).toBeDefined();
  });

  it('push identical components', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId(testIDs.PUSH_BTN));
    await getVisibleScreen().findByTestId(testIDs.PUSH_BTN);
  });

  it('showModal', async () => {
    await fireEvent.press(getVisibleScreen().getByTestId(testIDs.STACK_BTN));
    await getVisibleScreen().findByTestId(testIDs.PUSH_LIFECYCLE_BTN);
  });

  it('switch tab', async () => {
    await expect(getVisibleScreen().getByTestId(testIDs.WELCOME_SCREEN_HEADER)).toBeDefined();
    await elementById(testIDs.OPTIONS_TAB).tap();
    await expect(getVisibleScreen().queryByTestId(testIDs.WELCOME_SCREEN_HEADER)).toBe(null);
    await elementById(testIDs.NAVIGATION_TAB).tap();
    await expect(getVisibleScreen().getByTestId(testIDs.NAVIGATION_SCREEN)).toBeDefined();
  });

  it('topBar button dismissModal', async () => {
    await elementById(testIDs.STACK_BTN).tap();
    await getVisibleScreen().findByTestId(testIDs.PUSH_LIFECYCLE_BTN);
    await elementById('dismissModalButton').tap();
    await getVisibleScreen().findByTestId(testIDs.STACK_BTN);
  });

  it('show overlay', async () => {
    await elementById(testIDs.NAVIGATION_TAB).tap();
    await elementById(testIDs.SHOW_STATIC_EVENTS_SCREEN).tap();
    await elementById(testIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await App.findByTestId('lifecycleOverlay');
  });

  it('send viewDidAppear', async () => {
    await elementById(testIDs.NAVIGATION_TAB).tap();
    await elementById(testIDs.SHOW_STATIC_EVENTS_SCREEN).tap();
    await elementById(testIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await App.findByTestId('lifecycleOverlay');

    await elementById(testIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await elementById(testIDs.PUSH_BTN).tap();
    await App.findByText('componentDidAppear | Pushed | Component');
  });
});
