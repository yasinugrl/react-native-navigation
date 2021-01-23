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
    
    // TODO: We want to run different scaling techniques depending on the resize mode.
    // In general, we want to scale/resize the element/toElement directly, since that
    // is contained in the AnimatedReactView. This means, the AnimatedReactView will
    // manage the bounds animation, and we just want to silently update the contentMode
    // so it still looks the same but has different bounds.
    
    // Example 1: element has resizeMode "cover", so it's image is larger than the view.
    // We want to update element's resizeMode to whatever resizeMode the toElement has,
    // and cancel out the visual change this will result in. So if we change from "cover"
    // to "contain", the image will get smaller so that it exactly fits in the view's bounds.
    // That means we have to know how big the image is, how far it goes beyond the view's bounds
    // right now, and style it the same way with the new resize mode. (Basically make resizeMode
    // "contain" look the same as "cover" by changing the view's frame/bounds)
    
    switch (element.contentMode) {
        case UIViewContentModeScaleAspectFill: {
            CGSize fromImageSize = CGSizeMake(element.image.size.width / element.image.scale,
                                              element.image.size.height / element.image.scale);
            CGFloat fromWidthRatio = fromImageSize.width / element.frame.size.width;
            CGFloat fromHeightRatio = fromImageSize.height / element.frame.size.height;
            
            CGFloat newWidth, newHeight;
            if (fromWidthRatio > fromHeightRatio) {
                newWidth = element.frame.size.width * fromHeightRatio;
                newHeight = element.frame.size.height;
            } else {
                newWidth = element.frame.size.width;
                newHeight = element.frame.size.height * fromWidthRatio;
            }
            
            element.contentMode = UIViewContentModeScaleAspectFit;
            element.frame = CGRectMake(element.frame.origin.x - ((newWidth - element.frame.size.width) / 2),
                                       element.frame.origin.y - ((newHeight - element.frame.size.height) / 2),
                                       newWidth,
                                       newHeight);
            break;
        }
        case UIViewContentModeScaleAspectFit: {
            CGSize fromImageSize = CGSizeMake(element.image.size.width / element.image.scale,
                                              element.image.size.height / element.image.scale);
            CGFloat fromWidthRatio = fromImageSize.width / element.frame.size.width;
            CGFloat fromHeightRatio = fromImageSize.height / element.frame.size.height;
            
            CGFloat newWidth, newHeight;
            if (fromWidthRatio > fromHeightRatio) {
                newWidth = element.frame.size.width / fromHeightRatio;
                newHeight = element.frame.size.height;
            } else {
                newWidth = element.frame.size.width;
                newHeight = element.frame.size.height / fromWidthRatio;
            }
            
            element.contentMode = UIViewContentModeScaleAspectFit;
            element.frame = CGRectMake(element.frame.origin.x - ((newWidth - element.frame.size.width) / 2),
                                       element.frame.origin.y - ((newHeight - element.frame.size.height) / 2),
                                       newWidth,
                                       newHeight);
            break;
        }
        default: {
            // TODO: Other resizeModes are not yet implemented.
            break;
        }
    }
    
    
    
	self = [super initElement:element toElement:toElement transitionOptions:transitionOptions];
	
	_fromSize = element.image.size;
	_fromContentMode = element.contentMode;
	_toSize = toElement.image.size;
	_toContentMode = toElement.contentMode;
	
	//self.contentMode = element.contentMode;
    
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
