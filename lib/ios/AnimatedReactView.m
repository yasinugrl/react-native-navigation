#import "AnimatedReactView.h"
#import "UIView+Utils.h"
#import <React/UIView+React.h>

@implementation AnimatedReactView {
    UIView *_originalParent;
    CGRect _originalFrame;
    UIViewContentMode _originalContentMode;
    CGFloat _originalCornerRadius;
    CGRect _originalLayoutBounds;
    CATransform3D _originalTransform;
    UIView *_toElement;
    UIColor *_fromColor;
    NSInteger _zIndex;
    SharedElementTransitionOptions *_transitionOptions;
}

- (instancetype)initElement:(UIView *)element
                  toElement:(UIView *)toElement
          transitionOptions:(SharedElementTransitionOptions *)transitionOptions {
    self.location = [[RNNViewLocation alloc] initWithFromElement:element toElement:toElement];
    self = [super initWithFrame:self.location.fromFrame];
    _transitionOptions = transitionOptions;
    _toElement = toElement;
    _toElement.hidden = YES;
    _fromColor = element.backgroundColor;
    _zIndex = toElement.reactZIndex;
    [self hijackReactElement:element];

    return self;
}

- (void)setBackgroundColor:(UIColor *)backgroundColor {
    [super setBackgroundColor:backgroundColor];
    _reactView.backgroundColor = backgroundColor;
}

- (void)setCornerRadius:(CGFloat)cornerRadius {
    [super setCornerRadius:cornerRadius];
    [_reactView setCornerRadius:cornerRadius];
}

- (NSNumber *)reactZIndex {
    return @(_zIndex);
}

- (void)hijackReactElement:(UIView *)element {
    _reactView = element;
    _originalFrame = _reactView.frame;
    _originalTransform = element.layer.transform;
    _originalLayoutBounds = element.layer.bounds;
    _originalContentMode = element.contentMode;
    //self.contentMode = element.contentMode;
    self.frame = self.location.fromFrame;
    
    if ([_reactView isKindOfClass:UIImageView.class]) {
        CGRect before = element.bounds;
        NSLog(@"We're going from %d to %d", element.contentMode, _toElement.contentMode);
        [self resizeViewToContentMode:(UIImageView *)_reactView contentMode:_toElement.contentMode];
        CGRect after = element.bounds;
        _reactView.contentMode = _toElement.contentMode;
    }
    
    _originalParent = _reactView.superview;
    _originalCornerRadius = element.layer.cornerRadius;
    _reactView.frame = self.bounds;
    _reactView.layer.transform = CATransform3DIdentity;
    _reactView.layer.cornerRadius = self.location.fromCornerRadius;
    [self addSubview:_reactView];
}

- (void)reset {
    _reactView.frame = _originalFrame;
    _reactView.layer.cornerRadius = _originalCornerRadius;
    _reactView.bounds = _originalLayoutBounds;
    _reactView.layer.bounds = _originalLayoutBounds;
    _reactView.layer.transform = _originalTransform;
    _reactView.contentMode = _originalContentMode;
    [_originalParent insertSubview:_reactView atIndex:self.location.index];
    _toElement.hidden = NO;
    _reactView.backgroundColor = _fromColor;
    [self removeFromSuperview];
}

- (void)layoutSubviews {
    [super layoutSubviews];
    _reactView.frame = self.bounds;
}

/**
 Resizes the given view to a size where it looks exactly the same with the given `contentMode` as it does currently without it.
 */
