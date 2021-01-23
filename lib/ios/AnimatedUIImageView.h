//
//  AnimatedUIImageView.h
//  ReactNativeNavigation
//
//  Created by Marc Rousavy on 23.01.21.
//  Copyright © 2021 Wix. All rights reserved.
//

#ifndef AnimatedUIImageView_h
#define AnimatedUIImageView_h

#import "AnimatedReactView.h"
#import <UIKit/UIKit.h>

@interface AnimatedUIImageView : AnimatedReactView

@property(nonnull) CGSize fromSize;
@property(nonnull) UIViewContentMode fromContentMode;
@property(nonnull) CGSize toSize;
@property(nonnull) UIViewContentMode toContentMode;

@end

#endif /* AnimatedUIImageView_h */
