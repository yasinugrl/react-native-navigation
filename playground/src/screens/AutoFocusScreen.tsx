import React, { useState } from 'react';
import {
  Dimensions,
  InputAccessoryView,
  StyleSheet,
  View,
  ScrollView,
  TextInput,
  Text,
} from 'react-native';
import { NavigationFunctionComponent } from 'react-native-navigation';

// With autoFocus enabled, the InputAccessoryView glitches around even more. It just goes invisible.
const AUTO_FOCUS = false;

const AutoFocusScreen: NavigationFunctionComponent = () => {
  const [text, setText] = useState('');
  return (
    <View style={styles.container}>
      <ScrollView
        style={styles.scrollView}
        contentContainerStyle={styles.scrollViewContent}
        keyboardDismissMode="interactive"
      >
        {Array(20)
          .fill(0)
          .map((_, i) => (
            <Text key={i} style={styles.filler}>
              Row #{i + 1}
            </Text>
          ))}
        <View style={styles.filler} />
      </ScrollView>
      <InputAccessoryView>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            autoFocus={AUTO_FOCUS}
            placeholder="Enter some Text"
            value={text}
            onChangeText={setText}
          />
        </View>
      </InputAccessoryView>
    </View>
  );
};

AutoFocusScreen.options = {
  bottomTabs: {
    visible: false,
  },
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scrollView: {
    flex: 1,
  },
  scrollViewContent: {
    paddingTop: 10,
    paddingBottom: 60,
  },
  filler: {
    height: 50,
    width: Dimensions.get('screen').width,
    marginTop: 5,
    backgroundColor: 'grey',
    color: 'white',
    textAlign: 'center',
    textAlignVertical: 'center',
  },
  inputContainer: {
    paddingHorizontal: 5,
    paddingBottom: 5,
  },
  input: {
    height: 40,
    borderRadius: 5,
    backgroundColor: 'white',
    paddingHorizontal: 10,
  },
});

export default AutoFocusScreen;