- (void)resizeViewToContentMode:(UIImageView *)imageView
                    contentMode:(UIViewContentMode)contentMode {
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
    
    if (contentMode == imageView.contentMode) {
        return;
    }
    
    // TODO: This is still a bit off. See: https://github.com/vitoziv/VICMAImageView/blob/master/VICMAImageView/VICMAImageView.m#L156-L278
    // Maybe helpful: https://gist.github.com/tomasbasham/10533743
    
    CGSize imageSize = CGSizeMake(imageView.image.size.width / imageView.image.scale,
                                  imageView.image.size.height / imageView.image.scale);
    switch (contentMode) {
        case UIViewContentModeScaleAspectFill: {
            // "???" -> "cover"
            // image is going to be larger than view (clipped)
            CGFloat widthRatio = imageView.bounds.size.width / imageSize.width;
            CGFloat heightRatio = imageView.bounds.size.height / imageSize.height;
            CGFloat scale = MIN(widthRatio, heightRatio);
            CGFloat imageWidth = imageSize.width / scale;
            CGFloat imageHeight = imageSize.height / scale;
            imageView.bounds = CGRectMake(0, 0, imageWidth, imageHeight);
            imageView.center = CGPointMake(imageWidth / 2, imageHeight / 2);
            break;
        }
        case UIViewContentModeScaleAspectFit: {
            // "???" -> "contain"
            // image is going to fit in view with one side being an exact match
            CGFloat widthRatio = imageView.bounds.size.width / imageSize.width;
            CGFloat heightRatio = imageView.bounds.size.height / imageSize.height;
            CGFloat scale = MIN(widthRatio, heightRatio);
            CGFloat imageWidth = scale * imageSize.width;
            CGFloat imageHeight = scale * imageSize.height;
            imageView.bounds = CGRectMake(0, 0, imageWidth, imageHeight);
            imageView.center = CGPointMake(imageWidth / 2, imageHeight / 2);
            break;
        }
        default: {
            // TODO: Other resizeModes are not yet implemented.
            break;
        }
    }
}


/*
- (void)updateViewToAspectFit:(UIImageView*)imageView
{
    CGSize imageSize = CGSizeMake(imageView.image.size.width / imageView.image.scale,
                                  imageView.image.size.height / imageView.image.scale);
    
    CGFloat widthRatio = imageSize.width / self.bounds.size.width;
    CGFloat heightRatio = imageSize.height / self.bounds.size.height;
    
    if (widthRatio > heightRatio) {
        imageSize = CGSizeMake(imageSize.width / widthRatio, imageSize.height / widthRatio);
    } else {
        imageSize = CGSizeMake(imageSize.width / heightRatio, imageSize.height / heightRatio);
    }
    
    imageView.bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
    imageView.center = CGPointMake(self.bounds.size.width / 2, self.bounds.size.height / 2);
}

- (void)updateViewToAspectFill:(UIImageView*)imageView
{
    CGSize imageSize = CGSizeMake(imageView.image.size.width / imageView.image.scale,
                                  imageView.image.size.height / imageView.image.scale);
    
    CGFloat widthRatio = imageSize.width / self.bounds.size.width;
    CGFloat heightRatio = imageSize.height / self.bounds.size.height;
    
    if (widthRatio > heightRatio) {
        imageSize = CGSizeMake(imageSize.width / heightRatio, imageSize.height / heightRatio);
    } else {
        imageSize = CGSizeMake(imageSize.width / widthRatio, imageSize.height / widthRatio);
    }
    
    imageView.bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
    [self centerImageViewToSuperView:imageView];
}

- (void)updateViewToScaleToFill:(UIImageView*)imageView
{
    imageView.bounds = CGRectMake(0, 0, self.bounds.size.width, self.bounds.size.height);
    [self centerImageViewToSuperView:imageView];
}

- (void)updateViewToCenter:(UIImageView*)imageView
{
    [self fitImageViewSizeToImageSize:imageView];
    [self centerImageViewToSuperView:imageView];
}

- (void)fitImageViewSizeToImageSize:(UIImageView*)imageView
{
    CGSize imageSize = CGSizeMake(imageView.image.size.width / imageView.image.scale,
                                  imageView.image.size.height / imageView.image.scale);
    
    imageView.bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
}

- (void)centerImageViewToSuperView:(UIImageView*)imageView
{
    imageView.center = CGPointMake(self.bounds.size.width / 2, self.bounds.size.height / 2);
}
*/

@end
