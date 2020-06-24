#import "SharedElementAnimator.h"
#import "AnimatedViewFactory.h"
#import "RectTransition.h"
#import "TransformRectTransition.h"
#import "RotationTransition.h"
#import "ColorTransition.h"
#import "AnimatedTextView.h"
#import "TextStorageTransition.h"
#import "AnchorTransition.h"

@implementation SharedElementAnimator {
    SharedElementTransitionOptions* _transitionOptions;
    UIViewController* _toVC;
    UIViewController* _fromVC;
    UIView* _fromView;
    UIView* _toView;
}

- (instancetype)initWithTransitionOptions:(SharedElementTransitionOptions *)transitionOptions fromView:(UIView *)fromView toView:(UIView *)toView fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    self = [super init];
    _transitionOptions = transitionOptions;
    _fromVC = fromVC;
    _toVC = toVC;
    _fromView = fromView;
    _toView = toView;
    return self;
}

- (AnimatedReactView *)createAnimatedView:(SharedElementTransitionOptions *)transitionOptions fromView:(UIView *)fromView toView:(UIView *)toView {
    return [AnimatedViewFactory createFromElement:fromView toElement:toView transitionOptions:transitionOptions];
}

- (void)prepareAnimations {
    [self createAnimations];
}

- (void)createAnimations {
    self.view = [self createAnimatedView:_transitionOptions fromView:_fromView toView:_toView];
    [super createAnimations];
    NSMutableArray* animations = NSMutableArray.new;
    CGFloat startDelay = [_transitionOptions.startDelay getWithDefaultValue:0];
    CGFloat duration = [_transitionOptions.duration getWithDefaultValue:300];
    Text* interpolation = [_transitionOptions.interpolation getWithDefaultValue:@"accelerateDecelerate"];
    
    if (!CGRectEqualToRect(self.view.location.fromFrame, self.view.location.toFrame)) {
        if ([self.view isKindOfClass:AnimatedTextView.class]) {
            [animations addObject:[[RectTransition alloc] initWithView:self.view from:self.view.location.fromFrame to:self.view.location.toFrame startDelay:startDelay duration:duration interpolation:interpolation]];
        } else {
            [animations addObject:[[TransformRectTransition alloc] initWithView:self.view fromRect:self.view.location.fromFrame toRect:self.view.location.toFrame fromAngle:self.view.location.fromAngle toAngle:self.view.location.toAngle startDelay:startDelay duration:duration interpolation:interpolation]];
        }
    }
    
    if (![_fromView.backgroundColor isEqual:_toView.backgroundColor]) {
        [animations addObject:[[ColorTransition alloc] initWithView:self.view from:_fromView.backgroundColor to:_toView.backgroundColor startDelay:startDelay duration:duration interpolation:interpolation]];
    }
    
    if ([self.view isKindOfClass:AnimatedTextView.class]) {
        [animations addObject:[[TextStorageTransition alloc] initWithView:self.view from:((AnimatedTextView *)self.view).fromTextStorage to:((AnimatedTextView *)self.view).toTextStorage startDelay:startDelay duration:duration interpolation:interpolation]];
    }
    
    self.animations = [self.animations arrayByAddingObjectsFromArray:animations];
}

- (void)end {
    [super end];
    [self.view reset];
}


@end
