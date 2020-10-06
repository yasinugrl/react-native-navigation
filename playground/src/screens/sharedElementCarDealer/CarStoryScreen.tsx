import React, { useCallback, useEffect, useMemo, useRef } from 'react';
import { Dimensions, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { Navigation, NavigationFunctionComponent } from 'react-native-navigation';
import { CarItem } from '../../assets/cars';
import Reanimated, { Easing } from 'react-native-reanimated';
import DismissableView from './DismissableView';
import useDismissGesture from './useDismissGesture';
import { SET_DURATION } from './Constants';
import colors from '../../commons/Colors';
const ReanimatedTouchableOpacity = Reanimated.createAnimatedComponent(TouchableOpacity);

const HEADER_HEIGHT = 250;

interface Props {
  car: CarItem;
}

const CarStoryScreen: NavigationFunctionComponent<Props> = ({ car, componentId }) => {
  const isClosing = useRef(false);

  const onClosePressed = useCallback(() => {
    if (isClosing.current === true) return;
    isClosing.current = true;
    Navigation.pop(componentId);
  }, [componentId]);
  const dismissGesture = useDismissGesture(onClosePressed);

  const closeButtonStyle = useMemo(
    () => [styles.closeButton, { opacity: dismissGesture.controlsOpacity }],
    [dismissGesture.controlsOpacity]
  );

  useEffect(() => {
    setTimeout(() => {
      Reanimated.timing(dismissGesture.controlsOpacity, {
        toValue: 1,
        duration: 300,
        easing: Easing.linear,
      }).start();
    }, SET_DURATION);
  }, [dismissGesture.controlsOpacity]);

  return (
    <DismissableView dismissGestureState={dismissGesture} style={styles.container}>
      <Text style={styles.carName} numberOfLines={3} lineBreakMode="tail" ellipsizeMode="tail">
        {car.name}
      </Text>
      <Text
        style={styles.carDescription}
        numberOfLines={3}
        lineBreakMode="tail"
        ellipsizeMode="tail"
      >
        {car.description}
      </Text>
      <ReanimatedTouchableOpacity style={closeButtonStyle} onPress={onClosePressed}>
        <Text style={styles.closeButtonText}>x</Text>
      </ReanimatedTouchableOpacity>
    </DismissableView>
  );
};
CarStoryScreen.options = {
  statusBar: {
    visible: false,
  },
  topBar: {
    visible: false,
  },
  bottomTabs: {
    visible: false,
  },
  layout: {
    componentBackgroundColor: 'transparent',
    backgroundColor: 'transparent',
  },
};
export default CarStoryScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.primary,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: '20%',
  },
  headerImage: {
    position: 'absolute',
    height: HEADER_HEIGHT,
    width: Dimensions.get('window').width,
  },
  content: {
    paddingTop: HEADER_HEIGHT,
    paddingHorizontal: 25,
  },
  title: {
    fontSize: 32,
    marginTop: 30,
    fontWeight: '500',
    zIndex: 2,
  },
  description: {
    fontSize: 15,
    letterSpacing: 0.2,
    lineHeight: 25,
    marginTop: 32,
  },
  closeButton: {
    position: 'absolute',
    top: 50,
    right: 15,
    backgroundColor: 'rgba(0,0,0,0.5)',
    borderRadius: 15,
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  closeButtonText: {
    fontWeight: 'bold',
    color: 'white',
    fontSize: 16,
  },
  buyButton: {
    alignSelf: 'center',
    marginVertical: 25,
    width: '100%',
    height: 45,
    backgroundColor: 'dodgerblue',
    borderRadius: 15,
    justifyContent: 'center',
    alignItems: 'center',
  },
  buyText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },
  carName: {
    fontSize: 22,
    fontWeight: 'bold',
    color: 'white',
    marginVertical: 10,
    textAlign: 'center',
  },
  carDescription: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'white',
  },
});
