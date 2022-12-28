//
//  RNBcaSdkBridge.m
//  RNBcaSdk
//
//  Created by Khanh Khau - Ban Vien on 21/12/2022.
//

#import <Foundation/Foundation.h>

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(RNBcaSdk, NSObject)

RCT_EXTERN_METHOD(readCard:(NSDictionary *)options resolver:(RCTPromiseResolveBlock)resolver rejecter:(RCTPromiseRejectBlock)rejecter)

@end
