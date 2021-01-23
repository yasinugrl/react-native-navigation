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
	
	_fromSize = element.image.size;
	_fromContentMode = element.contentMode;
	_toSize = toElement.image.size;
	_toContentMode = toElement.contentMode;
	
	self.contentMode = toElement.contentMode;
	
	return self;
}
/*
- (void)updateViewToAspectFit
{
	CGSize imageSize = CGSizeMake(_toSize.width / self.imageView.image.scale,
								  self.imageView.image.size.height / self.imageView.image.scale);
	
	CGFloat widthRatio = imageSize.width / self.bounds.size.width;
	CGFloat heightRatio = imageSize.height / self.bounds.size.height;
	
	if (widthRatio > heightRatio) {
		imageSize = CGSizeMake(imageSize.width / widthRatio, imageSize.height / widthRatio);
	} else {
		imageSize = CGSizeMake(imageSize.width / heightRatio, imageSize.height / heightRatio);
	}
	
	self.imageView.bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
	self.imageView.center = CGPointMake(self.bounds.size.width / 2, self.bounds.size.height / 2);
}

- (void)updateViewToAspectFill
{
	CGSize imageSize = CGSizeMake(self.imageView.image.size.width / self.imageView.image.scale,
								  self.imageView.image.size.height / self.imageView.image.scale);
	
	CGFloat widthRatio = imageSize.width / self.bounds.size.width;
	CGFloat heightRatio = imageSize.height / self.bounds.size.height;
	
	if (widthRatio > heightRatio) {
		imageSize = CGSizeMake(imageSize.width / heightRatio, imageSize.height / heightRatio);
	} else {
		imageSize = CGSizeMake(imageSize.width / widthRatio, imageSize.height / widthRatio);
	}
	
	self.imageView.bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
	[self centerImageViewToSuperView];
}

- (void)updateViewToScaleToFill
{
	self.imageView.bounds = CGRectMake(0, 0, self.bounds.size.width, self.bounds.size.height);
	[self centerImageViewToSuperView];
}

- (void)updateViewToCenter
{
	[self fitImageViewSizeToImageSize];
	[self centerImageViewToSuperView];
}

- (void)updateViewToBottom
{
	[self fitImageViewSizeToImageSize];
	self.imageView.center = CGPointMake(self.bounds.size.width / 2,
										self.bounds.size.height - self.image.size.height / 2);
}
*/


@end
