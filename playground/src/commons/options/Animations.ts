import {
  AnimationOptions,
  ViewAnimationOptions,
  OptionsAnimationPropertyConfig,
} from 'react-native-navigation';
import { Dimensions } from 'react-native';
import flags from '../../flags';

const { useCustomAnimations, useSlowOpenScreenAnimations, useSlideAnimation } = flags;
const height = Math.round(Dimensions.get('window').height);
const width = Math.round(Dimensions.get('window').width);
const SCREEN_ANIMATION_DURATION = 300 * (useSlowOpenScreenAnimations ? 2.5 : 1);

const baseSlideAnimation: OptionsAnimationPropertyConfig = {
  duration: SCREEN_ANIMATION_DURATION,
  interpolation: {
    type: 'spring',
    damping: 500,
    stiffness: 200,
    mass: 5,
  },
};

const slideInFromRight: ViewAnimationOptions = {
  translationX: {
    from: width,
    to: 0,
    ...baseSlideAnimation,
  },
};

const slideInFromLeft: ViewAnimationOptions = {
  translationX: {
    from: -50,
    to: 0,
    ...baseSlideAnimation,
  },
};

const slideOutToLeft: ViewAnimationOptions = {
  translationX: {
    from: 0,
    to: -50,
    ...baseSlideAnimation,
  },
};

const slideOutToRight: ViewAnimationOptions = {
  translationX: {
    from: 0,
    to: width,
    ...baseSlideAnimation,
  },
};

const slideAnimations: AnimationOptions = {
  push: {
    waitForRender: true,
    content: {
      enter: slideInFromRight,
      exit: slideOutToLeft,
    },
  },
  pop: {
    content: {
      enter: slideInFromLeft,
      exit: slideOutToRight,
    },
  },
  setStackRoot: {
    waitForRender: true,
    content: {
      enter: slideInFromRight,
      exit: slideOutToLeft,
    },
  },
};

const customAnimations: AnimationOptions = {
  showModal: {
    waitForRender: true,
    translationY: {
      from: height,
      to: 0,
      duration: SCREEN_ANIMATION_DURATION,
      interpolation: { type: 'decelerate' },
    },
    alpha: {
      from: 0.65,
      to: 1,
      duration: SCREEN_ANIMATION_DURATION * 0.7,
      interpolation: { type: 'decelerate' },
    },
  },
  dismissModal: {
    translationY: {
      from: 0,
      to: height,
      duration: SCREEN_ANIMATION_DURATION * 0.9,
    },
  },
  push: {
    waitForRender: true,
    content: {
      alpha: {
        from: 0.65,
        to: 1,
        duration: SCREEN_ANIMATION_DURATION * 0.7,
        interpolation: { type: 'decelerate' },
      },
      translationY: {
        from: height * 0.3,
        to: 0,
        duration: SCREEN_ANIMATION_DURATION,
        interpolation: { type: 'decelerate' },
      },
    },
  },
  pop: {
    content: {
      alpha: {
        from: 1,
        to: 0,
        duration: SCREEN_ANIMATION_DURATION,
      },
      translationY: {
        from: 0,
        to: height * 0.7,
        duration: SCREEN_ANIMATION_DURATION * 0.9,
      },
    },
  },
};

export default {
  ...(useSlideAnimation ? slideAnimations : useCustomAnimations ? customAnimations : {}),
};
