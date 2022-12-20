//
//  BCAManagerBridge.m
//  RNBcaSdk
//
//  Created by Khanh Khau - Ban Vien on 16/12/2022.
//  Copyright © 2022 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(BCAManager, NSObject)

RCT_EXTERN_METHOD(readCard:(NSDictionary *)options resolver:(RCTPromiseResolveBlock)resolver rejecter:(RCTPromiseRejectBlock)rejecter)

@end
