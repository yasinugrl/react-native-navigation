import React, { useCallback } from 'react';
import { StyleSheet, Text, Pressable } from 'react-native';
import FastImage, { Source } from 'react-native-fast-image';
import { Navigation, NavigationFunctionComponent } from 'react-native-navigation';
import Animated, { useValue } from 'react-native-reanimated';

interface Props {
  source: Source;
  sharedElementId: string;
}

const ImageFullScreenViewer: NavigationFunctionComponent<Props> = ({
  source,
  sharedElementId,
  componentId,
}): React.ReactElement => {
  const onClose = useCallback(() => {
    Navigation.dismissModal(componentId);
  }, [componentId]);

  const x = useValue(-150);

  return (
    <Animated.View style={[styles.container, { transform: [{ translateX: x }] }]}>
      <FastImage
        // @ts-ignore nativeID isn't included in FastImage props.
        nativeID={sharedElementId}
        style={StyleSheet.absoluteFill}
        source={source}
        resizeMode="contain"
      />

      <Pressable onPress={onClose} style={styles.closeButton}>
        <Text style={styles.closeText}>x</Text>
      </Pressable>
    </Animated.View>
  );
};

export default ImageFullScreenViewer;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'black',
    alignItems: 'center',
    justifyContent: 'center',
  },
  closeButton: {
    position: 'absolute',
    top: 50,
    right: 15,
    backgroundColor: 'white',
    borderRadius: 15,
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  closeText: {
    color: 'black',
    fontWeight: 'bold',
  },
});
