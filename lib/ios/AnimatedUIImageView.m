//
//  AnimatedUIImageView.m
//  ReactNativeNavigation
//
//  Created by Marc Rousavy on 23.01.21.
//  Copyright © 2021 Wix. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AnimatedUIImageView.h"

@implementation AnimatedUIImageView

- (instancetype)initElement:(UIImageView *)element
				  toElement:(UIImageView *)toElement
		  transitionOptions:(SharedElementTransitionOptions *)transitionOptions {
	self = [super initElement:element toElement:toElement transitionOptions:transitionOptions];
	self.contentMode = element.contentMode;
	_fromSize = element.image.size;
	_fromContentMode = element.contentMode;
	_toSize = toElement.image.size;
	_toContentMode = toElement.contentMode;
	
	return self;
}


@end
