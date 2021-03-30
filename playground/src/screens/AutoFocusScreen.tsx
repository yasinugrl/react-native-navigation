import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { TextInput } from 'react-native-gesture-handler';
import { NavigationFunctionComponent } from 'react-native-navigation';

const AutoFocusScreen: NavigationFunctionComponent = () => {
  const [text, setText] = useState('');
  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        autoFocus={true}
        placeholder="Enter some Text"
        value={text}
        onChangeText={setText}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  input: {
    height: 40,
    width: '80%',
    borderRadius: 5,
    borderWidth: 1,
    borderColor: 'grey',
    paddingHorizontal: 10,
  },
});

export default AutoFocusScreen;
