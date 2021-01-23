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

@property (nonatomic) CGSize fromSize;
@property (nonatomic) UIViewContentMode fromContentMode;
@property (nonatomic) CGSize toSize;
@property (nonatomic) UIViewContentMode toContentMode;

@end

#endif /* AnimatedUIImageView_h */
