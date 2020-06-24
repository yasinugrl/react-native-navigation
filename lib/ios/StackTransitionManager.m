#import "StackTransitionManager.h"
#import "SharedElementTransitionsCreator.h"
#import "ElementTransitionsCreator.h"
#import "ContentTransitionCreator.h"

@implementation StackTransitionManager {
    ContentTransitionCreator* _contentTransition;
    NSArray<BaseAnimator *>* _elementTransitions;
    NSArray<BaseAnimator *>* _sharedElementTransitions;
    BOOL _hasContentAnimation;
    NSTimeInterval _transitionDuration;
}

- (instancetype)initWithScreenTransition:(RNNScreenTransition *)screenTransition bridge:(RCTBridge *)bridge fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    self = [super initWithBridge:bridge fromVC:fromVC toVC:toVC];
    [self createTransitions:screenTransition fromVC:fromVC toVC:toVC];
    return self;
}

- (BOOL)isValid {
    return _elementTransitions.count || _sharedElementTransitions.count || _hasContentAnimation;
}

- (NSTimeInterval)transitionDuration:(id <UIViewControllerContextTransitioning>)transitionContext {
    return _transitionDuration;
}

- (void)createTransitions:(RNNScreenTransition *)screenTransition fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    _hasContentAnimation = screenTransition.content.hasAnimation;
    _transitionDuration = screenTransition.maxDuration;
    _contentTransition = [ContentTransitionCreator createTransition:screenTransition.content view:toVC.view fromVC:fromVC toVC:toVC];
    _elementTransitions = [ElementTransitionsCreator create:screenTransition.elementTransitions fromVC:fromVC toVC:toVC];
    _sharedElementTransitions = [SharedElementTransitionsCreator create:screenTransition.sharedElementTransitions fromVC:fromVC toVC:toVC];
}

- (NSArray *)prepareTransitionsFromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC containerView:(UIView *)containerView {
    for (ElementAnimator* transition in _sharedElementTransitions) {
        [transition prepareAnimations];
    }
    
    [self addSharedElementViews:_sharedElementTransitions toContainerView:containerView];
    
    return [[[NSArray arrayWithObject:_contentTransition] arrayByAddingObjectsFromArray:_elementTransitions] arrayByAddingObjectsFromArray:_sharedElementTransitions];
}

- (void)addSharedElementViews:(NSArray<BaseAnimator *> *)animators toContainerView:(UIView *)containerView {
    for (BaseAnimator* animator in [self sortByZIndex:animators]) {
        [containerView addSubview:animator.view];
    }
}

- (NSArray<DisplayLinkAnimatorDelegate>*)sortByZIndex:(NSArray<BaseAnimator *> *)animators {
    return (NSArray<DisplayLinkAnimatorDelegate>*)[animators sortedArrayUsingComparator:^NSComparisonResult(BaseAnimator * a, BaseAnimator* b) {
        id first = [a.view valueForKey:@"reactZIndex"];
        id second = [b.view valueForKey:@"reactZIndex"];
        return [first compare:second];
    }];
}

@end
