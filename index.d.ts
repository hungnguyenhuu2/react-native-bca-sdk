declare module 'react-native-bca-sdk' {

    export interface Config {
        idNumber: string
        dob: string
        doe: string
    }

    const RnBcaSdk: {
      readCard(config: Config): Promise<void>;
    };

    export default RnBcaSdk;
  }
  