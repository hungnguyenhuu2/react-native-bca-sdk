import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

export const UNKNOWN_ERROR = 'Lỗi không xác định';
const LINKING_ERROR =
    `The package 'react-native-bca-sdk' doesn't seem to be linked. Make sure: \n\n` +
    Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
    '- You rebuilt the app after installing the package\n' +
    '- You are not using Expo managed workflow\n';

const RNBcaSdk = NativeModules.RNBcaSdk
    ? NativeModules.RNBcaSdk
    : new Proxy(
        {},
        {
            get() {
                throw new Error(LINKING_ERROR);
            },
        }
    );


class RNBcaSdkJS {
    _url = undefined;
    _eventEmitter = undefined;
    constructor() {
        this._eventEmitter = new NativeEventEmitter(RNBcaSdk);
    }

    init = (
        url
    ) => {
        this._url = url;
        if (this._eventEmitter) {
            this._eventEmitter.addListener(
                'EventReadCardSuccess',
                (event) => {
                    console.log('EventReadCardSuccess', event);
                }
            );
            this._eventEmitter.addListener(
                'EventReadCardErrorCallback',
                (event) => {
                    console.log('EventReadCardErrorCallback', event);
                }
            );
            this._eventEmitter.addListener(
                'EventReadCardErrorCallback',
                (event) => {
                    console.log('EventErrorCodeCallback', event);
                }
            );
        }

        return RNBcaSdk.init(url);
    };

    getEventEmitter = () => {
        console.log('getEventEmitter')
        return this._eventEmitter;
    };


    readCardFIS = (token,cccd ) => {
        return RNBcaSdk.readCardFIS(token,cccd);
    };


    removeAllListeners = () => {
        if (this._eventEmitter) {
            this._eventEmitter.removeAllListeners('EventReadCardSuccess');
        }
    };
}

export default Platform.OS === 'ios' ? RNBcaSdk : new RNBcaSdkJS();