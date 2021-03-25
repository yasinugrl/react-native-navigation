import React, { useCallback } from 'react';
import { StyleSheet, Text, Pressable, View, Dimensions } from 'react-native';
import FastImage, { Source } from 'react-native-fast-image';
import { Navigation, NavigationFunctionComponent } from 'react-native-navigation';
import Animated, { useValue } from 'react-native-reanimated';
import Video from 'react-native-video';

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
    <Animated.View style={[styles.container, { transform: [{ translateX: x }] }]}>
      <View style={{ flexDirection: 'row' }}>
        <Video
          nativeID={sharedElementId}
          style={styles.image}
          source={{
            uri:
              'https://www.pexels.com/video/1572342/download/?search_query=&tracking_id=4tyxhpzipti',
          }}
          resizeMode="contain"
        />
      </View>

      <Pressable onPress={onClose} style={styles.closeButton}>
        <Text style={styles.closeText}>x</Text>
      </Pressable>
    </View>
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
  image: {
    width: Dimensions.get('screen').width,
    height: Dimensions.get('screen').height,
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
