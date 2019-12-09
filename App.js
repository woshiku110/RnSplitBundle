/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import {
  Text,
  View,
  StyleSheet,
  AppRegistry
} from 'react-native';
class App extends Component {

  render() {
    return (
        <View style={ styles.container }>
          <Text>
            正常代码开发
          </Text>
        </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },

  icon: {
    marginTop: 16
  }
});

export default App;
