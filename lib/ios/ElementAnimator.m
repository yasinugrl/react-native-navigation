#import "ElementAnimator.h"
#import "ElementAlphaTransition.h"
#import "ElementVerticalTransition.h"
#import "ElementHorizontalTransition.h"
#import "HorizontalTranslationTransition.h"
#import "VerticalTranslationTransition.h"
#import "Transition.h"
#import "RNNElementFinder.h"
#import "VerticalRotationTransition.h"

@implementation ElementAnimator {
    UIViewController* _toVC;
    UIViewController* _fromVC;
    ElementTransitionOptions* _transitionOptions;
}

- (instancetype)initWithTransitionOptions:(ElementTransitionOptions *)transitionOptions view:(UIView *)view fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    self = [super init];
    _fromVC = fromVC;
    _toVC = toVC;
    _transitionOptions = transitionOptions;
    self.view = view;
    [self createAnimations];
    return self;
}

- (void)prepareAnimations {
    
}

- (void)createAnimations {
    NSMutableArray* animations = [NSMutableArray new];
    if (_transitionOptions.alpha.hasAnimation) {
        [animations addObject:[[ElementAlphaTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.alpha]];
    }
    
    if (_transitionOptions.x.hasAnimation) {
        [animations addObject:[[ElementHorizontalTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.x]];
    }
    
    if (_transitionOptions.y.hasAnimation) {
        [animations addObject:[[ElementVerticalTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.y]];
    }
    
    if (_transitionOptions.translationX.hasAnimation) {
        [animations addObject:[[HorizontalTranslationTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.translationX]];
    }
    
    if (_transitionOptions.translationY.hasAnimation) {
        [animations addObject:[[VerticalTranslationTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.translationY]];
    }
    
    if (_transitionOptions.rotationY.hasAnimation) {
        [animations addObject:[[VerticalRotationTransition alloc] initWithView:self.view transitionDetails:_transitionOptions.rotationY]];
    }
    
    self.animations = animations;
}

@end
