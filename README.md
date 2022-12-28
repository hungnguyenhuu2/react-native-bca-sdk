
# react-native-bca-sdk

## Getting started

`$ npm install react-native-bca-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-bca-sdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-bca-sdk` and add `RNBcaSdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNBcaSdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<
5. Add code below to Info.plist:
  ```
  <key>NFCReaderUsageDescription</key>
	<string>Use NFC to read card info</string>
  <key>com.apple.developer.nfc.readersession.iso7816.select-identifiers</key>
  <array>
    <string>A0000002471001</string>
    <string>A0000002472001</string>
    <string>00000000000000</string>
  </array>
  ```
#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.vib.rnbca.RNBcaSdkPackage;` to the imports at the top of the file
  - Add `new RNBcaSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-bca-sdk'
  	project(':react-native-bca-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-bca-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-bca-sdk')
  	```


## Usage
```javascript
import RNBcaSdk from 'react-native-bca-sdk';
```
### Tap & read card info via NFC

Read card info
```javascript
RNBcaSdk.readCard({
  idNumber: 'xxxxxxxxx',
  dob: 'YYMMDD',
  doe: 'YYMMDD',
}).then(res => {
  console.log(res);
});
```

RNBcaSdk;
```
