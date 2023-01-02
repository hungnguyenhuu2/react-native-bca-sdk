declare module 'react-native-bca-sdk' {

    export interface Config {
        idNumber: string
        dob: string
        doe: string
    }

    export interface ConfigFIS {
      idNumber: string
      dob: string
      doe: string
      token: string
  }

    const RnBcaSdk: {
      readCard(config: Config): Promise<void>;
      readCardFIS(config: ConfigFIS): Promise<void>;
    };

    export default RnBcaSdk;
  }
  