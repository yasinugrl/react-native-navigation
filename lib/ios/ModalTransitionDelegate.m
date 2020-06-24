#import "ModalTransitionDelegate.h"

@implementation ModalTransitionDelegate

- (instancetype)initWithContentTransition:(TransitionOptions *)contentTransitionOptions bridge:(RCTBridge *)bridge fromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    self = [super initWithBridge:bridge fromVC:fromVC toVC:toVC];
    _contentTransitionOptions = contentTransitionOptions;
    return self;
}

- (NSArray *)prepareTransitionsFromVC:(UIViewController *)fromVC toVC:(UIViewController *)toVC {
    ContentTransitionCreator* contentTransition = [ContentTransitionCreator createTransition:_contentTransitionOptions view:toVC.view fromVC:fromVC toVC:toVC];
    
    return @[contentTransition];
}

- (void)prepareTransitionContext:(id<UIViewControllerContextTransitioning>)transitionContext {
    UIView* toView = [transitionContext viewForKey:UITransitionContextToViewKey];
    toView.alpha = 0;
    [transitionContext.containerView addSubview:toView];
}

- (nullable id <UIViewControllerAnimatedTransitioning>)animationControllerForPresentedController:(UIViewController *)presented presentingController:(UIViewController *)presenting sourceController:(UIViewController *)source {
    return self;
}

@end
