import React from 'react';
import { fireEvent, render, within } from '@testing-library/react-native';
import { MockNavigation } from 'react-native-navigation';
import TestIDs from '../playground/src/TestIDs';

describe.only('testing that the environment is working properly', () => {
  let App;
  beforeEach(() => {
    const Application = MockNavigation();

    const NativeModules = require('react-native').NativeModules;
    NativeModules.KeyboardTrackingViewTempManager = {};
    NativeModules.StatusBarManager = {
      getHeight: () => 40,
    };

    require('../playground/index');

    const origExpect = expect;
    expect = (e) => {
      const match = origExpect(e);
      match.toBeNotVisible = () => match.toBe(null);
      match.toBeVisible = () => match.toBeDefined();
      return match;
    }

    setTimeout = (func) => {
      func();
    }

    jest.spyOn(global, 'alert');

    App = render(<Application />);
  });

  function findElementById(id) {
    let element = null;
    if (App.queryByTestId('VISIBLE_SCREEN') && within(App.getByTestId('VISIBLE_SCREEN')).queryByTestId(id)) {
      element = within(App.getByTestId('VISIBLE_SCREEN')).getByTestId(id);
    } else if (App.queryByTestId('BottomTabs_Mock') && within(App.getByTestId('BottomTabs_Mock')).queryByTestId(id)) {
      element = within(App.getByTestId('BottomTabs_Mock')).getByTestId(id);
    } else if (App.queryByTestId('TopBar_Mock') && within(App.getByTestId('TopBar_Mock')).queryByTestId(id)) {
      element = within(App.getByTestId('TopBar_Mock')).getByTestId(id);
    }

    return element;
  }

  function findElementByLabel(label) {
    let element = null;
    if (App.queryByTestId('VISIBLE_SCREEN') && within(App.getByTestId('VISIBLE_SCREEN')).queryByText(label)) {
      element = within(App.getByTestId('VISIBLE_SCREEN')).getByText(label);
    } else if (App.queryByTestId('BottomTabs_Mock') && within(App.getByTestId('BottomTabs_Mock')).queryByText(label)) {
      element = within(App.getByTestId('BottomTabs_Mock')).getByText(label);
    } else if (App.queryByTestId('TopBar_Mock') && within(App.getByTestId('TopBar_Mock')).queryByText(label)) {
      element = within(App.getByTestId('TopBar_Mock')).getByText(label);
    }

    return element;
  }

  function elementById(id) {
    const element = findElementById(id);
    if (element) {
      element.tap = () => {
        fireEvent.press(element);
      }
    }

    return element;
  }

  function elementByLabel(label) {
    const element = findElementByLabel(label);
    if (element) {
      element.tap = () => {
        fireEvent.press(element);
      }
    }

    return element;
  }

  describe('Stack', () => {
    beforeEach(async () => {
      await elementById(TestIDs.STACK_BTN).tap();
    });

    it('push and pop screen', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementById(TestIDs.PUSHED_SCREEN_HEADER)).toBeVisible();
      await elementById(TestIDs.POP_BTN).tap();
      await expect(elementById(TestIDs.STACK_SCREEN_HEADER)).toBeVisible();
    });

    it('push and pop screen without animation', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_NO_ANIM_BTN).tap();
      await expect(elementByLabel('Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.POP_BTN).tap();
      await expect(elementByLabel('Stack Position: 1')).toBeVisible();
    });

    it('pop using stack id', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementById(TestIDs.PUSHED_SCREEN_HEADER)).toBeVisible();
      await elementById(TestIDs.POP_USING_STACK_ID_BTN).tap();
      await expect(elementById(TestIDs.STACK_SCREEN_HEADER)).toBeVisible();
    });

    it('pop using previous screen id', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementByLabel('Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.POP_USING_PREVIOUS_SCREEN_ID_BTN).tap();
      await expect(elementByLabel('Stack Position: 1')).toBeVisible();
    });

    it('pop to specific id', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementByLabel('Stack Position: 3')).toBeVisible();
      await elementById(TestIDs.POP_TO_FIRST_SCREEN_BTN).tap();
      await expect(elementByLabel('Stack Position: 1')).toBeVisible();
    });

    it('pop to root', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await elementById(TestIDs.POP_TO_ROOT_BTN).tap();
      await expect(elementById(TestIDs.STACK_SCREEN_HEADER)).toBeVisible();
    });

    it('pop component should not detach component if can`t pop', async () => {
      await elementById(TestIDs.POP_NONE_EXISTENT_SCREEN_BTN).tap();
      await expect(elementById(TestIDs.STACK_SCREEN_HEADER)).toBeVisible();
    });

    it('push title with subtitle', async () => {
      await elementById(TestIDs.PUSH_TITLE_WITH_SUBTITLE).tap();
      await expect(elementByLabel('Title')).toBeVisible();
      await expect(elementByLabel('Subtitle')).toBeVisible();
    });

    it('does not crash when setting the stack root to an existing component id', async () => {
      await elementById(TestIDs.SET_STACK_ROOT_WITH_ID_BTN).tap();
      await elementById(TestIDs.SET_STACK_ROOT_WITH_ID_BTN).tap();
    });

    it(':ios: set stack root component should be first in stack', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementByLabel('Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.SET_STACK_ROOT_BUTTON).tap();
      await expect(elementByLabel('Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.POP_BTN).tap();
      await expect(elementByLabel('Stack Position: 2')).toBeVisible();
    });

    it('push promise is resolved with pushed ViewController id', async () => {
      await elementById(TestIDs.STACK_COMMANDS_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementByLabel('push promise resolved with: ChildId')).toBeVisible();
      await expect(elementByLabel('pop promise resolved with: ChildId')).toBeVisible();
    });

    it('screen lifecycle', async () => {
      await elementById(TestIDs.PUSH_LIFECYCLE_BTN).tap();
      await expect(elementByLabel('didAppear')).toBeVisible();

      await elementById(TestIDs.PUSH_TO_TEST_DID_DISAPPEAR_BTN).tap();
      expect(global.alert).toHaveBeenCalledWith('didDisappear')
    });
  });

  describe('modal', () => {
    beforeEach(async () => {
      await elementById(TestIDs.NAVIGATION_TAB).tap();
      await elementById(TestIDs.MODAL_BTN).tap();
    });

    it('show modal', async () => {
      await expect(elementById(TestIDs.MODAL_SCREEN_HEADER)).toBeVisible();
    });

    it('dismiss modal', async () => {
      await expect(elementById(TestIDs.MODAL_SCREEN_HEADER)).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('unmount modal when dismissed', async () => {
      await expect(elementById(TestIDs.MODAL_SCREEN_HEADER)).toBeVisible();
      await elementById(TestIDs.MODAL_LIFECYCLE_BTN).tap();
      await expect(elementByLabel('didAppear')).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      expect(global.alert).toHaveBeenCalledWith('componentWillUnmount');
      expect(global.alert).toHaveBeenCalledWith('didDisappear');
    });

    it('show multiple modals', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('dismiss unknown screen id', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.DISMISS_UNKNOWN_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('dismiss modal by id which is not the top most', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.DISMISS_PREVIOUS_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('dismiss all previous modals by id when they are below top presented modal', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

      await elementById(TestIDs.DISMISS_ALL_PREVIOUS_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('dismiss some modal by id deep in the stack', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

      await elementById(TestIDs.DISMISS_FIRST_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();

      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('dismissAllModals', async () => {
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
      await elementById(TestIDs.DISMISS_ALL_MODALS_BTN).tap();
      await expect(elementById(TestIDs.NAVIGATION_TAB)).toBeVisible();
    });

    it('push into modal', async () => {
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementByLabel('Stack Position: 1')).toBeVisible();
    });

    it('present modal multiple times', async () => {
      await elementById(TestIDs.DISMISS_MODAL_BTN).tap();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    });

    it('setRoot dismisses modals', async () => {
      await elementById(TestIDs.SET_ROOT).tap();
      await expect(elementById(TestIDs.MODAL_SCREEN_HEADER)).toBeNotVisible();
      await expect(elementById(TestIDs.PUSHED_SCREEN_HEADER)).toBeVisible();
    });

    it('dismissModal promise is resolved with root ViewController id', async () => {
      await elementById(TestIDs.MODAL_COMMANDS_BTN).tap();
      await elementById(TestIDs.MODAL_BTN).tap();
      await expect(elementByLabel('showModal promise resolved with: UniqueStackId')).toBeVisible();
      await expect(elementByLabel('modalDismissed listener called with with: UniqueStackId')).toBeVisible();
      await expect(elementByLabel('dismissModal promise resolved with: UniqueStackId')).toBeVisible();
    });
  });

  describe('BottomTabs', () => {
    beforeEach(async () => {
      await elementById(TestIDs.BOTTOM_TABS_BTN).tap();
      await expect(elementByLabel('First Tab')).toBeVisible();
    });

    it('switch to tab by index', async () => {
      await elementById(TestIDs.SWITCH_TAB_BY_INDEX_BTN).tap();
      await expect(elementByLabel('First Tab')).toBeNotVisible();
      await expect(elementByLabel('Second Tab')).toBeVisible();
    });

    it('switch to tab by componentId', async () => {
      await elementById(TestIDs.SWITCH_TAB_BY_COMPONENT_ID_BTN).tap();
      await expect(elementByLabel('First Tab')).toBeNotVisible();
      await expect(elementByLabel('Second Tab')).toBeVisible();
    });

    it('push bottom tabs', async () => {
      await elementById(TestIDs.SWITCH_TAB_BY_INDEX_BTN).tap();
      await elementById(TestIDs.PUSH_BTN).tap();
      await expect(elementById(TestIDs.PUSHED_BOTTOM_TABS)).toBeVisible();
    });

    it('set Tab Bar badge on current Tab', async () => {
      await elementById(TestIDs.SET_BADGE_BTN).tap();
      await expect(elementByLabel('NEW')).toBeVisible();
    });

    it('set empty string badge on a current Tab should clear badge', async () => {
      await elementById(TestIDs.SET_BADGE_BTN).tap();
      await expect(elementByLabel('NEW')).toBeVisible();
      await elementById(TestIDs.CLEAR_BADGE_BTN).tap();
      await expect(elementByLabel('NEW')).toBeNotVisible();
    });

    xit('merge options correctly in SideMenu inside BottomTabs layout', async () => {
      await elementById(TestIDs.SWITCH_TAB_BY_INDEX_BTN).tap();
      await elementById(TestIDs.SIDE_MENU_INSIDE_BOTTOM_TABS_BTN).tap();
      await elementById(TestIDs.OPEN_LEFT_SIDE_MENU_BTN).tap();

      await elementById(TestIDs.CLOSE_LEFT_SIDE_MENU_BTN).tap();
      await expect(elementById(TestIDs.CLOSE_LEFT_SIDE_MENU_BTN)).toBeNotVisible();
    });

    it(':android: hide Tab Bar', async () => {
      await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
      await elementById(TestIDs.HIDE_TABS_BTN).tap();
      await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
    });

    it(':android: show Tab Bar', async () => {
      await elementById(TestIDs.HIDE_TABS_BTN).tap();
      await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
      await elementById(TestIDs.SHOW_TABS_BTN).tap();
      await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    });

    it('hide Tab Bar on push', async () => {
      // await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
      await elementById(TestIDs.HIDE_TABS_PUSH_BTN).tap();
      await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
      console.log(App.debug());
      // await elementById(TestIDs.POP_BTN).tap();
      // await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    });

    // it('hide Tab Bar on push from second bottomTabs screen', async () => {
    //   await elementById(TestIDs.SWITCH_TAB_BY_INDEX_BTN).tap();
    //   await elementById(TestIDs.HIDE_TABS_PUSH_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
    //   await elementById(TestIDs.POP_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    // });

    // it('hide Tab Bar on push from second bottomTabs screen - deep stack', async () => {
    //   await elementById(TestIDs.SWITCH_TAB_BY_INDEX_BTN).tap();
    //   await elementById(TestIDs.HIDE_TABS_PUSH_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
    //   await elementById(TestIDs.PUSH_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    //   await elementById(TestIDs.POP_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
    //   await elementById(TestIDs.POP_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    // });

    // it('hide Tab Bar on second tab after pressing the tab', async () => {
    //   await elementById(TestIDs.SECOND_TAB_BAR_BTN).tap();
    //   await elementById(TestIDs.HIDE_TABS_PUSH_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeNotVisible();
    //   await elementById(TestIDs.POP_BTN).tap();
    //   await expect(elementById(TestIDs.BOTTOM_TABS)).toBeVisible();
    // });
  });





  /// Remove below
  it('hides TopBar when pressing on Hide TopBar and shows it when pressing on Show TopBar', async () => {
    await elementById(TestIDs.OPTIONS_TAB).tap();
    await elementById(TestIDs.HIDE_TOP_BAR_BTN).tap();
    await expect(elementById('TopBar_Mock')).toBeNotVisible();
    await elementById(TestIDs.SHOW_TOP_BAR_BTN).tap();
    await expect(elementById('TopBar_Mock')).toBeVisible();
  });
  it('show overlay', async () => {
    await elementById(TestIDs.NAVIGATION_TAB).tap();
    await elementById(TestIDs.SHOW_STATIC_EVENTS_SCREEN).tap();
    await elementById(TestIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await App.findByTestId('lifecycleOverlay');
  });

  it('send viewDidAppear', async () => {
    await elementById(TestIDs.NAVIGATION_TAB).tap();
    await elementById(TestIDs.SHOW_STATIC_EVENTS_SCREEN).tap();
    await elementById(TestIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await App.findByTestId('lifecycleOverlay');

    await elementById(TestIDs.STATIC_EVENTS_OVERLAY_BTN).tap();
    await elementById(TestIDs.PUSH_BTN).tap();
    await App.findByText('componentDidAppear | Pushed | Component');
  });
});
