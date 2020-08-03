//
//  MyTurboModule.h
//  TurboModulePlayground
//
//  Created by Watcharachai Kanjaikaew on 22/12/2562 BE.
//  Copyright © 2562 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RNNTurboModuleSpec.h"

@interface RNNTurboModule : NSObject<NativeRNNTurboModuleSpec>

- (instancetype)initWithBridge:(RCTBridge *)bridge mainWindow:(UIWindow *)mainWindow nativeComponentStore:(id)nativeComponentStore;

@end
