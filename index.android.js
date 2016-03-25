/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  Alert,
  DeviceEventEmitter,
} from 'react-native';

// Must be in global to register the listener, otherwise it may be too late in this case.
// You may save the info to DB and subscribe DB event in component.
DeviceEventEmitter.addListener('FromService', (data)=>{
  console.log("got message from service " + data.something);
});

class ReactNativeAndroidService extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          This is example to show how to use react-native in android service.
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('ReactNativeAndroidService', () => ReactNativeAndroidService);
