//import '../Common';
import React, { Component } from 'react';
import {
    Text,
    View,
    StyleSheet,
    AppRegistry
} from 'react-native';
export default class SecondModule extends Component {

    render() {
        return (
            <View style={ styles.container }>
                <Text>
                    RN业务模块2lala
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

AppRegistry.registerComponent('SecondModule', () => SecondModule);
