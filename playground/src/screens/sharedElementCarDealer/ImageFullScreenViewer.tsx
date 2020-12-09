import React, { useCallback } from 'react';
import { StyleSheet, View, Text, Pressable, Dimensions } from 'react-native';
import { Blurhash } from 'react-native-blurhash';
import FastImage, { Source } from 'react-native-fast-image';
import { Navigation, NavigationFunctionComponent } from 'react-native-navigation';

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

  return (
    <View style={styles.container}>
      <Blurhash
        nativeID={sharedElementId}
        blurhash="LGFFaXYk^6#M@-5c,1J5@[or[Q6."
        style={styles.background}
        decodeAsync={true}
        decodeHeight={16}
        decodeWidth={16}
        decodePunch={0.35}
      />
      <FastImage
        // @ts-ignore nativeID isn't included in FastImage props.
        nativeID={`${sharedElementId}-small`}
        style={styles.smallImage}
        source={source}
        resizeMode="contain"
      />

      <Pressable onPress={onClose} style={styles.closeButton}>
        <Text style={styles.closeText}>x</Text>
      </Pressable>
    </View>
  );
};

export default ImageFullScreenViewer;

const styles = StyleSheet.create({
  background: {
    position: 'absolute',
    width: '100%',
    height: '100%',
    zIndex: -100,
    borderRadius: 1,
  },
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
  smallImage: {
    width: Dimensions.get('window').width * 0.1,
    aspectRatio: 1,
    zIndex: 100,
  },
});
